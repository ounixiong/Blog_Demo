package blog.controller.admin;

import blog.empty.Blog;
import blog.empty.Category;
import blog.empty.Comment;
import blog.empty.Tag;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 欧尼熊
 */
@Slf4j
@Api(tags = "后台博客管理")
@Controller
@RequestMapping("/admin/blog")
public class BlogManagerController {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentService commentService;

    @Autowired
    CommentDataService commentDataService;

    @ApiOperation("创建博客请求")
    @GetMapping("/create")
    public String toCreate() {
        log.info("处理创建博客请求");
        return "admin/manager";
    }

    @ApiOperation("博客列表")
    @GetMapping("/blogs")
    public String blogs() {
        log.info("进入博客管理");
        return "admin/blogs";
    }

    @ApiOperation("分类列表")
    @GetMapping("/categories")
    public String categories() {
        log.info("进入类别管理");
        return "admin/categories";
    }

    @ApiOperation("标签列表")
    @GetMapping("/tags")
    public String tags() {
        log.info("进入标签管理");
        return "admin/tags";
    }

    @ApiOperation("博客评论管理")
    @GetMapping("/comment")
    public String comment(Model model) {
        log.info("进入博客评论管理");
        model.addAttribute("commentData", Field.COMM_DATA);
        return "admin/comment";
    }

    @ApiOperation("回收站")
    @GetMapping("/recycle")
    public String recycle() {
        log.info("进入回收站");
        return "admin/recycle";
    }


    @ApiOperation("博客管理页面使用iframe框架加载写博客功能页面")
    @GetMapping("/createDate")
    public String createDate(Model model) {
        log.info("内联加载写博客子页面");
        PageHelper.startPage(0, 6);
        // 获取使用次数前六的分类
        String[] categories = categoryService.getNames();
        PageHelper.startPage(0, 6);
        // 获取使用次数前六的标签
        String[] tags = tagService.getNames();
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tags);
        return "admin/createDate";
    }

    @ApiOperation(value = "博客管理操作", notes = "博客id为空时进行添加博客操作，否则为修改博客")
    @PostMapping("/manager")
    @ResponseBody
    public Map<String, Object> blogManager(Blog blog, @RequestParam(value = "content-markdown-doc", required = false) String markdown) {
        log.info("进行博客添加或修改操作，接收数据 | {}", blog);
        // 设置内容
        if(markdown != null) {
            blog.setBlogContent(markdown);
        }
        // 当接收数据不含博客id时证明为添加新博客
        if(blog.getBlogId() == null) {
            log.info("执行添加操作，Blog | {}", blog);
            return Result.result(blogService.insert(blog));
        } else {
            log.info("执行修改操作。 Blog | {}", blog);
            return Result.result(blogService.update(blog));
        }

    }

    @ApiOperation(value = "博客删除", notes = "flag为true时彻底删除，否则放入回收站")
    @GetMapping("/deleted/{flag}")
    @ResponseBody
    public Map<String, Object> deleted(Integer blogId, @PathVariable("flag") Boolean flag) {
        log.info("执行放入回收站或彻底删除操作，接收数据  blogId = {}, flag = {}", blogId, flag);
        // 放入回收站
        return Result.result(blogService.remove(blogId, flag));
    }

    @ApiOperation("回收站博客恢复")
    @GetMapping("/restore")
    @ResponseBody
    public Map<String, Object> restore(Integer blogId) {
        log.info("博客恢复操作");
        return Result.result(blogService.restore(blogId));
    }

    @ApiOperation("请求进入博客修改页面")
    @GetMapping("/update/{blogId}")
    public String update(@PathVariable("blogId") Integer blogId, Model model) {
        log.info("接收参数 blogId = {}，获取博客信息返回页面", blogId);
        Blog blog = blogService.getBlogById(blogId);
        model.addAttribute("blog", blog);
        // 返回数据到博客管理页面
        return "admin/manager";
    }

    @ApiOperation(value = "正常数据", notes = "layui数据表格接口")
    @GetMapping("/blogsData")
    @ResponseBody
    public Map<String, Object> blogsData(Integer page, Integer limit) {
        log.info("获取未删除博客数据");
        PageHelper.startPage(page, limit);
        List<Blog> blogs = blogService.getBlogs(Field.UNDELETED);
        return Result.result(blogs != null && blogs.size() != 0, blogService.getCount(false), blogs);
    }

    @ApiOperation(value = "回收站数据", notes = "layui数据表格接口")
    @GetMapping("/recycleData")
    @ResponseBody
    public Map<String, Object> recycleData(Integer page, Integer limit) {
        log.info("获取已删除博客数据");
        PageHelper.startPage(page, limit);
        List<Blog> blogs = blogService.getBlogs(Field.DELETED);
        return Result.result(blogs != null && blogs.size() != 0, blogService.getCount(true), blogs);
    }

    @ApiOperation(value = "博客分类数据获取", notes = "layui数据表格接口")
    @GetMapping("/cateData")
    @ResponseBody
    public Map<String, Object> cateData() {
        log.info("获取类别数据");
        List<Category> categories = categoryService.get();
        return Result.result(categories != null && categories.size() > 0, categoryService.getCount(), categories);
    }

    @ApiOperation(value = "博客标签数据获取", notes = "layui数据表格接口")
    @GetMapping("/tagData")
    @ResponseBody
    public Map<String, Object> tagData(Integer page, Integer limit) {
        log.info("获取标签数据");
        PageHelper.startPage(page, limit);
        List<Tag> tags = tagService.get();
        return Result.result(tags != null && tags.size() > 0, tagService.getCount(), tags);
    }

    @ApiOperation(value = "指定类别、标签对应的博客数据", notes = "flag为true获取标签对应博客，否则获取分类对应博客，layui数据表格接口")
    @GetMapping("/{flag}")
    @ResponseBody
    public Map<String, Object> cateOrTagBlog(@PathVariable("flag") Boolean flag, String variable, Integer page, Integer limit) {
        log.info("获取标签或类别对应博客数据， flag = {}, variable = {}", flag, variable);
        PageHelper.startPage(page, limit);
        List<Blog> blogs = blogService.getBlogs(variable, flag);
        return Result.result(blogs != null && blogs.size() != 0, blogService.getCount(variable, flag), blogs);
    }

    @ApiOperation(value = "类别、标签删除操作", notes = "flag为true删除标签，否则删除分类，layui数据表格接口")
    @PostMapping("/delete/{flag}")
    @ResponseBody
    public Map<String, Object> cateOrTagDelete(@PathVariable("flag") Boolean flag, String variable) {
        Map<String, Object> map = new HashMap<>(1);
        if(variable != null) {
            if(flag) {
                log.info("执行标签删除, variable = {}", variable);
                return Result.result(tagService.remove(new String[] {variable}));
            } else {
                log.info("执行类别删除, variable = {}", variable);
                return Result.result(categoryService.remove(variable));
            }
        } else {
            map.put("message", "参数异常");
        }
        return map;
    }

    @ApiOperation(value = "指定博客评论数据获取", notes = "layui数据表格接口")
    @GetMapping("/commData/{id}")
    @ResponseBody
    public Map<String, Object> commData(@PathVariable("id") Integer blogId, Integer page, Integer limit) {
        log.info("获取博客对应评论， 博客id = {}, page = {}, limit = {}", blogId, page, limit);
        PageHelper.startPage(page, limit);
        List<Comment> comments = commentService.getAll(blogId);
        Boolean flag = comments != null && comments.size() != 0;
        if(flag) {
            commentDataService.setData(blogService.getBlogById(blogId).getBlogTitle());
        }
        return Result.result(flag, commentService.getCount(blogId), comments);
    }

    @ApiOperation("添加回复")
    @PostMapping("/reply")
    @ResponseBody
    public Map<String, Object> reply(Comment comment) {
        log.info("执行回复 - {}", comment);
        Boolean flag = commentService.insertReply(comment);
        // 回复后消除未读记录数量
        if(flag) {
            String blogTitle = commentService.getBlogTitle(comment.getCommentId());
            log.info("回复后记录数-1");
            commentDataService.setData(blogTitle, 1);
        }
        return Result.result(flag);
    }

    @ApiOperation("删除评论")
    @PostMapping("/comment/deleted/{id}")
    @ResponseBody
    public Map<String, Object> commentDelete(@PathVariable("id") Integer id) {
        log.info("评论删除， id = {}", id);
        Boolean flag = commentService.remove(new Integer[] {id});
        if(flag) {
            String blogTitle = commentService.getBlogTitle(id);
            log.info("删除评论后后记录数-1");
            commentDataService.setData(blogTitle, 1);
        }
        return Result.result(flag);
    }
}
