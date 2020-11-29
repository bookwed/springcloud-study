package com.wei.user.api.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegUserDTO {
    private Long id;
    private String loginName;
    private String password;
}
