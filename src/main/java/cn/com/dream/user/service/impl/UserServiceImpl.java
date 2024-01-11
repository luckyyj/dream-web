package cn.com.dream.user.service.impl;


import cn.com.dream.user.dao.UserDao;
import cn.com.dream.user.model.entity.UserEntity;
import cn.com.dream.user.service.UserService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity getByUserName(String userName) {


        List<UserEntity> list = lambdaQuery().eq(UserEntity::getUserName, userName).list();
        if (CollUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void add(UserEntity addUserPO) {
        userDao.insert(addUserPO);
    }

    @Override
    public IPage<UserEntity> pageList(Integer pageNum, Integer pageSize) {
        IPage<UserEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getIsDelete, 0);
        IPage<UserEntity> page1 = this.page(page, queryWrapper);
        return page1;
    }

}