package cn.hunkier.jpa.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 *
 * @author <a href="mailto:huangkuier@gmail.com">huangkui</a>
 * @version V1.0
 * @copyright Copyright © 2021 https://hunkier.cn/ Inc. All rights reserved.
 * @program: learnNote
 * @create: 2021-03-11 10:39
 * @since V1.0
 **/
@RestController
@RequestMapping(path="/api/v1")
public class UserController {

    @Autowired
    private  UserRepository userRepository;

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping(path="user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User addNewUser(@RequestBody User user){
        return userRepository.save(user);
    }

    /**
     * 根据分页信息查询用户
     * @param request
     * @return
     */
    @GetMapping(path="users")
    public Page<User> getAllUsers(Pageable request){
        return userRepository.findAll(request);
    }

}