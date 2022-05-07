package hay.hay.articleManage.dao;

import hay.hay.articleManage.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleDao extends JpaRepository<Article, String> {
    List<Article> findAllByDeletedFalseOrderByCreateTimeDesc();
}

