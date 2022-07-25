package blog.service;

import blog.empty.Comment;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface CommentService {

    /**
     * 获取评论
     * @param id 博客 id
     * @return 返回博客对应的评论，评论时间排序
     */
    List<Comment> getById(Integer id);

    /**
     * 根据评论id获取评论对象
     * @param id 评论id
     * @return 评论对象
     */
    Comment getByCommId(Integer id);

    /**
     * 根据评论id获取博客标题
     * @param id 评论id
     * @return 博客标题
     */
    String getBlogTitle(Integer id);

    /**
     * 获取指定博客所有评论
     * @param id 博客id
     * @return 已删除评论集合
     */
    List<Comment> getAll(Integer id);

    /**
     * 获取评论数量
     * @return 评论数量
     */
    Integer getCount();

    /**
     * 获取对应博客评论数量
     * @param blogId 博客 id
     * @return 对应博客评论数量
     */
    Integer getCount(Integer blogId);

    /**
     * 添加评论；根据博客 id，需要 博客 id、评论人、评论内容
     * @param comment 评论
     * @return 添加结果
     */
    Boolean insertComm(Comment comment);

    /**
     * 添加评论；根据评论 id，需要回复内容
     * @param comment 评论
     * @return 添加结果
     */
    Boolean insertReply(Comment comment);

    /**
     * 删除评论：将删除状态修改为 1
     * @param ids 评论 id 集合
     * @return 删除结果
     */
    Boolean remove(Integer[] ids);

}
