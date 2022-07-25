package blog.service.impl;

import blog.dao.CommentDataDao;
import blog.empty.Comment;
import blog.empty.CommentData;
import blog.service.CommentDataService;
import blog.utils.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 * @date 2022-07-18 09:09
 */
@Service
public class CommentDataServiceImpl implements CommentDataService {

    @Autowired
    CommentDataDao commentDataDao;

    /**
     * 封装博客未读评论数据
     * 以博客id为下标填充数据
     * @param comment 评论对象
     * @param blogTitle 博客标题
     */
    @Override
    public void setData(Comment comment, String blogTitle) {
        CommentData data = Field.COMM_DATA.get(blogTitle);
        // 已存在数据，数量 +1
        if(data != null) {
            data.setNum(data.getNum() + 1);
            Field.COMM_DATA.put(blogTitle, data);
        } else {
            data = new CommentData();
            data.setBlogTitle(blogTitle);
            data.setBlogId(comment.getBlogId());
            data.setNum(1);
            Field.COMM_DATA.put(blogTitle, data);
        }
    }

    /**
     * 查看后将对应博客记录全部删除
     * @param blogTitle 博客标题
     */
    @Override
    public void setData(String blogTitle) {
        Field.COMM_DATA.remove(blogTitle);
    }

    /**
     * 回复后将对应博客记录减去指定数量
     * @param blogTitle 博客标题
     * @param num 自定义减去数量
     */
    @Override
    public void setData(String blogTitle, Integer num) {
        CommentData data = Field.COMM_DATA.get(blogTitle);
        if(data != null) {
            // 当前记录删除指定数量后不为0
            if(data.getNum() - num >= Field.TIME) {
                data.setNum(data.getNum() - num);
                Field.COMM_DATA.put(blogTitle, data);
            } else {
                // 否则删除记录
                Field.COMM_DATA.remove(blogTitle);
            }
        }
    }

    /**
     * 保存记录
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean save(List<CommentData> commentData) {
        Integer rows = commentDataDao.insert(commentData);
        return rows != null && rows > 0;
    }

    /**
     * 删除记录
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean delete() {
        Integer rows = commentDataDao.delete();
        return rows != null && rows > 0;
    }

    /**
     * 获取记录
     * @return 获取集合
     */
    @Override
    public List<CommentData> get() {
        return commentDataDao.getAll();
    }
}
