package blog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 欧尼熊
 * 主启动类
 */
@SpringBootApplication
@EnableTransactionManagement
public class BlogDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogDemoApplication.class, args);
    }

}
