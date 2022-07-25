package blog.controller.admin;

import blog.service.ConfigService;
import blog.utils.Field;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 欧尼熊
 *         文件上传控制
 */
@Slf4j
@Controller
@Api(tags = "文件上传")
public class FileController {

    @Autowired
    ConfigService configService;

    @ApiOperation(value = "博客内容图片上传")
    @RequestMapping("/file/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("editormd-image-file") MultipartFile multipartFile) throws IOException {

        // 获取原始文件名
        String originalName = multipartFile.getOriginalFilename();
        assert originalName != null;
        // 获取文件名后缀
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        // 使用日期作为文件名
        String format = new SimpleDateFormat("yyMMdd-HHmmss").format(new Date());
        // 生成随机文件名：格式化日期+当前日期毫秒数+原文件后缀名
        String fileName = format + "-" + System.currentTimeMillis() + suffix;
        // 文件保存路径：项目路径下upload路径
        String filePath = System.getProperty("user.dir") + "\\upload\\" + fileName;
        File file = new File(filePath);
        if(!file.getParentFile().exists()) {
            file.mkdirs();
        }
        // 转存文件到本地
        multipartFile.transferTo(file);
        // 生成返回的URL：将 upload 路径映射到本地资源文件
        String url = "/upload/" + fileName;
        Map<String, Object> map = new HashMap<>(4);
        map.put("url", url);
        map.put("message", "success");
        map.put("success", 1);
        return map;
    }

    @ApiOperation(value = "头像上传")
    @PostMapping("/file/avatar")
    @ResponseBody
    public Map<String, Object> avatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException {
        log.info("头像上传");
        // 获取原始文件名
        String originalName = multipartFile.getOriginalFilename();
        assert originalName != null;
        // 获取文件名后缀
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        // 使用日期作为文件名
        String format = new SimpleDateFormat("yyMMdd-HHmmss").format(new Date());
        // 生成随机文件名：格式化日期+当前日期毫秒数+原文件后缀名
        String fileName = format + "-" + System.currentTimeMillis() + suffix;
        // 文件保存路径：项目路径下avatar路径
        String filePath = System.getProperty("user.dir") + "\\avatar\\" + fileName;
        File file = new File(filePath);
        if(!file.getParentFile().exists()) {
            file.mkdirs();
        }
        // 转存文件到本地
        multipartFile.transferTo(file);
        // 生成返回的URL：将 avatar 路径映射到本地资源文件
        String url = "/avatar/" + fileName;
        Map<String, Object> map = new HashMap<>(4);
        if(configService.insert(Field.AVATAR, url)) {
            map.put("code", 1);
            map.put("message", "上传成功");
        } else {
            map.put("code", 2);
            map.put("message", "上传失败");
        }
        map.put("data", url);
        return map;
    }

}
