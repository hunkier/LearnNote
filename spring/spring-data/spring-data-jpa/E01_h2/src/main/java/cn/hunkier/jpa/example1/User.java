package cn.hunkier.jpa.example1;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户类
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @program: learnNote
 * @create: 2021-03-11 10:34
 * @since V1.0
 **/
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String zipCode;
}
