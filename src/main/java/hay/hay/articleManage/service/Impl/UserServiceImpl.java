package hay.hay.articleManage.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import hay.hay.articleManage.dao.UserDao;
import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<User> findAll() {
        return userDao.findAllByDeletedFalseOrderByCreateTimeDesc();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        user.setDeleted(true);
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(String id) {
        if (userDao.findById(id).isPresent()) {
            return userDao.findById(id).get();
        } else {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmailAndDeletedIsFalse(email);
    }

}
