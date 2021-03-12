package cn.hunkier.jpa.example1;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * 测试
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @program: learnNote
 * @create: 2021-03-11 17:28
 * @since V1.0
 **/
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        final User user = userRepository.save(User.builder().name("hunkier").email("hunkier@163.com").build());
        Assert.assertNotNull(user);
        final List<User> users = userRepository.findAll();
        System.out.println(users);
        Assert.assertNotNull(users);
    }
}
