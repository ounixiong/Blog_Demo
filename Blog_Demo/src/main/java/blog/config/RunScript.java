package blog.config;

import blog.empty.CommentData;
import blog.service.CommentDataService;
import blog.utils.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 欧尼熊
 * @date 2022-07-20 23:56
 */
@Component
@Slf4j
public class RunScript {

    @Autowired
    CommentDataService commentDataService;

    @PostConstruct
    public void start() {
        log.info("启动后获取并删除记录");
        List<CommentData> dataList = commentDataService.get();
        if(dataList.size() > 0) {
            log.info("当前存在记录，进行保存");
            for(CommentData commentData: dataList) {
                Field.COMM_DATA.put(commentData.getBlogTitle(), commentData);
            }
            log.info("保存完毕，清空数据库表");
            commentDataService.delete();
        } else {
            log.info("没有记录，程序开始");
        }
    }

    @PreDestroy
    public void end() {
        log.info("关闭前保存记录");
        if(Field.COMM_DATA.size() > 0) {
            commentDataService.save(new ArrayList<>(Field.COMM_DATA.values()));
            log.info("存在记录，已持久化到数据库");
        } else {
            log.info("没有记录，程序关闭");
        }

    }
}