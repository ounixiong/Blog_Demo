package blog.dao;

import blog.empty.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface CategoryDao {

    /**
     * 获取所有类别：按照权重排序
     * @return 返回类别集合
     */
    List<Category> get();

    /**
     * 获取所有类别名称集合
     * @return 类别名称集合
     */
    String[] getNames();

    /**
     * 根据 id 获取类别信息
     * @param id 类别 id
     * @return 返回记录
     */
    Category getById(Integer id);

    /**
     * 根据 类别名 获取类别信息
     * @param name 类别 名
     * @return 返回记录
     */
    Category getByName(String name);

    /**
     * 获取类别数量
     * @return 类别总数量
     */
    Integer getCount();

    /**
     * 添加类别
     * @param name 类别名
     * @return 返回添加结果
     */
    Integer insert(String name);

    /**
     * 根据id批量删除分类
     * @param ids 类别 id 集合
     * @return 返回删除结果
     */
    Integer removeByIds(Integer[] ids);

    /**
     * 根据类别名删除分类
     * @param name 类别 id 集合
     * @return 返回删除结果
     */
    Integer removeByName(String name);

    /**
     * 根据类别 id 修改权重值
     * @param cateId 类别
     * @param num 权重值
     * @return 返回修改结果
     */
    Integer updateById(@Param("cateId") Integer cateId,
                       @Param("num") Integer num);

    /**
     * 根据类别名修改权重值
     * @param name 类别名
     * @param num 权重值
     * @return 修改结果
     */
    Integer updateByName(@Param("name") String name,
                         @Param("num") Integer num);


}
