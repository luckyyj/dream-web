package cn.com.dream.user.controller;

import cn.com.dream.common.model.LoginUser;
import cn.com.dream.common.model.ResponseData;
import cn.com.dream.common.util.RedisUtil;
import cn.com.dream.user.model.entity.UserEntity;
import cn.com.dream.user.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/test")
    public ResponseData test() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return ResponseData.buildSuccess(loginUser.getUserName());
    }

    @GetMapping({"/page"})
    public IPage<UserEntity> page(Integer pageNum, Integer pageSize) {
        return userService.pageList(pageNum, pageSize);
    }

    public void delete() {
        userService.removeById("001");
    }

    public void update() {
        UserEntity updateUserPO = new UserEntity();
        updateUserPO.setUserId("001");
        updateUserPO.setPersonName("update");
        userService.updateById(updateUserPO);
    }

    public void add() {
        UserEntity addUserPO = new UserEntity();
        addUserPO.setUserId("001");
        addUserPO.setPersonName("22");
        userService.add(addUserPO);
    }
}
