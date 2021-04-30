package cn.hunkier.jpa.example1;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserOnlyNameEmailEntityRepository
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @company 万众科技
 * @program: learnNote
 * @create: 2021-04-30 16:54
 * @since V1.0
 **/
public interface UserOnlyNameEmailEntityRepository extends JpaRepository<UserOnlyNameEmailEntity,Long> {
}
