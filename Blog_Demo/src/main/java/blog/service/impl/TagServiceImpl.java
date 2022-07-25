package blog.service.impl;

import blog.dao.TagDao;
import blog.empty.Tag;
import blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    protected TagDao tagDao;


    /**
     * 获取所有标签根据权重排序
     *
     * @return 返回标签记录集合
     */
    @Override
    public List<Tag> get() {
        return tagDao.get();
    }

    /**
     * 获取所有标签名称
     *
     * @return 返回标签名称集合
     */
    @Override
    public String[] getNames() {
        return tagDao.getNames();
    }

    /**
     * 通过标签id获取标签记录
     *
     * @param ids 标签 id 集合
     * @return 返回标签记录集合
     */
    @Override
    public List<Tag> getTags(Integer[] ids) {
        return tagDao.getById(ids);
    }

    /**
     * 通过标签名获取标签对象
     *
     * @param name 标签名
     * @return 标签对象
     */
    @Override
    public Tag getTag(String name) {
        return tagDao.getByName(name);
    }

    /**
     * 统计标签数量
     *
     * @return 返回标签数量
     */
    @Override
    public Integer getCount() {
        return tagDao.getCount();
    }

    /**
     * 批量添加标签；需要标签名称
     * 标签名不能重复，先验证标签名是否存在
     *
     * @param names 标签名集合
     * @return 返回添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(List<String> names) {
        Integer rows = tagDao.insert(names);
        return rows != null && rows > 0;
    }

    /**
     * 批量删除标签
     *
     * @param ids 标签 id 集合
     * @return 返回删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer[] ids) {
        Integer rows = tagDao.removeByIds(ids);
        return rows != null && rows > 0;
    }

    /**
     * 根据标签名删除标签
     *
     * @param names 标签 id 集合
     * @return 返回删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(String[] names) {
        Integer rows = tagDao.removeByNames(names);
        return rows != null && rows > 0;
    }

    /**
     * 根据 id 修改标签，添加使用次数
     *
     * @param id 标签 id
     * @return 返回修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(Integer id) {
        Integer rows = tagDao.updateById(id);
        return rows != null && rows > 0;
    }

    /**
     * 根据标签名批量修改使用次数
     *
     * @param names 标签名集合
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(List<String> names, Integer num) {
        Integer rows = tagDao.updateByNames(names, num);
        return rows != null && rows > 0;
    }
}
