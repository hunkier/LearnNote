package cn.hunkier.jpa.example1;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

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

    @Query("select  u from User u")
    Stream<User> findAllByCustomQueryAndStream(Pageable pageable);


}
