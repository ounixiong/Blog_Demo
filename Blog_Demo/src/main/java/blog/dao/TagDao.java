package blog.dao;

import blog.empty.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface TagDao {

    /**
     * 获取所有标签根据权重排序
     * @return 返回标签记录集合
     */
    List<Tag> get();

    /**
     * 获取所有标签名称
     * @return 返回标签名称集合
     */
    String[] getNames();

    /**
     * 通过标签id获取标签记录
     * @param ids 标签 id 集合
     * @return 返回标签记录集合
     */
    List<Tag> getById(Integer[] ids);

    /**
     * 通过标签名获取标签记录
     * @param name 标签 名 集合
     * @return 返回标签记录集合
     */
    Tag getByName(String name);

    /**
     * 统计标签数量
     * @return 返回标签数量
     */
    Integer getCount();

    /**
     * 批量添加标签；需要标签名称
     * 标签名不能重复，先验证标签名是否存在
     * @param names 标签名集合
     * @return 返回添加结果
     */
    Integer insert(List<String> names);

    /**
     * 批量删除标签
     * @param id 标签 id 集合
     * @return 返回删除结果
     */
    Integer removeByIds(Integer[] id);

    /**
     * 根据标签名删除标签
     * @param names 标签 id 集合
     * @return 返回删除结果
     */
    Integer removeByNames(String[] names);

    /**
     * 根据 id 更改使用次数
     * @param id 标签 id
     * @return 返回修改结果
     */
    Integer updateById(@Param("id") Integer id);

    /**
     * 根据标签名批量修改权重值
     * @param names 标签名
     * @param num 修改值
     * @return 修改结果
     */
    Integer updateByNames(@Param("list") List<String> names,
                          @Param("num") Integer num);



}
