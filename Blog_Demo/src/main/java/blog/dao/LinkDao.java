package blog.dao;

import blog.empty.Link;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface LinkDao {

    /**
     * 获取所有友链，根据权重排序
     *
     * @return 返回友链集合
     */
    List<Link> getAll();

    /**
     * 通过类型获取友链数据t
     *
     * @param type 友链类型 0-友链，1-推荐
     * @return 友链集合
     */
    List<Link> getByType(String type);

    /**
     * 通过 id 获取友链
     *
     * @param id 友链 id
     * @return 返回友链
     */
    Link getById(Integer id);

    /**
     * 通过友链名称获取友链
     *
     * @param names 友链名称
     * @return 返回友链
     */
    Link getByName(String names);

    /**
     * 统计链接数量
     *
     * @return 返回友链数量
     */
    Integer getCount();

    /**
     * 获取所有友链数量，包括已删除
     *
     * @returny 友链数量
     */
    Integer getAllCount();

    /**
     * 添加友链；需要 链接类型、网站名称、网站 url、网站描述、链接权重
     * 验证网站名称是否存在
     *
     * @param link 友链
     * @return 返回添加结果
     */
    Integer insert(Link link);

    /**
     * 批量根据 id 删除链接
     *
     * @param ids 标签 id 集合
     * @return 删除结果
     */
    Integer remove(Integer[] ids);

    /**
     * 修改链接；可恢复被删除链接
     *
     * @param link 链接
     * @return 返回修改结果
     */
    Integer update(Link link);

    /**
     * 获取所有链接名称；包括被删除的
     *
     * @return 链接名称集合
     */
    String[] get();
}
