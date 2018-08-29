package com.hunk.user.controller;

import com.hunk.thrift.user.UserInfo;
import com.hunk.thrift.user.dto.UserDTO;
import com.hunk.user.redis.RedisClient;
import com.hunk.user.response.LoginResponse;
import com.hunk.user.response.Response;
import com.hunk.user.thrift.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisClient redisClient;

    @GetMapping(path = "/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        log.info("username: {}, password: {}, md5pw: {}",username, password, md5(password));
        // 1.验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (null==userInfo){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (!StringUtils.endsWithIgnoreCase(userInfo.getPassword(), md5(password))){
            return Response.USERNAME_PASSWORD_INVALID;
        }


        //  2.生成token
        String token = genToken();
        //  3.缓存用户
        redisClient.set(token,toDTO(userInfo),3600);

        return new LoginResponse(token);
    }

    @PostMapping(path = "/sendVerifyCode")
    @ResponseBody
    public Response sendVerifyCode(
            @RequestParam(value = "mobile",required = false)String mobile,
            @RequestParam(value = "email",required = false)String email
    ){
        String message = "Verify code is:";
        String code = randomCode("0123456789",6);
        message +=code;
        log.info("sendVerifyCode, mobile: {}, email: {}, message: {}", mobile, email, message);
        try {
            boolean result = false;
            if (StringUtils.isNotBlank(mobile)){
                result = serviceProvider.getMessageService().sendMobileMessage(mobile,message);
                redisClient.set(mobile,code,1800);
            }else if (StringUtils.isNotBlank(email)){
                result = serviceProvider.getMessageService().sendEmailMessage(email, message);
                redisClient.set(email,code,1800);
            }else {
                return Response.MOBILE_OR_EMAIL_REQUIRED;
            }
            if (!result){
                return  Response.SEND_VERIFYCODE_FAILED;
            }
        } catch (TException e) {
            e.printStackTrace();
        }

        return Response.SUCCESS;
    }

    @PostMapping(path = "/register")
    @ResponseBody
    public Response register(
            @RequestParam("password")String password,
            @RequestParam("username")String username,
            @RequestParam(value = "mobile",required = false)String mobile,
            @RequestParam(value = "email",required = false)String email,
            @RequestParam(value = "realName",required = false)String realName,
            @RequestParam("verifyCode")String verifyCode
            ){
        if (StringUtils.isBlank(mobile) && StringUtils.isBlank(email)){
            return Response.MOBILE_OR_EMAIL_REQUIRED;
        }

        String key = mobile;
        if (StringUtils.isBlank(mobile)){
             key = email;
        }
        String redisCode = redisClient.get(key);
        if (!StringUtils.endsWithIgnoreCase(redisCode, verifyCode)){
            return Response.VERIFY_CODE_INVALID;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setRealName(realName);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);

        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            log.error("register fail: ", e);
        }

        return Response.SUCCESS;
    }

    @PostMapping(path = "/authentication")
    @ResponseBody
    public UserDTO authentication(@RequestHeader("token") String token){
        return redisClient.get(token);
    }


    private UserDTO toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuilder result = new StringBuilder(size);

        Random random = new Random();
        for(int i=0;i<size;i++) {
            int loc = random.nextInt(s.length());
            result.append(s.charAt(loc));
        }
        return result.toString();
    }

    private String md5(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
