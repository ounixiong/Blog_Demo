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
@ApiModel("博客类别")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @ApiModelProperty("类别 id")
    private Integer cateId;

    @ApiModelProperty("类别名称")
    private String cateName;

    @ApiModelProperty(value = "类别权重", notes = "用来进行排序")
    private Integer cateWeights;

    @ApiModelProperty("添加时间")
    private Date createTime;


   /*  public String getCreateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (this.createTime != null) {
            return format.format(this.createTime);
        } else {
            return format.format(new Date());
        }
    } */
}
