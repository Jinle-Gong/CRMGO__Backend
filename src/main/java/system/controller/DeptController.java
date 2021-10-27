package system.controller;

import com.github.pagehelper.PageHelper;
import common.entity.PageData;
import common.entity.ResponseBean;
import common.entity.StatusBean;
import common.entity.StatusEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import system.entity.Department;
import system.service.DeptService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname DeptController
 * @Description:
 * @Date 2020/8/15 16:35
 */
@Controller
@RequestMapping("/dept")
public class DeptController {
    @Resource
    DeptService deptService;

    @GetMapping("/getAllDept")
    public ResponseEntity getAllDept() {
        List<Department> depts = deptService.getAllDepts();
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, depts));
    }

    @GetMapping("/getAllDeptWithPage")
    public ResponseEntity getAllDeptWithPage(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Department> depts = deptService.getAllDepts();
        PageData info = new PageData(depts);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @DeleteMapping("/deleteDept/{deptId}")
    public ResponseEntity deleteDept(@PathVariable("deptId") int deptId) {
        if (deptService.delete(deptId)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PutMapping("/updateDept")
    public ResponseEntity updateDept(@RequestBody Department dept) {
        if (deptService.update(dept)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PostMapping("/insertDept")
    public ResponseEntity insertDept(@RequestBody Department dept) {
        if (deptService.insert(dept)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @GetMapping("/searchDept")
    public ResponseEntity searchUsers(Department dept, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> depts = deptService.selectDeptByProps(dept);
        PageData info = new PageData(depts);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }
}
