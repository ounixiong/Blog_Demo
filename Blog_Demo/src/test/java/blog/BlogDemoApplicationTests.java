package blog;

import blog.dao.AdminUserDao;
import blog.empty.CommentData;
import blog.utils.Field;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
class BlogDemoApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    AdminUserDao adminUserDao;


    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());
    }

    @Test
    void testDao() {
        CommentData commentData = Field.COMM_DATA.get("博客");
        System.out.println(commentData);
        CommentData commentData1 = Field.COMM_DATA.remove("博客");
        System.out.println(commentData1);
    }

    @Test
    void testPass() {
        String[] tags = "基础 Magaic 练习".trim().split("\\s+");
        System.out.println(Arrays.toString(tags));
        List<String> list = new ArrayList<>(Arrays.asList(tags));
        System.out.println(list);
        log.trace("1");
        log.debug("3");
        log.info("2");
        log.warn("4");
        log.error("5");
    }
}
