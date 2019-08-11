package com.shixun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;


/**
 * spring security 所有配置信息
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//允许开启方法级别的认证
public class MyWebSerurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncode passwordEncode;//密码
    @Resource
    private MyUserDetailService myUserDetailService = null;//自定义用户认证逻辑
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;//无权限访问
    @Autowired
    private SessionRegistry sessionRegistry;//session 配置
    @Autowired
    private MyAuthenticationSuccessHander myAuthenticationSuccessHander;//登陆认证成功处理的逻辑
    @Autowired
    private MyLogoutHandler myLogoutHandler;

    /**
     * 初始化session
     *
     * @return
     */
    @Bean
    public SessionRegistry sessionRegistry2() {
        return new SessionRegistryImpl();
    }

    /**
     * session 监听器
     *
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * security 标签库
     * @return
     */
    @Bean
    public  ClassPathTldsLoader classPathTldsLoader(){
        return  new ClassPathTldsLoader();
    }
    /**
     * 配置用户认证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncode);
    }


    /**
     * 配置前端静态文件
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO Auto-generated method stub
        String[] antMatchers = {
                "/404/**", "/js/**", "/login/css/**",
                "/login/images/**", "/logo/**",
                "/tags/**", "/xml/**","/css/**",
                "/layui/**","/layer/**","/images/**"
            };
        web.ignoring().antMatchers(antMatchers);
    }

    /**
     * 配置拦截规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] url = {"/login","/","/nopub/**","/druid/**"};//放行的路径
        http.authorizeRequests()//允许基于http请求的配置
                .antMatchers(url).permitAll()//登录页面放行
                .anyRequest().authenticated()//其他请求需要验证
                .and()
                .formLogin()//允许表单登陆
                .loginPage("/login")//登录页
                .loginProcessingUrl("/tologin")//表单登陆提交的路径
                //.successForwardUrl("/api/admin/goadmin.html")//登陆成功后跳转的路径
                .successHandler(myAuthenticationSuccessHander)//登陆成功后的业务逻辑
                .failureForwardUrl("/loginerror")//登陆失败返回的路径
                .and()
                .logout()//配置退出登录
                .logoutUrl("/logout")
                .addLogoutHandler(myLogoutHandler)//自定义退出登录
                .deleteCookies("JSESSIONID")//删除cookie
                .invalidateHttpSession(true)//删除session
                .and()
                .exceptionHandling()//异常配置
                .accessDeniedHandler(myAccessDeniedHandler);//没有权限访问返回的信息
        http.csrf().disable();//关闭csrf
        http.sessionManagement().invalidSessionUrl("/login");//session失效返回的路径
        //同一个账号只允许穿在1个session 存在多个 踢掉前一个 返回登陆界面
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry).expiredUrl("/login");

    }


}
