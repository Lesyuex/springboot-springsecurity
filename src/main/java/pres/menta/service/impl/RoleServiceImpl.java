package pres.menta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pres.menta.entity.Role;
import pres.menta.repository.RoleRepository;
import pres.menta.service.RoleService;

/**
 * 功能描述
 *
 * @author JyrpoKoo
 * @version [版本号 2019/11/18]
 * @date Created by IntelliJ IDEA on 10:23 2019/11/18
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getById(Long id) {
        return roleRepository.getById(id);
    }
}
