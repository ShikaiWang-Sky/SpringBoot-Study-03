package com.wang.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //链式编程
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问, 功能页只有对应有权限的人才能访问
        //请求授权的规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限, 默认回到登录页面(/login), 需要开启登录的页面
        /*
            The most basic configuration defaults to automatically generating a login page at
            the URL "/login", redirecting to "/login?error" for authentication failure.
         */
//        http.formLogin();
        //自定义登录页面
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login");

        //注销, 开启了注销功能, 跳到首页
        /*
            The default is that accessing the URL
	        "/logout" will log the user out by invalidating the HTTP Session, cleaning up any
	        {@link #rememberMe()} authentication that was configured, clearing the
	        {@link SecurityContextHolder}, and then redirect to "/login?success".
         */
        http.logout().deleteCookies("remove").invalidateHttpSession(true).logoutSuccessUrl("/");

        //get 明文, a标签, 不安全      post 表单, 安全
        //SpringSecurity默认开启了防止csrf攻击的设置, 使用disable可以将其关闭
        http.csrf().disable();

        //开启记住我功能   cookie 默认保存两周   自定义rememberMe对应的前端的name属性
        http.rememberMe().rememberMeParameter("rememberMe");

    }

    //认证
    //密码编码:  PassWordEncoding
    //在SpringSecurity 5.0+ 新增了很多的加密方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //此处的数据是从内存中读的, 而正常情况下应该从数据库中读
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("wang").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2", "vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2", "vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}
