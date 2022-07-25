package blog.service;

import blog.empty.Blog;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface BlogService {

    /**
     * 获取所有博客信息：不包含内容
     * @param type '2'-未删除博客，'1'-已删除博客,'0'-已发布且未删除博客, "3"-所有已发布博客
     * @return 博客信息集合
     */
    List<Blog> getBlogs(Character type);

    /**
     * 通过 id 获取博客信息
     * @param id 博客 id
     * @return 博客
     */
    Blog getBlogById(Integer id);

    /**
     * 通过标题模糊搜索博客：不包含内容
     * @param title 标题
     * @return 博客结果
     */
    List<Blog> getBlogByTitle(String title);

    /**
     * 通过标签名或类别名获取博客集合：不含内容
     * @param variable 标签名 或 类别名
     * @param flag 判断是标签或类别,true-标签名，false-类别名
     * @return 博客集合
     */
    List<Blog> getBlogs(String variable, Boolean flag);

    /**
     * 根据参数获取不同排序博客集合：仅获取博客id、标题
     * @param type 排序类型：0-浏览量排序，1-创建时间排序
     * @return 返回博客集合
     */
    List<Blog> getByOrder(Integer type);

    /**
     * 获取博客数量
     * @param flag false-正常博客，true-已删除博客
     * @return 返回博客数量
     */
    Integer getCount(Boolean flag);

    /**
     * 根据指定标签或类别或取对应博客数量
     * @param variable 标签或类别名
     * @param flag true-标签，false-类别
     * @return 对应博客数量
     */
    Integer getCount(String variable, Boolean flag);

    /**
     * 添加博客；需要 博客标题、内容、类别 id、
     * 标签名称（' '分隔字符串）、状态、评论权限
     * @param blog 博客
     * @return 返回添加结果
     */
    Boolean insert(Blog blog);

    /**
     * 删除博客
     * @param id 博客 id
     * @param flag 为true时彻底删除博客，否则将deleted设置1
     * @return 返回删除结果
     */
    Boolean remove(Integer id, Boolean flag);

    /**
     * 恢复博客
     * @param id 博客id
     * @return 恢复结果
     */
    Boolean restore(Integer id);

    /**
     * 修改博客
     * @param blog 博客
     * @return 返回修改结果
     */
    Boolean update(Blog blog);

    /**
     * 浏览量添加；每次访问博客内容调用一次此方法
     * @param id 博客 id
     * @return 返回添加结果
     */
    Boolean updateViews(Integer id);

    /**
     * 添加博客评论数量
     * @param num 评论数量
     * @param id 博客id
     * @return 添加结果
     */
    Boolean updateCommNum(Integer num, Integer id);

}
