package system.mapper;

import system.entity.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepts();

    List<Department> selectDeptByProps(Department record);
}
