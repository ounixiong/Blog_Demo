package blog.service;

import blog.empty.Comment;
import blog.empty.CommentData;

import java.util.List;

/**
 * @author 欧尼熊
 * @date 2022-07-18 08:57
 */
public interface CommentDataService {

    /**
     * 封装博客未读评论数据数据
     * 以博客id为下标
     * @param comment 评论对象
     * @param blogTitle 博客标题
     */
    void setData(Comment comment, String blogTitle);

    /**
     * 查看后将博客对应记录全部删除
     * @param blogTitle 博客标题
     */
    void setData(String blogTitle);

    /**
     * 回复后将对应博客记录减一
     * @param blogTitle 博客标题
     * @param num 自定义减去数量
     */
    void setData(String blogTitle, Integer num);

    /**
     * 保存记录
     * @param commentData 记录对象列表
     * @return 保存结果
     */
    Boolean save(List<CommentData> commentData);

    /**
     * 删除记录
     * @return 删除结果
     */
    Boolean delete();

    /**
     * 获取记录
     * @return 获取集合
     */
    List<CommentData> get();

}
