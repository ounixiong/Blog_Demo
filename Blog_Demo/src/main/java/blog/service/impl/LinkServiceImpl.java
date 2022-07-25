package blog.service.impl;

import blog.dao.LinkDao;
import blog.empty.Link;
import blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    protected LinkDao linkDao;

    @Override
    public List<Link> getAll() {
        return linkDao.getAll();
    }

    /**
     * 根据友链类型获取友链数据
     *
     * @param type 类型，0-友链 1-推荐
     * @return 友链数据
     */
    @Override
    public List<Link> getByType(String type) {
        return linkDao.getByType(type);
    }

    @Override
    public Link getLink(Integer id) {
        return linkDao.getById(id);
    }

    @Override
    public Link getLink(String name) {
        return linkDao.getByName(name);
    }

    @Override
    public Integer getCount(Boolean type) {
        if (type) {
            return linkDao.getCount();
        } else {
            return linkDao.getAllCount();
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(Link link) {
        Integer rows = linkDao.insert(link);
        return rows != null && rows != 0;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer[] ids) {
        Integer rows = linkDao.remove(ids);
        return rows != null && rows != 0;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(Link link) {
        Integer rows = linkDao.update(link);
        return rows != null && rows != 0;
    }

    @Override
    public String[] get() {
        return linkDao.get();
    }
}
