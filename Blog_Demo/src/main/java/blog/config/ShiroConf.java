package blog.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 欧尼熊
 * Shiro 配置
 */
@Slf4j
@Configuration
public class ShiroConf {

    /**
     * 将 realm 对象注入容器
     *
     * @return 注入容器
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * session 管理，防止重定向跳转的 url 追加 JSESSIONID
     *
     * @return 管理器对象
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionIdUrlRewritingEnabled(false);
        return manager;
    }

    /**
     * 设置cookie，实现 remember me 功能
     *
     * @return cookie
     */
    @Bean
    public SimpleCookie rememberCookie() {
        SimpleCookie cookie = new SimpleCookie("remember");
        cookie.setMaxAge(60 * 60 * 24 * 3);
        return cookie;
    }

    /**
     * cookie 管理对象
     *
     * @return remember 管理对象
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager meManager = new CookieRememberMeManager();
        meManager.setCookie(rememberCookie());
        // 设置加密密钥
        meManager.setCipherKey(Base64.getDecoder().decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return meManager;
    }


    /**
     * 注册安全管理器关联 realm 对象
     *
     * @return 注入容器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        manager.setSessionManager(sessionManager());
        manager.setRememberMeManager(cookieRememberMeManager());
        return manager;
    }

    /**
     * shiro 认证过滤器
     *
     * @return 过滤器工厂bean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 关联安全管理器
        bean.setSecurityManager(securityManager());
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        // 配置自定义 or 角色 认证
        filtersMap.put("roles", new RoleFilter());
        bean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 静态资源放行
        filterChainDefinitionMap.put("/resources/**", "anon");
        // 对登录、注册请求不进行拦截
        filterChainDefinitionMap.put("/admin/toLogin", "anon");
        filterChainDefinitionMap.put("/admin/login", "anon");
        filterChainDefinitionMap.put("/admin/toRegister", "anon");
        filterChainDefinitionMap.put("/admin/register", "anon");
        // 登录后可获取用户信息
        filterChainDefinitionMap.put("/admin/user/**", "user");
        // filterChainDefinitionMap.put("/admin/roles/**", "roles[boss]");
        // admin 或 boss 权限都可以访问admin路径
        filterChainDefinitionMap.put("/admin/**", "roles[admin, boss]");
        // 退出，remember状态下会清除 cookie
        filterChainDefinitionMap.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        bean.setLoginUrl("/admin/toLogin");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorized");
        return bean;
    }

    /**
     * 关联 shiro 与 thymeleaf
     *
     * @return shiro 方言
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
