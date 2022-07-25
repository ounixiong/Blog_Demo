package blog.controller.admin;

import blog.empty.AdminUser;
import blog.service.*;
import blog.utils.Field;
import blog.utils.Md5Util;
import blog.utils.Result;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 欧尼熊
 *         用户管理
 */
@Slf4j
@Api(tags = "后台用户管理")
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 判断remember功能是否开启
     */
    @Autowired
    AdminUserService adminUserService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    LinkService linkService;

    @ApiOperation("登录请求")
    @GetMapping("/toLogin")
    public String toLogin() {
        log.info("处理登录请求");
        return "admin/login";
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(String username, String password, String remember) {
        log.info("执行登录操作");
        // 对前台输入密码进行加密
        password = Md5Util.md5Encode(password, "UTF-8");
        Subject subject = SecurityUtils.getSubject();
        log.info("获取参数； username = {}，password = {}， remember = {}", username, password, remember);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        if(Field.REMEMBER.equals(remember)) {
            token.setRememberMe(true);
        }
        try {
            log.info("开始登录：username = {}, remember = {}， token = {}", username, remember, token);
            subject.login(token);
            if(Field.USER.size() == 0) {
                log.info("添加元素");
                Field.USER.add((AdminUser)SecurityUtils.getSubject().getPrincipal());
            } else {
                Field.USER.set(0, (AdminUser)SecurityUtils.getSubject().getPrincipal());
            }
            log.info("登录成功，进入博客首页, user = {}", Field.USER.get(0));
            return Result.result("登录成功", 1);
        } catch( UnknownAccountException e ) {
            log.error("用户名错误");
            return Result.result("用户名错误", 2);
        } catch( IncorrectCredentialsException e ) {
            log.error("密码错误");
            return Result.result("密码错误", 2);
        }
    }

    @ApiOperation("返回当前用户信息到页面")
    @PostMapping("/user/userInfo")
    @ResponseBody
    public AdminUser userInfo() {
        if(Field.USER.size() == 0) {
            log.info("添加元素");
            Field.USER.add((AdminUser)SecurityUtils.getSubject().getPrincipal());
        }
        log.info("获取当前用户信息, {}", Field.USER.get(0));
        return Field.USER.get(0);
    }

    @ApiOperation("已认证后直接进入首页")
    @GetMapping({"/", "/index", "", "/index.html"})
    public String index(Model model) {
        log.info("处理进入后台请求");
        if(Field.USER.size() == 0) {
            log.info("添加元素");
            Field.USER.add((AdminUser)SecurityUtils.getSubject().getPrincipal());
        }
        log.info("user = {}", Field.USER.get(0));
        indexModel(model);
        return "admin/index";
    }

    /**
     * 首页所需要的数据
     * @param model 数据
     */
    private void indexModel(Model model) {
        log.info("获取博客后台首页数据");
        Integer blogCount = blogService.getCount(false);
        Integer cateCount = categoryService.getCount();
        Integer tagCount = tagService.getCount();
        Integer linkCount = linkService.getCount(false);
        log.info("blogCount = {}, cateCount = {}, tagCount = {}, linkCount = {}", blogCount, cateCount, tagCount, linkCount);
        model.addAttribute("blogCount", blogCount);
        model.addAttribute("cateCount", cateCount);
        model.addAttribute("tagCount", tagCount);
        model.addAttribute("linkCount", linkCount);
    }

    @ApiOperation("注册请求")
    @GetMapping("/toRegister")
    public String toRegister() {
        log.info("处理注册请求");
        return "admin/register";
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> register(AdminUser adminUser) {
        log.info("执行注册操作");
        Boolean flag = adminUserService.insert(adminUser);
        return Result.result(flag, "注册成功", "注册失败");
    }

    @ApiOperation("用户管理")
    @GetMapping("/user")
    public String user() {
        return "admin/user";
    }


    @ApiOperation("账号数据，layui数据接口")
    @ResponseBody
    @GetMapping("/userData")
    public Map<String, Object> userData(Integer limit, Integer page) {
        log.info("获取用户数据");
        PageHelper.startPage(page, limit);
        List<AdminUser> users = adminUserService.getAll();
        return Result.result(users != null && users.size() > 0, adminUserService.get().length, users);
    }


    @ApiOperation("修改用户信息")
    @PostMapping("/user/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(AdminUser user, String oldPassWord, String newPassWord) {
        log.info("修改用户信息. user = {}, 原密码 = {} 新密码 = {}", user, oldPassWord, newPassWord);
        if(!Md5Util.md5Encode(oldPassWord, Field.CHARSET).equals(user.getPassWord())) {
            return Result.result("密码错误", 2);
        }
        AdminUser oldUser = adminUserService.getUser(user.getUserName());
        // 此用户名是否被使用
        if(oldUser != null) {
            // 此用户名不是被当前用户使用
            if(!oldUser.getUserId().equals(user.getUserId())) {
                return Result.result("用户名已存在", 2);
            }
        }
        if(newPassWord != null && !"".equals(newPassWord)) {
            user.setPassWord(Md5Util.md5Encode(newPassWord, Field.CHARSET));
        }
        Boolean flag = adminUserService.update(user);
        return Result.result(flag);
    }

    @ApiOperation("修改用户头像")
    @PostMapping("/user/updateAvatar")
    @ResponseBody
    public Map<String, Object> updateAvatar(AdminUser user) {
        log.info("修改用户头像, user = {}", user);
        Boolean flag = adminUserService.update(user);
        if(flag) {
            AdminUser adminUser = Field.USER.get(0);
            adminUser.setAvatar(user.getAvatar());
            Field.USER.set(0, adminUser);
        }
        return Result.result(flag);
    }

    @ApiOperation("锁定用户")
    @ResponseBody
    @PostMapping("/roles/deleted/{userId}")
    public Map<String, Object> userDeleted(@PathVariable("userId") Integer id) {
        log.info("锁定用户， userId = {}", id);
        return Result.result(adminUserService.remove(id));
    }

    @ApiOperation("用户恢复")
    @ResponseBody
    @PostMapping("/roles/recover/{userId}")
    public Map<String, Object> userRecover(@PathVariable("userId") Integer id) {
        log.info("恢复用户， userId = {}", id);
        return Result.result(adminUserService.recover(id));
    }


    @ApiOperation("修改用户权限")
    @ResponseBody
    @PostMapping("/roles/update")
    public Map<String, Object> rolesUpdate(AdminUser adminUser) {
        log.info("修改用户权限， adminUser = {}", adminUser);
        Boolean flag = adminUserService.update(adminUser);
        if(flag) {
            AdminUser user = Field.USER.get(0);
            user.setRoles(adminUser.getRoles());
            Field.USER.set(0, user);
        }
        return Result.result(flag);
    }

}
