package hay.hay.articleManage.dao;

import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, String> {

   List<Comment> findAllByArticleAndDeletedFalseOrderByCreateTimeDesc(Article article);

    Comment findByIdAndDeletedFalse(String id);

}

