package cn.hunkier.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户查询
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @program: learnNote
 * @create: 2021-03-11 10:38
 * @since V1.0
 **/
public interface UserRepository extends JpaRepository<User,Long> {
}
