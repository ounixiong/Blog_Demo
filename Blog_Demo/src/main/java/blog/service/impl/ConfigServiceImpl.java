package blog.service.impl;

import blog.dao.ConfigDao;
import blog.empty.Config;
import blog.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    protected ConfigDao configDao;

    @Override
    public List<Config> getAll() {
        return configDao.getAll();
    }

    /**
     * 添加配置项
     * @param name 配置项
     * @param value 配置项值
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(String name, String value) {
        Integer rows = configDao.insert(name, value);
        return rows != null && rows > 0;
    }

    /**
     * 根据配置项名称获取对应配置内容
     * @param name 配置项名称
     * @return 配置项列表
     */
    @Override
    public List<Config> getByName(String name) {
        return configDao.getByName(name);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(Config config) {
        Integer rows = configDao.update(config);
        return rows != null && rows != 0;
    }
}
