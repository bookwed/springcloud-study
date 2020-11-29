package com.wei.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.user.api.dto.RegUserDTO;
import com.wei.user.api.dto.RegUserQO;
import com.wei.user.api.dto.RegUserVO;
import com.wei.user.dao.RegUserMapper;
import com.wei.user.domain.entity.RegUser;
import com.wei.user.service.IRegUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public RegUserVO findPage(RegUserQO regUserQO) {

        /*
        //方式一：通过继承提供的方法查询，需要转换
        long startTime=System.currentTimeMillis();            //获得当前时间
        RegUserVO vo = new RegUserVO();
        //分页查询
        IPage<RegUser> page = new Page<>(regUserQO.getPageNum(), regUserQO.getPageSize());
        QueryWrapper<RegUser> wrapper = new QueryWrapper<>();
        wrapper.eq("login_name", regUserQO.getLoginName());
        IPage<RegUser> userPage = baseMapper.selectPage(page, wrapper);
        //转换数据
        List<RegUserDTO> list = new ArrayList<>();
        userPage.getRecords().forEach(regUser -> {
            RegUserDTO dto = new RegUserDTO();
            BeanUtils.copyProperties(regUser, dto);
            list.add(dto);
        });
        //封装分页对象
        vo.setCurrent(regUserQO.getPageNum());  //当前页
        vo.setSize(regUserQO.getPageSize());    //每页条数
        vo.setTotal(userPage.getTotal());       //总条数
        vo.setTotalPage(userPage.getPages());    //总页数
        vo.setRecords(list);
        long endTime=System.currentTimeMillis();                //获得当前时间
        System.out.println("方法执行时长："+(endTime-startTime)); //耗费时长*/

        //自定义sql语句，直接返回类型为需要的类型
        long startTime=System.currentTimeMillis();            //获得当前时间
        RegUserVO vo = new RegUserVO();
        //分页查询
        Page<RegUser> page = new Page<>(regUserQO.getPageNum(), regUserQO.getPageSize());
        IPage<RegUserDTO> dtoPage = baseMapper.selectPageVo(page, regUserQO);
        vo.setCurrent(regUserQO.getPageNum());  //当前页
        vo.setSize(regUserQO.getPageSize());    //每页条数
        vo.setTotal(dtoPage.getTotal());       //总条数
        vo.setTotalPage(dtoPage.getPages());    //总页数
        vo.setRecords(dtoPage.getRecords());
        long endTime=System.currentTimeMillis();                //获得当前时间
        System.out.println("方法执行时长："+(endTime-startTime)); //耗费时长
        return vo;
    }


}
