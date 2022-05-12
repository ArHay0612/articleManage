package hay.hay.articleManage.entity;

import hay.hay.articleManage.Dto.ArticleInDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // 注明此Java类是实体类
@Setter// 自动生成getter和setter
@Getter
@Table(name = "ts_comment") // 数据库中表的名称
public class Comment extends BaseEntity<String> {

    private static final long serialVersionUID = -2380287952383169720L;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false,
            foreignKey = @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT))
    private Article article;

    public Comment(String comment,Article article, User user) {
        this.comment = comment;
        this.user = user;
        this.article = article;
    }


    public Comment() {
    }

}