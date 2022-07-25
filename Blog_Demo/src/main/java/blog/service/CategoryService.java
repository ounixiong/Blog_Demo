package blog.service;

import blog.empty.Category;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface CategoryService {

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
    Category getCate(Integer id);

    /**
     * 根据类别名查找记录
     * @param name 类别名
     * @return 查找结果
     */
    Category getCate(String name);

    /**
     * 获取类别数量
     * @return 类别总数量
     */
    Integer getCount();

    /**
     * 添加类别；需要类别名称
     * @param name 类别名
     * @return 返回添加结果
     */
    Boolean insert(String name);

    /**
     * 删除分类
     * @param ids 类别 id 集合
     * @return 返回删除结果
     */
    Boolean remove(Integer[] ids);

    /**
     * 根据类别名删除类别
     * @param name 类别名
     * @return 删除结果
     */
    Boolean remove(String name);

    /**
     * 根据类别 id 添加使用次数）
     * @param id 类别id
     * @param num 修改权重值
     * @return 返回修改结果
     */
    Boolean update(Integer id, Integer num);

    /**
     * 跟据类别名添加使用次数
     * @param name 类别名
     * @param num 修改权重值
     * @return 修改结果
     */
    Boolean update(String name, Integer num);


}
