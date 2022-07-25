package blog.empty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 欧尼熊
 * 博客
 */
@ApiModel("博客")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {

    @ApiModelProperty("博客 id")
    private Integer blogId;

    @ApiModelProperty("博客标题")
    private String blogTitle;

    @ApiModelProperty("博客内容")
    private String blogContent;

    @ApiModelProperty("类别")
    private String blogCate;

    @ApiModelProperty(value = "标签名称", notes = "使用' '分隔多个标签")
    private String blogTagName;

    @ApiModelProperty(value = "博客状态", notes = "0-草稿，1-发布，默认 1")
    private String blogStatus;

    @ApiModelProperty(value = "浏览量", notes = "默认 0")
    private Integer blogViews;

    @ApiModelProperty(value = "评论权限", notes = "0-允许评论，1-不允许，默认 0")
    private String allowComm;

    @ApiModelProperty(value = "删除状态", notes = "0-正常，1-删除，默认 0")
    private String deleted;

    @ApiModelProperty("添加时间")
    private Date creatTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("评论数量")
    private Integer commentNum;
}
