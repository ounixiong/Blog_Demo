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
 */
@ApiModel("友链")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link implements Serializable {

    @ApiModelProperty("友链 id")
    private Integer linkId;

    @ApiModelProperty(value = "友链类型", notes = "0-友情链接，1-推荐网站")
    private String type;

    @ApiModelProperty("网站名称")
    private String linkName;

    @ApiModelProperty("连接地址")
    private String linkUrl;

    @ApiModelProperty("网站介绍")
    private String linkDescription;

    @ApiModelProperty(value = "链接权重", notes = "进行排序使用")
    private Integer linkWeights;

    @ApiModelProperty("添加时间")
    private Date createTime;

    @ApiModelProperty(value = "删除状态", notes = "0-正常，1-删除")
    private String deleted;
}
