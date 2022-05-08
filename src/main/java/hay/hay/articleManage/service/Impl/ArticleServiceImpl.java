package hay.hay.articleManage.service.Impl;

import hay.hay.articleManage.dao.ArticleDao;
import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Article> findAll() {
        return articleDao.findAllByDeletedFalseOrderByCreateTimeDesc();
    }

    @Override
    public Article save(Article article) {
        return articleDao.save(article);
    }

    @Override
    public void delete(Article article) {
        article.setDeleted(true);
        articleDao.save(article);
    }

    @Override
    public void update(Article article) {
        articleDao.save(article);
    }

    @Override
    public Article findById(String id) {
        return articleDao.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<Article> findMine(User user) {
        return articleDao.findAllByUserAndDeletedFalseOrderByCreateTimeDesc(user);
    }

}
