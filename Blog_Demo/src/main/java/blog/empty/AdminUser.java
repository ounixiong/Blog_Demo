package blog.empty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 欧尼熊
 */
@ApiModel("管理员")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser implements Serializable {

    @ApiModelProperty("管理员 id")
    private Integer userId;

    @ApiModelProperty(value = "用户名", notes = "登录使用，不允许重复")
    private String userName;

    @ApiModelProperty("密码")
    private String passWord;

    @ApiModelProperty(value = "昵称", notes = "博客页面显示")
    private String nickName;

    @ApiModelProperty(value = "状态", notes = "0-正常，1-被锁定无法登录")
    private String status;

    @ApiModelProperty(value = "权限", notes = "admin-管理员，user-普通用户, boss-老板，注册默认user权限")
    private String roles;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

}
