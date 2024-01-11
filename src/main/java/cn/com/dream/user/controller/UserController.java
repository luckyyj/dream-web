package cn.com.dream.user.controller;

import cn.com.dream.user.dao.UserDao;
import cn.com.dream.user.model.entity.UserEntity;
import cn.com.dream.user.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/user"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @GetMapping({"/test"})
    public void test(){
        List<UserEntity> userEntities = userDao.listTest();
        System.out.println("111");
    }


   /* @PostMapping({"/login"})
    public void login(@RequestParam String abc, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(abc, password);
        subject.login((AuthenticationToken)usernamePasswordToken);
        System.out.println(");
    }*/

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
