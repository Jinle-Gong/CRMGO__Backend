package system.controller;

import com.github.pagehelper.PageHelper;
import common.entity.PageData;
import common.entity.ResponseBean;
import common.entity.StatusBean;
import common.entity.StatusEnum;
import common.service.DictionaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import system.entity.Role;
import system.service.RoleService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname RoleController
 * @Description:
 * @Date 2020/8/15 16:34
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    RoleService roleService;
    @Resource
    DictionaryService dictionaryService;

    @GetMapping("/getAllRole")
    public ResponseEntity getAllRole() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, roles));
    }

    @GetMapping("/getAllRoleWithPage")
    public ResponseEntity getAllRoleWithPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleService.getAllRoles();
        dictionaryService.getViewObjectList(new String[]{"roleStatus", "deptName", "auth"}, roles);
        PageData info = new PageData(roles);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @GetMapping("/searchRoles")
    public ResponseEntity searchRoles(Role role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleService.getRolesByProps(role);
        if (roles != null && roles.size() != 0)
            dictionaryService.getViewObjectList(new String[]{"roleStatus", "deptName", "auth"}, roles);
        PageData info = new PageData(roles);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @DeleteMapping("/deleteRole/{rid}")
    public ResponseEntity deleteRole(@PathVariable("rid") int rid) {
        if (roleService.deleteRole(rid)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PutMapping("/updateRole")
    public ResponseEntity updateRole(@RequestBody Role role) {
        dictionaryService.getkeyByValue(new String[]{"roleStatus", "deptName", "auth"}, role);
        if (roleService.updateRole(role)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PostMapping("/insertRole")
    public ResponseEntity insertRole(@RequestBody Role role) {
        if (roleService.insertRole(role)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

// todo ?????????????????????auth ???menu id ????????? ????????????????????????????????????????????????????????????????????????????????????role?????????????????????id
//  ?????????role?????????id????????????????????????????????????????????????role???????????????????????????

//  ????????????????????????role???????????????????????????auth?????????
}
