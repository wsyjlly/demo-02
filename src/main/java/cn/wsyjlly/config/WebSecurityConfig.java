package cn.wsyjlly.config;

import cn.wsyjlly.service.implementation.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author wsyjlly
 * @create 2019.06.16 - 11:59
 **/
@Configuration
/*
* 开启注解配置方法安全
* */
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserServiceImpl userService;

    private static final String PASSWORD1 = "$2a$10$ZrdSqSO5m0xyt88fGzP3tOUocmRMu2oDWMJzyAe.Wc7l5ErRh.IYi";       //234
    private static final String PASSWORD2 = "$2a$10$o9.MF1hHKCcHsfvIsvzrc.Y7J/Rx5Ty52YqYsL4odg4AUpJKtMHlK";       //234
    private static final String PASSWORD3 = "$2a$10$UD1XqwWgqEdkjqAxw6potOjMdtzLNI1SxUUBmFGolpLC.M.LMW/Gm";       //234
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(PASSWORD1).roles("ADMIN","USER")
                .and().withUser("root").password(PASSWORD2).roles("ADMIN","DBA")
                .and().withUser("wsyjlly").password(PASSWORD3).roles("DBA");
        auth.userDetailsService(userService);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        /*
        * 明文密码
        * */
//        return NoOpPasswordEncoder.getInstance();
        /*
        * BCrypt强哈希函数加密算法
        * */
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").access("hasAnyRole('ADMIN','USER')")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login_page.html").loginProcessingUrl("/login")
                .permitAll()
                .usernameParameter("username").passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        Object principal = authentication.getPrincipal();
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(200);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("status",200);
                        map.put("message",principal);
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(401);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("status",401);
                        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
                            map.put("message","账户名或者密码输入错误!");
                        } else if (e instanceof LockedException) {
                            map.put("message","账户被锁定，请联系管理员!");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("message","密码过期，请联系管理员!");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("message","账户过期，请联系管理员!");
                        } else if (e instanceof DisabledException) {
                            map.put("message","账户被禁用，请联系管理员!");
                        } else {
                            map.put("message","登录失败!");
                        }

                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and().logout()
                .logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {}
                }).logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/login_page.html");
                    }
                })
                .and().csrf().disable();


    }
}
