package com.wei.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.user.api.dto.RegUserQO;
import com.wei.user.api.dto.RegUserVO;
import com.wei.user.domain.entity.RegUser;

public interface IRegUserService extends IService<RegUser> {

    int updateUser(Long userId, String password);

    RegUserVO findPage(RegUserQO regUserQO);
}
