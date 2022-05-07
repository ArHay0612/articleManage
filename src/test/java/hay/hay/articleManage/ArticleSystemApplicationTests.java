package hay.hay.articleManage;

import hay.hay.articleManage.entity.User;
import hay.hay.articleManage.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleSystemApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() throws UnsupportedEncodingException {
        User user = new User();
        user.setEmail("719510848@qq.com");
        user.setName("张三");
        user.setSex("男");
        user.setPsw(DigestUtils.md5Hex( new String(Base64Utils.decodeFromString("MTIzNDU2"), "utf-8")));
        user.setType(1);
        userService.save(user);
    }
}
