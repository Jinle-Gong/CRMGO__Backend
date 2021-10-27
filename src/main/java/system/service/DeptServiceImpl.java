package system.service;

import org.springframework.stereotype.Service;
import system.entity.Department;
import system.mapper.DepartmentMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname DeptServiceImpl
 * @Description:
 * @Date 2020/8/14 21:31
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    DepartmentMapper deptMapper;

    @Override
    public boolean delete(Integer deptId) {
        if (deptMapper.deleteByPrimaryKey(deptId) > 0) return true;
        return false;
    }

    @Override
    public boolean insert(Department record) {
        if (deptMapper.insertSelective(record) > 0) return true;
        return false;
    }

    @Override
    public Department select(Integer deptId) {
        return deptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public boolean update(Department record) {
        if (deptMapper.updateByPrimaryKeySelective(record) > 0) return true;
        return false;
    }

    @Override
    public List<Department> getAllDepts() {
        return deptMapper.getAllDepts();
    }

    @Override
    public List<Department> selectDeptByProps(Department record) {
        return deptMapper.selectDeptByProps(record);
    }
}
