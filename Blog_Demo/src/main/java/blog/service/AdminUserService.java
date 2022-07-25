package blog.service;

import blog.empty.AdminUser;

import java.util.List;

/**
 * @author 欧尼熊
 */
public interface AdminUserService {

    /**
     * 获取所有用户信息
     *
     * @return 用户信息
     */
    List<AdminUser> getAll();

    /**
     * 获取用户对象
     *
     * @param id 用户 id
     * @return 用户对象
     */
    AdminUser getUser(Integer id);

    /**
     * 获取用户对象
     *
     * @param name 用户名
     * @return 用户对象
     */
    AdminUser getUser(String name);

    /**
     * 添加用户
     *
     * @param adminUser 用户
     * @return 添加结果
     */
    Boolean insert(AdminUser adminUser);

    /**
     * 删除用户
     *
     * @param id 用户 id
     * @return 删除结果
     */
    Boolean remove(Integer id);

    /**
     * 恢复用户
     *
     * @param id 用户id
     * @return 恢复结果       h
     */
    Boolean recover(Integer id);

    /**
     * 修改用户：可以恢复锁定用户
     *
     * @param adminUser 用户
     * @return 修改结果
     */
    Boolean update(AdminUser adminUser);

    /**
     * 获取所有用户的 用户名 集合：包括被锁定用户
     *
     * @return 全部用户名集合
     */
    String[] get();
}
