package com.wanan.webgoat.container.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserForm {
    @NotNull
    @Size(min = 6,max = 45)
    @Pattern(regexp = "[a-z0-9-]*",message = "can only contain lowercase letters.digits,and -")
    private String username;
//    表单数据中的username
    @NotNull
    @Size(min = 6,max = 10)
    private String password;
    @NotNull
    @Size(min = 6,max = 10)
    private String matchingPassword;
    @NotNull
    private String agree;
}