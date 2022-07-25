package blog.service;

import blog.empty.Config;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface ConfigService {

    /**
     * 获取所有配置
     * @return 返回配置列表
     */
    List<Config> getAll();

    /**
     * 添加配置项
     * @param name 配置项
     * @param value 配置项值
     * @return 添加结果
     */
    Boolean insert(String name, String value);

    /**
     * 根据配置项名称获取对应配置内容
     * @param name 配置项名称
     * @return 配置项列表
     */
    List<Config> getByName(String name);

    /**
     * 根据配置项 id 修改配置信息
     * @param config 配置项 id
     * @return 返回修改结果
     */
    Boolean update(Config config);

}
