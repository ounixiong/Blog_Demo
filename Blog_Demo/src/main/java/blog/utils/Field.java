package blog.utils;

import blog.empty.AdminUser;
import blog.empty.CommentData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 欧尼熊
 * @date 2022-07-10 22:01
 */
public interface Field {

    /**
     * MD5 加密编码方式
     */
    String CHARSET = "UTF-8";
    /**
     * 登录时 Remember功能判断
     */
    String REMEMBER = "on";

    /**
     * 所有正常类型（已发布且未删除）
     */
    Character NORMAL = '0';
    /**
     * 所有已删除类型， deleted == '1'
     */
    Character DELETED = '1';
    /**
     * 所有未删除类型
     */
    Character UNDELETED = '2';

    /**
     * 所有已发布类型，包括已删除但发布
     */
    Character RELEASE = '3';

    /**
     * 博客创建时间排序
     */
    Integer TIME = 1;

    /**
     * 博客访问量类型排序
     */
    Integer VIEWS = 0;

    /**
     * 判断评论记录是否删除
     */
    Integer NUM = 2;
    /**
     * 头像配置项名称
     */
    String AVATAR = "avatar";

    /**
     * 存放当前登录用户信息
     */
    List<AdminUser> USER = new ArrayList<>(1);


    /**
     * 存放博客的评论信息 以博客id为下标
     */
    Map<String, CommentData> COMM_DATA = new HashMap<>();
}
