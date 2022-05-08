package hay.hay.articleManage.service;

import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.User;

import java.util.List;

public interface ArticleService extends BaseService<Article> {
	public List<Article> findAll();
	public Article save(Article article);
	//逻辑删除
	public void delete(Article article);
	public void update(Article article);
	public Article findById(String id);

	List<Article> findMine(User user);
}
