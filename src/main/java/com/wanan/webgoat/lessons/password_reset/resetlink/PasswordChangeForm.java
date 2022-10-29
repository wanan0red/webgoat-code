package com.wanan.webgoat.lessons.password_reset.resetlink;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangeForm {
    @NotNull
    @Size(min = 6,max =10)
    private String password;
    private String resetLink;
}
