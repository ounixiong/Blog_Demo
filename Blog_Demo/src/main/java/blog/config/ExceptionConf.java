package blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 * @author 欧尼熊
 */
@Slf4j
@ControllerAdvice
public class ExceptionConf {

    private final Map<String, Object> map = new HashMap<>();

    /**
     * 处理注册账号时的重复用户名异常
     * @param e 异常
     * @return JSON对象
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public Map<String, Object> duplicateKeyException(Exception e) {
        log.error("拦截DuplicateKeyException异常， 类型 = {}, 原因 = {}", e.getClass().getSimpleName(), e.getCause().getMessage());
        map.put("code", "error");
        map.put("message", "用户名已被使用");
        return map;
    }

    /**
     * 处理请求方法不支持异常
     * @param e 异常
     * @return JSON 对象
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Map<String, Object> httpRequestMethodNotSupportedException(Exception e) {
        log.error("拦截HttpRequestMethodNotSupportedException异常， 类型 = {}, 原因 = {}", e.getClass().getSimpleName(), e.getMessage());
        map.put("code", "error");
        map.put("message", e.getMessage());
        return map;
    }

    /**
     * 空指针异常
     * @param e 异常
     * @return JSON 对象
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Map<String, Object> nullPointerException(Exception e) {
        map.put("code", "error");
        map.put("type", e.getClass().getSimpleName());
        map.put("message", e.getMessage());
        log.error("空指针异常， 类型 = {}, 信息 = {}", e.getClass(), e.getMessage());
        return map;
    }

    /**
     * 默认异常处理器
     * @param e 异常
     * @return JSON 对象
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> defaultException(Exception e) {
        String type = e.getClass().getSimpleName();
        String message = e.getMessage();
        map.put("code", "error");
        map.put("type", type);
        map.put("message", message);
        log.error("默认异常， 类型 = {}, 原因 = {}", e.getClass(), e.getMessage());
        return map;
    }

}
