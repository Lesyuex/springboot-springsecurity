package pres.menta.service;

import pres.menta.entity.User;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/20]
 * @date Created by IntelliJ IDEA on 14:04 2019/11/20
 */
public interface UserService {
    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return user
     */
    User getByUsername(String username);
}
