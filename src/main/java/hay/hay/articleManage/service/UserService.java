package hay.hay.articleManage.service;

import java.util.List;

import hay.hay.articleManage.entity.User;

public interface UserService extends BaseService<User> {
	public List<User> findAll();
	public void save(User user);
	//逻辑删除
	public void delete(User user);
	public void update(User user);
	public User findById(String id);
	public User findByEmail(String email);
}
