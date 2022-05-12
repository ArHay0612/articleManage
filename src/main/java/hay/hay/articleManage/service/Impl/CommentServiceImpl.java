package hay.hay.articleManage.service.Impl;

import hay.hay.articleManage.dao.CommentDao;
import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.Comment;
import hay.hay.articleManage.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Comment> findAllByArticle(Article article) {
        return commentDao.findAllByArticleAndDeletedFalseOrderByCreateTimeDesc(article);
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findById(String id) {
        return commentDao.findByIdAndDeletedFalse(id);
    }


    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        comment.setDeleted(true);
        commentDao.save(comment);
    }

    @Override
    public void update(Comment comment) {
        commentDao.save(comment);
    }
}
