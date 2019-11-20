package pres.menta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pres.menta.entity.User;
import pres.menta.repository.UserRepository;
import pres.menta.service.UserService;

/**
 * UserDetailsServiceImpl比较特殊，需要实现UserDetailsService接口
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 10:08 2019/11/17
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userService.getByUsername(username);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        System.out.println(user.toString());
        return user;
    }
}
