package com.wei.user.remote;

import com.wei.user.api.dto.RegUserDTO;
import com.wei.user.api.service.IRegUserRemoteService;
import com.wei.user.domain.entity.RegUser;
import com.wei.user.service.IRegUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外提供接口的具体实现类
 */
@RestController
public class RegUserRemoteServiceImpl implements IRegUserRemoteService {
    @Autowired
    private IRegUserService regUserService;

    @Override
    public RegUserDTO getById(Long id) {
        RegUser regUser = regUserService.getById(id);
        RegUserDTO regUserDTO = new RegUserDTO();
        BeanUtils.copyProperties(regUser, regUserDTO);
        return regUserDTO;
    }
}
