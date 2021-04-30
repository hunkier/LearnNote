package cn.hunkier.jpa.example1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * UserOnlyNameEmailEntity
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @company 万众科技
 * @program: learnNote
 * @create: 2021-04-30 16:52
 * @since V1.0
 **/
@Entity
@Table(name="user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOnlyNameEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
}
