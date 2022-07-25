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
@ApiModel("配置")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config implements Serializable {

    @ApiModelProperty("配置项 id")
    private Integer configId;

    @ApiModelProperty("配置项名称")
    private  String configName;

    @ApiModelProperty("配置项值")
    private String configValue;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
