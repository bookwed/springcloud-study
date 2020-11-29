package com.wei.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.user.domain.entity.RegUser;

public interface RegUserMapper extends BaseMapper<RegUser> {

    Integer updateRegUserByUserId(RegUser regUser);
}
