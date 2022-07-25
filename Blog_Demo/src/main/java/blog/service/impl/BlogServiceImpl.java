package blog.service.impl;

import blog.dao.BlogDao;
import blog.empty.Blog;
import blog.service.BlogService;
import blog.service.CategoryService;
import blog.service.TagService;
import blog.utils.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 欧尼熊
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    protected BlogDao blogDao;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    /**
     * 获取所有博客信息：不包含内容
     * @param type '2'-未删除博客，'1'-已删除博客,'0'-已发布且未删除博客, "3"-所有已发布博客
     * @return 博客信息集合
     */
    @Override
    public List<Blog> getBlogs(Character type) {
        if(Field.UNDELETED.equals(type)) {
            return blogDao.getUnDeleted();
        } else if(Field.DELETED.equals(type)) {
            return blogDao.getDeleted();
        } else if(Field.NORMAL.equals(type)) {
            return blogDao.getNormal();
        } else if(Field.RELEASE.equals(type)) {
            return blogDao.getRelease();
        } else {
            return new ArrayList<>(0);
        }
    }

    /**
     * 通过 id 获取博客信息
     * @param id 博客 id
     * @return 博客
     */
    @Override
    public Blog getBlogById(Integer id) {
        return blogDao.getBlogById(id);
    }

    /**
     * 通过标题模糊搜索博客：不包含内容
     * @param title 标题
     * @return 博客结果
     */
    @Override
    public List<Blog> getBlogByTitle(String title) {
        return blogDao.getBlogByName(title);
    }

    /**
     * 通过标签名获取博客集合：不含内容
     * 或通过类别获取博客集合
     * @param variable 标签名 或 类别名
     * @param flag 判断是标签或类别,true-标签名，false-类别名
     * @return 博客集合
     */
    @Override
    public List<Blog> getBlogs(String variable, Boolean flag) {
        if(flag) {
            return blogDao.getByTag(variable);
        } else {
            return blogDao.getBlogByCate(variable);
        }
    }

    /**
     * 根据参数获取不同排序博客集合：仅获取博客id、标题
     * @param type 排序类型：0-浏览量排序，1-创建时间排序
     * @return 返回博客集合
     */
    @Override
    public List<Blog> getByOrder(Integer type) {
        if(Field.VIEWS.equals(type)) {
            return blogDao.getByViews();
        } else if(Field.TIME.equals(type)) {
            return blogDao.getByTimes();
        }
        return new ArrayList<>();
    }

    /**
     * 获取博客数量
     * @param flag true-已删除博客，false-正常博客
     * @return 返回博客数量
     */
    @Override
    public Integer getCount(Boolean flag) {
        if(flag) {
            return blogDao.getCount(Field.DELETED);
        } else {
            return blogDao.getCount(Field.NORMAL);
        }
    }

    /**
     * 根据指定标签或类别或取对应博客数量
     * @param variable 标签或类别名
     * @param flag true-标签，false-类别
     * @return 对应博客数量
     */
    @Override
    public Integer getCount(String variable, Boolean flag) {
        if(flag) {
            return blogDao.getTagBlogCount(variable);
        } else {
            return blogDao.getCateBlogCount(variable);
        }
    }

    /**
     * 添加博客；需要 博客标题、内容、类别 id、
     * 标签名称（'|'分隔字符串）、状态、评论权限
     * 添加博客后添加标签、类别数据
     * @param blog 博客
     * @return 返回添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(Blog blog) {
        // 添加博客
        Integer rows = blogDao.insert(blog);
        // 成功后进行标签和类别数据添加
        if(rows != null && rows != 0) {
            String[] tags = blog.getBlogTagName().trim().split("\\s+");
            // 定义自定义标签集合
            List<String> newTags = new ArrayList<>();
            // 已存在标签集合
            List<String> existTags = new ArrayList<>();
            // 遍历博客标签
            for(String tag: tags) {
                log.info("tag = {}", tag);
                // 标签已存在时添加到已存在标签集合，否则放入新集合中
                if(tagService.getTag(tag) != null) {
                    existTags.add(tag);
                } else {
                    newTags.add(tag);
                }
            }
            if(existTags.size() > 0) {
                // 批量修改使用次数
                tagService.update(existTags, 1);
            }
            // 批量添加标签
            if(newTags.size() > 0) {
                tagService.insert(newTags);
            }
            // 获取类别信息
            String blogCate = blog.getBlogCate();
            if(categoryService.getCate(blogCate) != null) {
                // 类别已存在时添加使用次数
                categoryService.update(blogCate, 1);
            } else {
                // 否则添加类别
                categoryService.insert(blogCate);
            }
            return true;
        }
        return false;
    }

    /**
     * 删除博客：放入回收站或者彻底删除
     * @param id 博客 id
     * @param flag 为true时彻底删除博客，否则将deleted设置1
     * @return 返回删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer id, Boolean flag) {
        if(flag) {
            log.info("彻底删除博客");
            Integer rows = blogDao.deleted(id);
            return rows != null && rows != 0;
        } else {
            log.info("将博客放入回收站");
            // 设置deleted为1
            Integer rows = blogDao.remove(id);
            return tagAndCateCount(id, rows, -1);
        }
    }

    /**
     * 恢复博客
     * @param id 博客id
     * @return 恢复结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean restore(Integer id) {
        Integer rows = blogDao.restore(id);
        return tagAndCateCount(id, rows, 1);
    }

    /**
     * 删除或恢复博客时修改类别、标签使用次数
     * @param id 博客 id
     * @param rows sql 语句受影响行数
     * @return 修改结果
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean tagAndCateCount(Integer id, Integer rows, Integer num) {
        if(rows != null && rows != 0) {
            Blog blog = blogDao.getBlogById(id);
            String[] tags = blog.getBlogTagName().trim().split("\\s+");
            List<String> list = new ArrayList<>(Arrays.asList(tags));
            if(list.size() > 0) {
                log.info("修改标签使用次数, tags = {}", list);
                tagService.update(list, num);
            }
            log.info("修改类别使用次数");
            categoryService.update(blog.getBlogCate(), num);
            return true;
        }
        return false;
    }

    /**
     * 修改博客
     * 修改标签、分类对应情况
     * @param blog 博客
     * @return 返回修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(Blog blog) {
        // 获取原博客
        Blog oldBlog = blogDao.getBlogById(blog.getBlogId());
        /* 博客修改 */
        Integer rows = blogDao.update(blog);
        if(rows != null && rows != 0) {
            log.info("标签修改开始 --- ");
            // 获取新、原博客标签，并分割为字符串数组
            String newBlogTagName = blog.getBlogTagName();
            String oldBlogBlogTagName = oldBlog.getBlogTagName();
            String[] newNames = newBlogTagName.trim().split("\\s+");
            String[] oldNames = oldBlogBlogTagName.trim().split("\\s+");
            // 自定义标签集合
            List<String> newTagsList = new ArrayList<>(5);
            // 已存在且和原博客不同的标签集合
            List<String> existTagsList = new ArrayList<>(5);
            // 新博客标签集合
            List<String> newTags = new ArrayList<>(5);
            // 原博客标签集合
            List<String> oldTags = new ArrayList<>(5);
            // 新博客存在标签
            if(newNames.length > 0) {
                newTags = new ArrayList<>(Arrays.asList(newNames));
                // 原博客也存在标签
                if(oldNames.length > 0) {
                    oldTags = new ArrayList<>(Arrays.asList(oldNames));
                    log.info("新、原博客都有标签：新 = {}， 原 = {}", newTags, oldTags);
                    for(int i = 0; i < newTags.size(); i++) {
                        String newTag = newTags.get(i);
                        if(tagService.getTag(newTag) == null) {
                            // 自定义新标签添加到自定义标签集合
                            newTagsList.add(newTag);
                            // 将此标签从集合中移除
                            newTags.remove(newTag);
                        } else {
                            // 遍历原博客标签集合，删除新旧博客都使用的标签
                            for(int j = 0; j < oldTags.size(); j++) {
                                String oldTag = oldTags.get(j);
                                if(newTag.equals(oldTag)) {
                                    // 将原博客中和新博客相同的标签删除，保留新博客不再使用的标签
                                    oldTags.remove(oldTag);
                                    // 同时从新博客标签集合中删除，只保留数据库存在但原博客未使用标签
                                    newTags.remove(newTag);
                                }
                            }
                        }
                    }
                } else {
                    log.info("新博客有标签，原博客没有标签");
                    for(String newTag: newTags) {
                        if(tagService.getTag(newTag) == null) {
                            newTagsList.add(newTag);
                        } else {
                            existTagsList.add(newTag);
                        }
                    }
                }
            } else if(oldNames.length > 0) {
                log.info("新博客没有标签，原博客有标签");
                oldTags = new ArrayList<>(Arrays.asList(oldNames));
            }
            // 批量添加自定义标签
            if(newTagsList.size() > 0) {
                tagService.insert(newTagsList);
            }
            // 修改新使用标签的使用次数
            if(existTagsList.size() > 0) {
                tagService.update(existTagsList, 1);
            }
            // 新博客标签集合中还有数据则为新使用标签
            if(newTags.size() > 0) {
                tagService.update(newTags, 1);
            }
            // 修改不再使用标签的使用次数
            if(oldTags.size() > 0) {
                tagService.update(oldTags, -1);
            }
            log.info("类别修改");
            String newCate = blog.getBlogCate();
            String oldCate = oldBlog.getBlogCate();
            if(categoryService.getCate(newCate) == null) {
                log.info("自定义分类");
                categoryService.insert(newCate);
            } else if(!newCate.equals(oldCate)) {
                log.info("分类被修改");
                categoryService.update(newCate, 1);
                categoryService.update(oldCate, -1);
            }
            return true;
        }
        return false;
    }

    /**
     * 浏览量添加；每次访问博客内容调用一次此方法
     * @param id 博客 id
     * @return 返回添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean updateViews(Integer id) {
        // 浏览量随机数增加
        int num = (int)(1 + Math.random() * 12);
        Integer rows = blogDao.updateViews(num, id);
        return rows != null && rows != 0;
    }

    /**
     * 添加博客评论数量
     * @param num 评论数量
     * @param id 博客id
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean updateCommNum(Integer num, Integer id) {
        Integer rows = blogDao.updateCommNum(num, id);
        return rows != null && rows != 0;
    }
}
