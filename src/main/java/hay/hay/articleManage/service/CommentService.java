package hay.hay.articleManage.service;

import hay.hay.articleManage.entity.Article;
import hay.hay.articleManage.entity.Comment;

import java.util.List;


public interface CommentService extends BaseService<Comment> {
	public List<Comment> findAllByArticle(Article article);
	public void save(Comment comment);
	//逻辑删除
	public void delete(Comment comment);
	public void update(Comment comment);
}
