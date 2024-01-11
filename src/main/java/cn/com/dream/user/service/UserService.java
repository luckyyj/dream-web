package cn.com.dream.user.service;

import cn.com.dream.user.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<UserEntity> {

    UserEntity getByUserName(String userName);

    void add(UserEntity addUserPO);

    IPage<UserEntity> pageList(Integer pageNum, Integer pageSize);
}
