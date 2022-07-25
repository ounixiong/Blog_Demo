package blog.dao;

import blog.empty.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface BlogDao {

    /**
     * 获取所有未删除博客信息：不包含内容
     * @return 返回博客列表
     */
    List<Blog> getUnDeleted();

    /**
     * 获取所有已删除博客
     * @return 已删除博客列表
     */
    List<Blog> getDeleted();

    /**
     * 获取所有已发布未删除博客信息
     * @return 博客列表
     */
    List<Blog> getNormal();

    /**
     * 获取所有已发布博客
     * @return 已发布博客集合      u
     */
    List<Blog> getRelease();

    /**
     * 根据 id 获取指定博客
     * @param id 博客 id
     * @return 返回指定博客
     */
    Blog getBlogById(Integer id);

    /**
     * 通过标题模糊查询博客集合；不包括内容
     * @param name 博客名称
     * @return 返回博客集合
     */
    List<Blog> getBlogByName(String name);

    /**
     * 根据标签名获取博客记录集：不含内容
     * @param name 标签名
     * @return 博客集合
     */
    List<Blog> getByTag(String name);

    /**
     * 根据类别名查询博客集合：不含内容
     * @param blogCate 类别
     * @return 博客集合
     */
    List<Blog> getBlogByCate(String blogCate);

    /**
     * 获得博客集合：根据浏览量排序前 5
     * 仅获取博客id、标题
     * @return 返回博客集合
     */
    List<Blog> getByViews();

    /**
     * 根据创建时间排序：博客标题、id
     * @return 返回最新创建的 5 篇博客
     */
    List<Blog> getByTimes();

    /**
     * 获取博客数量
     * @param type '0'-正常，'1'-已删除
     * @return 返回博客数量
     */
    Integer getCount(Character type);

    /**
     * 获取指定标签博客总数量
     * @param name 标签名
     * @return 指定标签博客数量
     */
    Integer getTagBlogCount(String name);

    /**
     * 获取指定类别标签总数
     * @param blogCate 类别名
     * @return 指定类别标签总数
     */
    Integer getCateBlogCount(String blogCate);

    /**
     * 添加博客；需要 博客标题、内容、类别 id、
     * 标签名称（' '分隔字符串）、状态、评论权限
     * @param blog 博客
     * @return 返回添加结果
     */
    Integer insert(Blog blog);

    /**
     * 删除博客
     * @param id 博客 id
     * @return 返回删除结果
     */
    Integer remove(Integer id);

    /**
     * 恢复博客
     * @param id 博客 id
     * @return 返回恢复结果
     */
    Integer restore(Integer id);

    /**
     * 彻底删除博客
     * @param id 博客 id
     * @return 删除结果
     */
    Integer deleted(Integer id);

    /**
     * 修改博客
     * @param blog 博客
     * @return 返回修改结果
     */
    Integer update(Blog blog);

    /**
     * 浏览量添加；每次访问博客内容调用一次此方法
     * @param id 博客 id
     * @param num 增加的浏览量
     * @return 返回添加结果
     */
    Integer updateViews(@Param("num") Integer num,
                        @Param("blogId") Integer id);

    /**
     * 添加评论数量
     * @param num 评论数量
     * @param id 博客id
     * @return 添加结果
     */
    Integer updateCommNum(@Param("num") Integer num,
                          @Param("blogId") Integer id);


}
