package cn.com.dream.user.dao;

import cn.com.dream.user.model.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    List<UserEntity> listTest();

}
