package hay.hay.articleManage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hay.hay.articleManage.entity.User;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String> {
    User findByEmailAndDeletedIsFalse(String email);
    List<User> findAllByDeletedFalseOrderByCreateTimeDesc();
}

