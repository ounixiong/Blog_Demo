package blog.controller.admin;

import blog.empty.AdminUser;
import blog.empty.Config;
import blog.empty.Link;
import blog.service.ConfigService;
import blog.service.LinkService;
import blog.utils.Field;
import blog.utils.Result;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 欧尼熊
 * @date 2022-06-23 21:55
 *         后台页面的通用controller
 */
@Controller
@Slf4j
@RequestMapping("/admin")
public class CommentManagerController {

    @Autowired
    LinkService linkService;

    @Autowired
    ConfigService configService;

    @ApiOperation(value = "友链管理")
    @GetMapping("/links")
    public String links() {
        log.info("友链管理");
        return "admin/links";
    }

    @ApiOperation("系统设置页面")
    @GetMapping("/setting")
    public String setting(Model model) {
        log.info("系统设置");
        if(Field.USER.size() == 0) {
            Field.USER.add((AdminUser)SecurityUtils.getSubject().getPrincipal());
        }
        model.addAttribute("user", Field.USER.get(0));
        return "admin/setting";
    }

    @ApiOperation(value = "获取指定类型配置项")
    @PostMapping("/user/setting/{config}")
    @ResponseBody
    public Map<String, Object> setting(@PathVariable("config") String config) {
        log.info("获取 {} 类型配置", config);
        List<Config> configs = configService.getByName(config);
        return Result.result(configs != null && configs.size() != 0, configs.size(), configs);
    }

    @ApiOperation(value = "友链数据", notes = "layui数据接口")
    @GetMapping("/linkData")
    @ResponseBody
    public Map<String, Object> linkData(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Link> links = linkService.getAll();
        return Result.result(links != null && links.size() > 0, linkService.getCount(false), links);
    }

    @ApiOperation("添加友链")
    @PostMapping("/addLink")
    @ResponseBody
    public Map<String, Object> addLink(Link link) {
        log.info("添加友链，link = {}", link);
        return Result.result(linkService.insert(link));
    }

    @ApiOperation("修改友链")
    @PostMapping("/updateLink")
    @ResponseBody
    public Map<String, Object> updateLink(Link link) {
        log.info("修改友链，link = {}", link);
        return Result.result(linkService.update(link));
    }

    @ApiOperation("删除友链")
    @PostMapping("/deleteLink")
    @ResponseBody
    public Map<String, Object> deleteLink(Integer linkId) {
        log.info("删除友链，友链id = {}", linkId);
        return Result.result(linkService.remove(new Integer[] {linkId}));
    }

    @ApiOperation(value = "获取博客评论添加数据")
    @PostMapping("/commentData")
    @ResponseBody
    public Object commentData() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", Field.COMM_DATA);
        map.put("length", Field.COMM_DATA.size());
        return map;
    }

}
