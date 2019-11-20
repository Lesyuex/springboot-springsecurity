package pres.menta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.DigestUtils;
import pres.menta.service.impl.UserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SecurityConfiguration配置类，继承WebSecurityConfigurerAdapter对SpringSecurity进行配置
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 12:41 2019/11/17
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 认证
     * <p>
     * 重写configure(AuthenticationManagerBuilder auth)方法来配置认证方式，
     * 在auth.userDetailsService()方法中传入userService，userDetailsService()方法在用户登录时将会被自动调用。
     * passwordEncoder是可选项，可写可不写，因为我是将用户的明文密码生成了MD5消息摘要后存入数据库的，
     * 因此在登录时也需要对明文密码进行处理，所以就加上了passwordEncoder，加上passwordEncoder后，
     * 直接new一个PasswordEncoder匿名内部类即可，这里有两个方法要实现，看名字就知道方法的含义，
     * 第一个方法encode显然是对明文进行加密，这里使用MD5消息摘要，具体的实现方法是由commons-codec依赖提供的，
     * 第二个方法matches是密码的比对，两个参数，第一个参数是明文密码，
     * 第二个是密文，这里只需要对明文加密后和密文比较即可
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            /**
             * @param charSequence 明文
             * @return 密文
             */
            @Override
            public String encode(CharSequence charSequence) {
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }

            /**
             * @param charSequence 明文
             * @param s 密文
             * @return boolean
             */
            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes()));
            }
        });
    }

    /**
     * 授权
     * <p>
     * 重写configure(HttpSecurity http)方法配置授权规则。
     * authorizeRequests()方法表示开启了认证规则配置。
     * antMatchers("/admin/**").hasRole("超级管理员")表示/admin/**的路径需要有‘超级管理员’角色的用户才能访问。
     * 网上有人对hasRole方法中要不要加ROLE_前缀有疑问,这里是不要加的，如果用hasAuthority方法才需要加。
     * anyRequest().authenticated()表示其他所有路径都是需要认证/登录后才能访问。
     * login_page为去往登录界面的请求，登录处理路径为/login，登录用户名为username，密码为password，并配置了这些路径都可以直接访问，注销登陆也可以直接访问，最后关闭csrf。
     * 在successHandler中，使用response返回登录成功的json即可，切记不可以使用defaultSuccessUrl。
     * defaultSuccessUrl是只登录成功后重定向的页面，使用failureHandler也是由于相同的原因。
     *
     * @param http HttpSecurity对象
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //登录界面的路径，所有用户可以访问
                .antMatchers("/login_page").permitAll()
                // /admin/**的路径需要有‘超级管理员’角色的用户才能访问
                .antMatchers("/admin/**").hasRole("超级管理员")
                //所有请求需要登录认证
                .anyRequest().authenticated()
                .and()
                // 设置自定义登录的页面
                .formLogin().loginPage("/login_page")
                // 登录页表单提交的 action 及对应参数名字
                .loginProcessingUrl("/form_login").usernameParameter("username").passwordParameter("password").permitAll()
                //登录成功后做什么
                .successHandler(new AuthenticationSuccessHandler() {
                    /**
                     * @param httpServletRequest 对象
                     * @param httpServletResponse 对象
                     * @param authentication 对象
                     * @throws IOException 对象
                     * @throws ServletException 对象
                     */
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        System.out.println("onAuthenticationSuccess...");
                        httpServletResponse.sendRedirect("/index");
                    }
                })
                //登录失败后做什么
                .failureHandler(new AuthenticationFailureHandler() {
                    /**
                     * @param httpServletRequest 对象
                     * @param httpServletResponse 对象
                     * @param e 对象
                     * @throws IOException 对象
                     * @throws ServletException 对象
                     */
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        System.out.println("onAuthenticationFailure...");
                        httpServletResponse.sendRedirect("/login_page");
                    }
                })
                .and()
                //注销功能,注销成功跳转登录页
                .logout().logoutSuccessUrl("/login_page").permitAll()
                .and()
                //关闭跨站检测
                .csrf().disable();
    }

    /**
     * 过滤
     * <p>
     * 重写configure(WebSecurity web)方法用来配置过滤规则
     *
     * @param web WebSecurity对象
     * @throws Exception 异常
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/register")
                .antMatchers("/images/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    /**
     * 重写configureGlobal(AuthenticationManagerBuilder auth)方法为全局配置几个默认的用户
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring").password("123456").roles("LEVEL1", "LEVEL2")
                .and()
                .withUser("summer").password("123456").roles("LEVEL2", "LEVEL3")
                .and()
                .withUser("autumn").password("123456").roles("LEVEL1", "LEVEL3");
    }
}
