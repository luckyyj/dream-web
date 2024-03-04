package cn.com.dream.user.controller;

import cn.com.dream.common.exception.BaseException;
import cn.com.dream.common.model.LoginUser;
import cn.com.dream.common.model.ResponseData;
import cn.com.dream.common.util.RedisUtil;
import cn.com.dream.shiro.TokenGenerator;
import cn.com.dream.user.model.entity.UserEntity;
import cn.com.dream.user.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping({"/sys"})
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping({"/login"})
    public ResponseData<LoginUser> login(@RequestParam String userName, @RequestParam String password) {
        UserEntity userEntity = userService.getByUserName(userName);
        if (ObjectUtil.isEmpty(userEntity) || !DigestUtil.md5Hex(password).equals(userEntity.getPassword())) {
            throw new BaseException("用户不存在");
        }
        Date now = new Date();
        String token = TokenGenerator.generateValue();
        LoginUser loginUser = new LoginUser();
        loginUser.setToken(token);
        loginUser.setUserId(userEntity.getUserId());
        loginUser.setUserName(userEntity.getUserName());
        loginUser.setLoginTime(now.getTime());
        loginUser.setExpireTime(now.getTime() + 60 * 60 * 1000);
        // 存入redis
        redisUtil.setEx("dream:login:" + token, JSONUtil.toJsonStr(loginUser), 60, TimeUnit.MINUTES);


        //获取当前的用户
     /*   Subject subject = SecurityUtils.getSubject();
        JWTToken jwtToken = new JWTToken(token);
        subject.login(jwtToken);*/

        return ResponseData.buildSuccess(loginUser);
    }

}
