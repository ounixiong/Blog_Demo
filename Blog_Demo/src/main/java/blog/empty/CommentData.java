package blog.empty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 欧尼熊
 * @date 2022-07-18 08:47
 *         评论数据临时存放实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentData {

    @ApiModelProperty("表主键id")
    private Integer id;

    @ApiModelProperty("评论所属博客id")
    private Integer blogId;

    @ApiModelProperty("评论所属博客标题")
    private String blogTitle;

    @ApiModelProperty("同一博客未处理评论数量")
    private Integer num;


    public CommentData(Integer blogId, String blogTitle, Integer num) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.num = num;
    }

}
