package blog.dao;

import blog.empty.AdminUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 欧尼熊
 */
@Mapper
public interface AdminUserDao {

    /**
     * 获取所有用户
     * @return 用户集合
     */
    List<AdminUser> getAll();

    /**
     * 获取用户记录
     * @param id 用户 id
     * @return 用户对象
     */
    AdminUser getUserById(Integer id);

    /**
     * 获取用户
     * @param name 用户名
     * @return 用户对象
     */
    AdminUser getUserByName(String name);

    /**
     * 添加用户；需要 用户名、密码、昵称；
     * 先验证用户名是否存在
     * @param user 用户对象
     * @return 返回结果
     */
    Integer insert(AdminUser user);

    /**
     * 删除用户：将账户状态修改为 1 锁定
     * @param id 用户 id
     * @return 返回删除结果
     */
    Integer remove(Integer id);

    /**
     * 恢复用户
     * @param id 用户 id
     * @return 恢复结果        h
     */
    Integer recover(Integer id);

    /**
     * 修改用户；可以解锁账号,修改权限
     * @param adminUser 用户
     * @return 返回修改结果
     */
    Integer update(AdminUser adminUser);

    /**
     * 更新用户头像
     * @param adminUser 用户对象
     * @return 更新结果
     */
    Integer updateAvatar(AdminUser adminUser);

    /**
     * 获取用户名集合；包括被锁定用户
     * @return 用户名集合
     */
    String[] get();

}
