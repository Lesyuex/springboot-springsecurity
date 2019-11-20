package pres.menta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pres.menta.entity.User;

/**
 * 用户DAO类接口，继承JPA接口
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 10:13 2019/11/17
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return user
     */
    User getByUsername(String username);
}
