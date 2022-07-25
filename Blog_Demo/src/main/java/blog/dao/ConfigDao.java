package blog.dao;

import blog.empty.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface ConfigDao {

    /**
     * 获取所有配置
     * @return 返回配置列表
     */
    List<Config> getAll();

    /**
     * 添加配置项
     * @param name 配置项
     * @param value 配置项值
     * @return 返回添加结果
     */
    Integer insert(@Param("name") String name,
                   @Param("value") String value);

    /**
     * 获取指定配置项内容
     * @param name 配置项名称
     * @return 配置项列表
     */
    List<Config> getByName(String name);

    /**
     * 根据配置项 id 修改配置信息
     * @param config 配置项 id
     * @return 返回修改结果
     */
    Integer update(Config config);

}
