package cn.hunkier.jpa.example1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * UserDto
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @company 万众科技
 * @program: learnNote
 * @create: 2021-04-30 16:47
 * @since V1.0
 **/
@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private String name, email;
}
