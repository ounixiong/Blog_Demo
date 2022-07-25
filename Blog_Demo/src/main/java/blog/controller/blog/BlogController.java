package blog.controller.blog;

import blog.empty.Blog;
import blog.empty.Comment;
import blog.empty.Link;
import blog.service.*;
import blog.utils.Field;
import blog.utils.Result;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 欧尼熊
 */
@Controller
@Api(tags = "博客管理")
@Slf4j
public class BlogController {

    private static String[] tags;
    private static String[] categories;
    private static List<Blog> newBlog;
    private static List<Blog> viewsBlog;
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    LinkService linkService;

    @Autowired
    CommentDataService commentDataService;

    @ApiOperation(value = "进入博客首页", notes = "进入首页时刷新数据")
    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model) {
        log.info("进入博客首页");
        log.warn("刷新 | 获取最近博客、热门博客");
        newBlog = blogService.getByOrder(Field.TIME);
        viewsBlog = blogService.getByOrder(Field.VIEWS);
        log.warn("刷新 | 获取热门标签、分类");
        PageHelper.startPage(0, 8);
        tags = tagService.getNames();
        PageHelper.startPage(0, 8);
        categories = categoryService.getNames();
        model.addAttribute("blogs", blogService.getBlogs(Field.NORMAL));
        model.addAttribute("newBlog", newBlog);
        model.addAttribute("viewsBlog", viewsBlog);
        model.addAttribute("tags", tags);
        model.addAttribute("categories", categories);
        return "blog/index";
    }

    @ApiOperation("归档页")
    @GetMapping("/archive")
    public String archive(Model model) {
        log.info("归档页，获取所有博客数据");
        List<Blog> blogs = blogService.getBlogs(Field.RELEASE);
        model.addAttribute("blogs", blogs);
        data(model);
        return "blog/archive";
    }

    /**
     * 获取常用标签和类别的数据保存到静态集合中
     * @param model model
     */
    private void data(Model model) {
        if(tags == null || categories == null) {
            log.warn("获取标签、类别数据");
            PageHelper.startPage(0, 8);
            tags = tagService.getNames();
            PageHelper.startPage(0, 8);
            categories = categoryService.getNames();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("categories", categories);
    }

    @ApiOperation("获取博客数据和博客的评论数据，进入博客浏览页面")
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model) {
        blogService.updateViews(id);
        Blog blog = blogService.getBlogById(id);
        log.info("浏览博客，{}", blog);
        List<Comment> comments = commentService.getById(id);
        data(model);
        model.addAttribute("blog", blog);
        model.addAttribute("blogTags", blog.getBlogTagName().trim().split("\\s+"));
        model.addAttribute("comments", comments);
        return "blog/blog";
    }

    @ApiOperation("添加评论")
    @PostMapping("/blog/comment")
    @ResponseBody
    public Map<String, Object> comment(Comment comment, String blogTitle) {
        log.info("开始添加留言 - {}", comment);
        Boolean flag = commentService.insertComm(comment);
        if(flag) {
            // 记录添加留言数量
            commentDataService.setData(comment, blogTitle);
        }
        return Result.result(flag);
    }

    @ApiOperation("关于介绍页面")
    @GetMapping("/about")
    public String about(Model model) {
        PageHelper.startPage(0, 1);
        Blog about = blogService.getBlogById(13);
        model.addAttribute("about", about);
        data(model);
        return "blog/about";
    }


    @ApiOperation("获取友链数据，进入友链页面")
    @GetMapping("/links")
    public String links(Model model) {
        List<Link> friend = linkService.getByType("0");
        List<Link> recommend = linkService.getByType("1");
        data(model);
        model.addAttribute("friend", friend);
        model.addAttribute("recommend", recommend);
        return "blog/links";
    }

    @ApiOperation("根据请求参数获取标签或类别对应博客集合")
    @GetMapping("/blogs")
    public String tagBlog(@RequestParam("variable") String variable, @RequestParam("flag") Boolean flag, Model model) {
        log.info("获取标签或类别对应博客, variable = {}, flag = {}", variable, flag);
        PageHelper.startPage(0, 6);
        List<Blog> blogs = blogService.getBlogs(variable, flag);
        if(blogs.size() == 0) {
            if(flag) {
                model.addAttribute("message", "当前标签没有对应博客");
            } else {
                model.addAttribute("message", "当前分类没有对应博客");
            }
        }
        model.addAttribute("blogs", blogs);
        if(newBlog.size() > 0 && viewsBlog.size() > 0) {
            model.addAttribute("newBlog", newBlog);
            model.addAttribute("viewsBlog", viewsBlog);
        }
        data(model);
        return "blog/index";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        model.addAttribute("message", "你没有权限访问该功能，请联系管理员用户");
        List<Blog> blogs = blogService.getBlogs(Field.NORMAL);
        model.addAttribute("blogs", blogs);
        data(model);
        return "blog/index";
    }

}
