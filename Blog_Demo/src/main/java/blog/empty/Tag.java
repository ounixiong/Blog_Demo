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
@ApiModel("博客标签")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    @ApiModelProperty("标签 id")
    private Integer tagId;

    @ApiModelProperty(value = "标签名", notes = "使用 ' ' 拆分")
    private String tagName;

    @ApiModelProperty(value = "权重", notes = "按照使用次数排序")
    private Integer tagWeights;
}
