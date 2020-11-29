package com.wei.user.api.service;

import com.wei.user.api.dto.RegUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
@RequestMapping("/remote/service/user")
public interface IRegUserRemoteService {

    @GetMapping("getById")
    RegUserDTO getById(@RequestParam("id") Long id);

}
