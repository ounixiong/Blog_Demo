package blog.service;

import blog.empty.Link;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface LinkService {

    /**
     * 获取所有友链
     * 根据类型分组，根据权重排序
     *
     * @return 返回友链集合
     */
    List<Link> getAll();

    /**
     * 根据友链类型获取友链数据
     *
     * @param type 类型，0-友链 1-推荐
     * @return 友链数据
     */
    List<Link> getByType(String type);

    /**
     * 通过 id 获取友链
     *
     * @param id 友链 id
     * @return 返回友链
     */
    Link getLink(Integer id);

    /**
     * 通过友链名称获取友链
     *
     * @param name 友链名称
     * @return 返回友链
     */
    Link getLink(String name);

    /**
     * 统计链接数量
     *
     * @param type true-正常友链数量，false-所有友链数量
     * @return 返回友链数量
     */
    Integer getCount(Boolean type);

    /**
     * 添加友链；需要 链接类型、网站名称、网站 url、网站描述、链接权重
     * 验证网站名称是否存在
     *
     * @param link 友链
     * @return 返回添加结果
     */
    Boolean insert(Link link);

    /**
     * 批量根据 id 删除链接
     *
     * @param ids 标签 id 集合
     * @return 删除结果
     */
    Boolean remove(Integer[] ids);

    /**
     * 修改链接；可恢复被删除链接
     *
     * @param link 链接
     * @return 返回修改结果
     */
    Boolean update(Link link);

    /**
     * 获取所有链接名称；包括被删除的
     *
     * @return 链接名称集合
     */
    String[] get();
}
