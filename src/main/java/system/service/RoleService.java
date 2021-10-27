package system.service;

import system.entity.Role;

import java.util.List;

/**
 * @author Jeff Gong
 * @Classname RoleService
 * @Description:
 * @Date 2020/8/14 21:29
 */
public interface RoleService {
    boolean insertRole(Role role);

    boolean deleteRole(Integer rid);

    //修改一项或多项
    boolean updateRole(Role role);

    Role getRole(Integer rid);

    //多条件查询
    List<Role> getRolesByProps(Role role);

    List<Role> getAllRoles();
}
