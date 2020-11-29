package com.wei.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.user.api.dto.RegUserDTO;
import com.wei.user.api.dto.RegUserQO;
import com.wei.user.domain.entity.RegUser;
import org.apache.ibatis.annotations.Param;

public interface RegUserMapper extends BaseMapper<RegUser> {

    Integer updateRegUserByUserId(RegUser regUser);

    //List<RegUserDTO> findPage(RegUser regUser);
    IPage<RegUserDTO> selectPageVo(Page<?> page, @Param("regUserQO") RegUserQO regUserQO);
}
