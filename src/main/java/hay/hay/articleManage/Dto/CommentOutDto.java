
package hay.hay.articleManage.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hay.hay.articleManage.entity.Comment;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CommentOutDto {

    private String id;

    private String comment;

    private String userName;

    private String userid;

    private String articleId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public CommentOutDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userName = comment.getUser().getName();
        this.userid = comment.getUser().getId();
        this.articleId = comment.getArticle().getId();
        this.createTime = comment.getCreateTime();

    }
}
