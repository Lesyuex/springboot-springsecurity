package pres.menta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pres.menta.entity.User;
import pres.menta.repository.UserRepository;

import java.util.List;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 11:17 2019/11/17
 */
@RestController
@RequestMapping("/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查找所有用户
     *
     * @return 用户集合
     */
    @GetMapping(value = "/listUser")
    public List<User> listUser() {
        return userRepository.findAll();
    }


    @GetMapping(value = "getByUsername")
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }}
