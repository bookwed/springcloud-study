package com.wei.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.user.dao.RegUserMapper;
import com.wei.user.domain.entity.RegUser;
import com.wei.user.service.IRegUserService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegUserServiceImpl extends ServiceImpl<RegUserMapper, RegUser> implements IRegUserService {

    @Override
    public int updateUser(Long userId, String password) {
        RegUser user = new RegUser();
        user.setId(userId);
        user.setPassword(password);
        user.setCreateTime(new Date());
        return baseMapper.updateRegUserByUserId(user);
    }

}
