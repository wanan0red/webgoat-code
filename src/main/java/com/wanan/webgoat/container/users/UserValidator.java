package com.wanan.webgoat.container.users;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
//实现bean的注入,当我们的类不属于@controller @services的时候我们就可以使用@component来标注这个类
@AllArgsConstructor
public class UserValidator implements Validator {
    private  final  UserRepository userRepository;

    @Override
    public  boolean supports(Class<?> clazz){
        return UserForm.class.equals(clazz);
    }
    @Override
    public void validate(Object o, Errors errors){
        UserForm userForm = (UserForm) o;
        if (userRepository.findByUsername(userForm.getUsername()) != null){
            errors.rejectValue("username","username.duplicate");
        }
        if (!userForm.getMatchingPassword().equals(userForm.getPassword())){
            errors.rejectValue("matchingPassword","password.diff");
        }
    }

}
