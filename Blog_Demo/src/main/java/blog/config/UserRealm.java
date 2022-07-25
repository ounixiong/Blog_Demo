package blog.config;

import blog.empty.AdminUser;
import blog.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 欧尼熊
 * shiro 权限认证
 */

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    protected AdminUserService adminUserService;

    /**
     * 授权
     *
     * @param principalCollection 用户集
     * @return 返回授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进行授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 得到认证时传入的用户对象（或用户名、id 等对象）
        AdminUser user = (AdminUser) principalCollection.getPrimaryPrincipal();
        // 获取用户角色信息并添加角色（或使用授权）
        info.addRole(user.getRoles());
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken 认证令牌
     * @return 认证信息
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进行认证");
        String username = (String) authenticationToken.getPrincipal();
        AdminUser user = adminUserService.getUser(username);
        if (user == null) {
            // 用户不存在返回空，自动捕捉异常
            return null;
        }
        log.info("姓名合法，开始认证");
        return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
    }
}
