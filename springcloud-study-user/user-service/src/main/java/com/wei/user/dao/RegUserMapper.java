package com.wei.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.user.api.dto.RegUserDTO;
import com.wei.user.domain.entity.RegUser;

import java.util.List;

public interface RegUserMapper extends BaseMapper<RegUser> {

    Integer updateRegUserByUserId(RegUser regUser);

    List<RegUserDTO> findPage(RegUser regUser);
}
