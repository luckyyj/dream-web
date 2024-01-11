package cn.com.dream.user.dao;

import cn.com.dream.user.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface UserDao extends BaseMapper<UserEntity> {

    List<UserEntity> listTest();

}
