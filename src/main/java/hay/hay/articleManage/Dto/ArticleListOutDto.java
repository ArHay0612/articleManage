package hay.hay.articleManage.Dto;

import hay.hay.articleManage.entity.Article;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleListOutDto {

    private String id;

    private String title;

    private Date createTime;

    private String userName;

    private String userid;

    public ArticleListOutDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.createTime = article.getCreateTime();
        this.userName = article.getUser().getName();
        this.userid = article.getUser().getId();
    }
}
