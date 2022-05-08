package hay.hay.articleManage.dao;

import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findAllByDeletedFalseOrderByCreateTimeDesc();
    Article findByIdAndDeletedFalse(String id);
    List<Article> findAllByUserAndDeletedFalseOrderByCreateTimeDesc(User user);

}

