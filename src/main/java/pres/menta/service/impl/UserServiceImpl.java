package pres.menta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pres.menta.entity.User;
import pres.menta.repository.UserRepository;
import pres.menta.service.UserService;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/20]
 * @date Created by IntelliJ IDEA on 14:05 2019/11/20
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }


}
