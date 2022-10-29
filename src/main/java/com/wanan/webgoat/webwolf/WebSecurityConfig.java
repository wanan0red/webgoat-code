package com.wanan.webgoat.webwolf;

import com.wanan.webgoat.webwolf.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
//告诉spring 这是一个配置类
@AllArgsConstructor
//作用于类,生成一个参数为所有实例变量的构造方法
@EnableWebSecurity
//用于在项目中启用springsecurity 开启安全认证

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry security = http
                .authorizeRequests()
                //                开启登录匹配
                .antMatchers("/css/**","/images/**","/js/**","/fonts/**","/webjars/**","/home").permitAll()
//                允许匿名的url 可以理解为放行接口 多个接口使用,分割
                .antMatchers(HttpMethod.GET,"/mail/**","/requests/**").authenticated()
//                这些get请求需要登录才能访问
                .antMatchers("/files").authenticated()
//                这些请求需要登录才能访问
                .anyRequest().permitAll();
//              剩余请求可随意访问
        security.and()
//                进行拼接
                .csrf().disable()
//              关闭csrf跨域
                .formLogin().loginPage("/login")
//                设置登录页面
                .failureForwardUrl("/login?error=true");
//                设置登录失败页面

        security.and()
                .formLogin().loginPage("/login")
//                设置表单登录页面
                .defaultSuccessUrl("/home",true)
                .permitAll();
        security.and()
                .logout()
                .permitAll();
//        注销操作

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
//    将用户设置在内存中
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws  Exception{
        return  userDetailsService;
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
    @Bean
    public NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//        不对密码进行加密操作
    }
}
