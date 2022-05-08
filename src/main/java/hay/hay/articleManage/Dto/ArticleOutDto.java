package hay.hay.articleManage.Dto;

import hay.hay.articleManage.entity.Article;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleOutDto {

    private String id;

    private String title;

    private String content;

    private Date createTime;

    private String userName;

    private Integer clickTime;

    public ArticleOutDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.userName = article.getUser().getName();
        this.clickTime = article.getClickTime();
        this.createTime = article.getCreateTime();
    }
}
