package system.service;

import org.springframework.stereotype.Service;
import system.entity.Role;
import system.mapper.RoleMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname RoleServiceImpl
 * @Description:
 * @Date 2020/8/14 21:29
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public boolean insertRole(Role role) {
        //TODO 遍历查看父类menu id是否在数组内，然后修改role实体类和mapper, 添加menu id字段
        if (roleMapper.insert(role) > 0) return true;
        return false;
    }

    @Override
    public boolean deleteRole(Integer rid) {
        if (roleMapper.deleteByPrimaryKey(rid) > 0) return true;
        return false;
    }

    @Override
    public boolean updateRole(Role role) {
        //TODO 遍历查看父类menu id是否在数组内，然后修改role实体类和mapper, 添加menu id字段
        if (roleMapper.updateByPrimaryKeySelective(role) > 0) return true;
        return false;
    }

    @Override
    public Role getRole(Integer rid) {
        return roleMapper.selectByPrimaryKey(rid);
    }

    @Override
    public List<Role> getRolesByProps(Role role) {
        return roleMapper.selectRoleByProps(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }
}
