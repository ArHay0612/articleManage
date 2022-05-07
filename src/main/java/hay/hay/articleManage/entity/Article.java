package hay.hay.articleManage.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // 注明此Java类是实体类
@Setter// 自动生成getter和setter
@Getter
@Table(name = "ts_article") // 数据库中表的名称
public class Article extends BaseEntity<String> {

    private static final long serialVersionUID = 6772948018122216208L;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "null", value = ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "click_time")
    private Integer clickTime;

}