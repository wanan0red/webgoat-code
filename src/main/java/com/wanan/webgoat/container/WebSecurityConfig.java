package com.wanan.webgoat.container;



import com.wanan.webgoat.container.users.UserService;
import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.NoOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
//用于定义配置类
@AllArgsConstructor
//自动生成构造方法
@EnableWebSecurity
//@EnableWebSecurity，用于在我们的项目中启用SpringSecurity。为某些请求路径开启安全认证策略。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry security = httpSecurity
                .authorizeRequests()
//                http请求是否要登录认证
                .antMatchers("/css/**", "/images/**", "/js/**", "fonts/**", "/plugins/**", "/registration", "/register.mvc", "/actuator/**").permitAll()
//                antMatcher用在多个HttpSecurity的场景下，用来为每个HttpSecurity过滤  permitAll() 不管登入,不登入 都能访问
                .anyRequest().authenticated();
//               任何请求都必须经过身份验证
        security.and()
//                 and() 是将方法链接在一起的一种方式。
                .formLogin()
                .loginPage("/login")
//                在未登录时自动跳转到login页面
                .defaultSuccessUrl("/welcome.mvc", true)
//                登录认证成功之后默认跳转的路径 defaultSuccessUrl 就是说，它会默认跳转到 Referer 来源页面，如果 Referer 为空，没有来源页，则跳转到默认设置的页面。
//        successForwardUrl 表示不管 Referer 从何而来，登录成功后一律跳转到指定的地址  defaultSuccessUrl 中如果第二个参数输入为 true，则效果和 successForwardUrl 一致。
                .usernameParameter("username")
//                登录表单form中用户名输入框中的name名,不修改的话默认是username
                .passwordParameter("password")
//                登录表单form中面输入框input的name名,不修改的话默认就是password
                .permitAll();
//        不需要登录就可以访问的
        security.and()
//                将方法连接在一起的方式NMnmnmMMmMmmMMmmM
                .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true);
//        登出 删除cookies
        security.and().csrf().disable();
//        关闭csrf防护
        httpSecurity.headers().cacheControl().disable();
//      禁用缓存
        httpSecurity.exceptionHandling().authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/login"));
//       添加自定义的未授权和为登录结果返回
    }
    @Autowired
//    这个注解就是spring可以自动帮你把Bean里面引用的对象的setter/getter方法省略,他会自动帮你set/get
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean()throws Exception{
        return userDetailsService;
    }
    @Override
    @Bean
    protected AuthenticationManager authenticationManager()throws Exception{
        return super.authenticationManager();
    }
    @SuppressWarnings("deprecation")
//    表示不检测过期的方法,不显示使用了不赞成使用的类或方法时的警告
    @Bean
    public NoOpPasswordEncoder passwordEncoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
