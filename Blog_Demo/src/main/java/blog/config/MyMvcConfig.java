package blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 欧尼熊
 *         对资源访问路径进行映射,隐藏本地真实资源路径
 */
@Slf4j
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    /**
     * 访问的时候使用虚路径/upload，比如文件名为1.png，就直接/upload/1.png就ok了。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/upload/");
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/avatar/");
        registry.addResourceHandler("/resources/Images/**")
                .addResourceLocations("file:D:/Application/Typora/resources/Images/");
    }
//    D:/Application/Typora/Typora/resources/Images/image-20220307230411908.png
}
