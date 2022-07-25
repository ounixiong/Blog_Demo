package blog.dao;

import blog.empty.CommentData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧尼熊
 * @date 2022-07-20 23:34
 */
@Mapper
public interface CommentDataDao {

    /**
     * 获取所有保存的记录
     * @return 执行结果
     */
    List<CommentData> getAll();

    /**
     * 将数据保存到数据库中
     * @param commentData 记录集合
     * @return 添加结果
     */
    Integer insert(List<CommentData> commentData);

    /**
     * 清除所有记录
     * @return 执行结果
     */
    Integer delete();

}
