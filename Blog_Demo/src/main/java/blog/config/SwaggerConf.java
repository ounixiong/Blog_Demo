package blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author 欧尼熊
 * Swagger 配置类
 */
@Configuration
@EnableOpenApi
public class SwaggerConf {

    /**
     * 配置 Docket 设置 Swagger 参数
     *
     * @param environment 环境信息
     * @return Docket
     */
    @Bean
    public Docket docket(Environment environment) {
        //获取当前环境是否是 dev 开发环境
        boolean flag = environment.acceptsProfiles(Profiles.of("dev"));
        //swagger文档类型
        return new Docket(DocumentationType.OAS_30)
                //分组名
                .groupName("first")
                //根据环境配置选择是否启用
                .enable(flag)
                //元信息
                .apiInfo(apiInfo())
                //扫描接口：controller 包中所有接口
                .select().apis(RequestHandlerSelectors.basePackage("blog.controller"))
                //所有请求路径
                .paths(PathSelectors.any())
                .build();
    }


    /**
     * 配置接口文档元信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "个人博客",
                "基于十三的开源项目学习",
                "1.0",
                "urn:tos",
                new Contact("木十八", "#", "2217794007@qq.com"),
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}
