package system.mapper;

import system.entity.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer rid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByProps(Role role);

    List<Role> getAllRoles();

    String getAuthIdByRole(String role);
}
