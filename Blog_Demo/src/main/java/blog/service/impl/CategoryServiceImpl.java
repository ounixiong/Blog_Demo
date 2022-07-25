package blog.service.impl;

import blog.dao.CategoryDao;
import blog.empty.Category;
import blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    protected CategoryDao categoryDao;

    /**
     * 获取所有类别：按照权重排序
     *
     * @return 返回类别集合
     */
    @Override
    public List<Category> get() {
        return categoryDao.get();
    }

    /**
     * 获取所有类别名称集合
     *
     * @return 类别名称集合
     */
    @Override
    public String[] getNames() {
        return categoryDao.getNames();
    }

    /**
     * 根据 id 获取类别信息
     *
     * @param id 类别 id
     * @return 返回记录
     */
    @Override
    public Category getCate(Integer id) {
        return categoryDao.getById(id);
    }

    /**
     * 根据类别名查找记录
     *
     * @param name 类别名
     * @return 查找结果
     */
    @Override
    public Category getCate(String name) {
        return categoryDao.getByName(name);
    }

    /**
     * 获取类别数量
     *
     * @return 类别总数量
     */
    @Override
    public Integer getCount() {
        return categoryDao.getCount();
    }

    /**
     * 添加类别；需要类别名称、权重值；
     * 先校验名称是否存在
     *
     * @param name 类别名
     * @return 返回添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(String name) {
        Integer rows = categoryDao.insert(name);
        return rows != null && rows > 0;
    }

    /**
     * 删除分类：将删除状态修改为 1
     *
     * @param ids 类别 id 集合
     * @return 返回删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer[] ids) {
        Integer rows = categoryDao.removeByIds(ids);
        return rows != null && rows > 0;
    }

    /**
     * 根据类别名删除类别
     *
     * @param name 类别名
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(String name) {
        Integer rows = categoryDao.removeByName(name);
        return rows != null && rows > 0;
    }

    /**
     * 根据类别 id 添加使用次数）
     *
     * @param id  类别id
     * @param num 修改权重值
     * @return 返回修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(Integer id, Integer num) {
        Integer rows = categoryDao.updateById(id, num);
        return rows != null && rows > 0;
    }

    /**
     * 跟据类别名添加使用次数
     *
     * @param num  修改权重值
     * @param name 类别名
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(String name, Integer num) {
        Integer rows = categoryDao.updateByName(name, num);
        return rows != null && rows > 0;
    }
}
