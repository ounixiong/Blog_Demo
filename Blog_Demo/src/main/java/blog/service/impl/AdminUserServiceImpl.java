package blog.service.impl;

import blog.dao.AdminUserDao;
import blog.empty.AdminUser;
import blog.service.AdminUserService;
import blog.utils.Field;
import blog.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    protected AdminUserDao adminUserDao;

    /**
     * 获取所有用户信息
     * @return 用户信息
     */
    @Override
    public List<AdminUser> getAll() {
        return adminUserDao.getAll();
    }

    /**
     * 获取指定用户
     * @param id 用户 id
     * @return 用户结果
     */
    @Override
    public AdminUser getUser(Integer id) {
        return adminUserDao.getUserById(id);
    }

    /**
     * 根据用户名获取指定用户
     * @param name 用户名
     * @return 用户
     */
    @Override
    public AdminUser getUser(String name) {
        return adminUserDao.getUserByName(name);
    }

    /**
     * 添加用户
     * @param adminUser 用户
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean insert(AdminUser adminUser) {
        String newPassword = Md5Util.md5Encode(adminUser.getPassWord(), Field.CHARSET);
        adminUser.setPassWord(newPassword);
        Integer rows = adminUserDao.insert(adminUser);
        return rows != null && rows != 0;
    }

    /**
     * 删除用户
     * @param id 用户 id
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean remove(Integer id) {
        Integer rows = adminUserDao.remove(id);
        return rows != null && rows != 0;
    }

    /**
     * 恢复用户
     * @param id 用户id
     * @return 恢复结果       h
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean recover(Integer id) {
        Integer rows = adminUserDao.recover(id);
        return rows != null && rows != 0;
    }

    /**
     * 修改用户：任意属性，包括删除、回复用户以及修改用户权限
     * @param adminUser 用户
     * @return 修改结果
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean update(AdminUser adminUser) {
        Integer rows = adminUserDao.update(adminUser);
        return rows != null && rows != 0;
    }

    /**
     * 获取所有用户名集合
     * @return 用户名集合      y
     */
    @Override
    public String[] get() {
        return adminUserDao.get();
    }
}
