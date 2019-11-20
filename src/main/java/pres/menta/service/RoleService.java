package pres.menta.service;

import pres.menta.entity.Role;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/18]
 * @date Created by IntelliJ IDEA on 10:23 2019/11/18
 */
public interface RoleService {
    /**
     * 根据角色ID查询角色
     * @param id id
     * @return 角色对象
     */
    Role getById(Long id);
}
