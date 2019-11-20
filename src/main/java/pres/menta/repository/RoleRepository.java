package pres.menta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pres.menta.entity.Role;

import java.util.List;

/**
 * 角色DAO类接口，继承JPA接口
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/17]
 * @date Created by IntelliJ IDEA on 10:14 2019/11/17
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * 根据角色Id查询
     *
     * @param id 角色ID
     * @return Role
     */
    Role getById(Long id);

}
