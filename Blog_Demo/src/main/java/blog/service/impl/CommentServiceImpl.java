package blog.service.impl;

import blog.dao.CommentDao;
import blog.empty.Comment;
import blog.service.BlogService;
import blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    protected CommentDao commentDao;

    @Autowired
    BlogService blogService;

    /**
     * 获取评论
     * @param id 博客 id
     * @return 返回博客对应的评论，评论时间排序
     */
    @Override
    public List<Comment> getById(Integer id) {
        return commentDao.getById(id);
    }

    /**
     * 根据评论id获取评论对象
     * @param id 评论id
     * @return 评论对象
     */
    @Override
    public Comment getByCommId(Integer id) {
        return commentDao.getByCommId(id);
    }

    /**
     * 根据评论id获取博客标题
     * @param id 评论id
     * @return 博客标题
     */
    @Override
    public String getBlogTitle(Integer id) {
        return commentDao.getBlogTitle(id);
    }

    /**
     * 获取指定博客所有评论
     * @param id 博客id
     * @return 已删除评论集合
     */
    @Override
    public List<Comment> getAll(Integer id) {
        return commentDao.getAllById(id);
    }

    /**
     * 获取评论数量
     * @return 评论数量
     */
    @Override
    public Integer getCount() {
        return commentDao.getCount();
    }

    /**
     * 获取对应博客评论数量
     * @param blogId 博客 id
     * @return 对应博客评论数量
     */
    @Override
    public Integer getCount(Integer blogId) {
        return commentDao.getCountByBlogId(blogId);
    }

    /**
     * 添加评论；根据博客 id，需要 博客 id、评论人、评论内容
     * @param comment 评论
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insertComm(Comment comment) {
        Integer rows = commentDao.insertComm(comment);
        blogService.updateCommNum(1, comment.getBlogId());
        return rows != null && rows != 0;
    }

    /**
     * 添加回复；根据评论 id，需要回复内容
     * @param comment 评论
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insertReply(Comment comment) {
        Integer rows = commentDao.insertReply(comment);
        return rows != null && rows != 0;
    }

    /**
     * 删除评论：将删除状态修改为 1
     * @param ids 评论 id 集合
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer[] ids) {
        Integer rows = commentDao.remove(ids);
        return rows != null && rows != 0;
    }

}
