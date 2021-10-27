package system.service;

import system.entity.Department;

import java.util.List;

/**
 * @author Jeff Gong
 * @Classname DeptService
 * @Description:
 * @Date 2020/8/14 21:31
 */
public interface DeptService {
    boolean delete(Integer deptId);

    boolean insert(Department record);

    Department select(Integer deptId);

    boolean update(Department record);

    List<Department> getAllDepts();

    List<Department> selectDeptByProps(Department record);
}
