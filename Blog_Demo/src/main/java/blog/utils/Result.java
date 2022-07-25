package blog.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 欧尼熊
 * @date 2022-07-10 20:57
 * 用户处理Ajax请求的返回结果集
 */
public class Result {

    /**
     * 根据布尔值返回不同的Map集合
     * @param flag 执行函数结果
     * @return 返回Map集合
     */
    public static Map<String, Object> result(Boolean flag) {
        Map<String, Object> map = new HashMap<>(4);
        if ( flag ) {
            map.put("message", "操作成功");
            map.put("code", 1);
        } else {
            map.put("message", "操作失败");
            map.put("code", 2);
        }
        return map;
    }

    /**
     * 自定义执行成功或失败的返回结果
     * @param message 返回消息
     * @param code 执行代码
     * @return 返回map集合
     */
    public static Map<String, Object> result(String message, Integer code) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("message", message);
        map.put("code", code);
        return map;
    }

    /**
     * 重载函数，可自定义执行成功或失败后的返回消息
     * @param flag 执行结果
     * @param message1 执行成功的消息
     * @param message2 执行失败的消息
     * @return map集合
     */
    public static Map<String, Object> result(Boolean flag, String message1, String message2) {
        Map<String, Object> map = new HashMap<>(4);
        if ( flag ) {
            map.put("message", message1);
            map.put("code", 1);
        } else {
            map.put("message", message2);
            map.put("code", 2);
        }
        return map;
    }

    /**
     * lay-ui数据接口
     * @param flag 执行函数判断返回值
     * @param message1 成功时自定义消息
     * @param message2 失败时自定义消息
     * @param count 数据数量
     * @param obj 数据
     * @return 返回map集合
     */
    public static Map<String, Object> result(Boolean flag, String message1, String message2, Integer count, Object obj) {
        Map<String, Object> map = new HashMap<>(4);
        if ( flag ) {
            map.put("msg", message1);
        } else {
            map.put("msg", message2);
        }
        map.put("count", count);
        map.put("code", 0);
        map.put("data", obj);
        return map;
    }

    /**
     * lay-ui数据接口返回通用格式
     * @param flag 判断执行条件
     * @param count 数据数量
     * @param obj 返回数据
     * @return 返回map集合
     */
    public static Map<String, Object> result(Boolean flag, Integer count, Object obj) {
        Map<String, Object> map = new HashMap<>(4);
        if ( flag ) {
            map.put("msg", "");
        } else {
            map.put("msg", "当前数据为空");
        }
        map.put("count", count);
        map.put("code", 0);
        map.put("data", obj);
        return map;
    }

}
