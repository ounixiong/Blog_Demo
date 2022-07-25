package blog.empty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 欧尼熊
 */
@ApiModel("评论")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {

    @ApiModelProperty("评论 id")
    private Integer commentId;

    @ApiModelProperty("被评论博客 id")
    private Integer blogId;

    @ApiModelProperty("评论人")
    private String commentator;

    @ApiModelProperty("评论人联系方式")
    private String telNum;

    @ApiModelProperty("评论时间")
    private Date commentTime;

    @ApiModelProperty("评论内容")
    private String commentBody;

    @ApiModelProperty(value = "删除状态", notes = "0-正常，1-删除，默认 0 ")
    private String deleted;

    @ApiModelProperty("回复内容")
    private String reply;

    @ApiModelProperty("回复时间")
    private Date replyTime;

    public String getCommentTime() {
        if (this.commentTime != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.commentTime);
        } else {
            return null;
        }
    }

    public String getReplyTime() {
        if (this.replyTime != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.replyTime);
        } else {
            return null;
        }
    }

}
