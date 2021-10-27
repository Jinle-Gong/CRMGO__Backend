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
import system.entity.User;
import system.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname UserController
 * @Description:
 * @Date 2020/8/15 16:34
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    DictionaryService dictionaryService;

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.getAllUsers();
        dictionaryService.getViewObjectList(new String[]{"accountStatus", "gender", "deptName", "role"}, users);
        PageData info = new PageData(users);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @GetMapping("/searchUsers")
    public ResponseEntity searchUsers(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.getUserByProps(user);
        if (users != null && users.size() != 0)
            dictionaryService.getViewObjectList(new String[]{"accountStatus", "gender", "deptName", "role"}, users);
        PageData info = new PageData(users);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @DeleteMapping("/deleteUser/{uid}")
    public ResponseEntity deleteUser(@PathVariable("uid") int uid) {
        if (userService.delete(uid)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PutMapping("/updateUser")
    public ResponseEntity updateUser(@RequestBody User user) {
        dictionaryService.getkeyByValue(new String[]{"accountStatus", "gender", "deptName", "role"}, user);
        if (userService.update(user)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PostMapping("/insertUser")
    public ResponseEntity insertUser(@RequestBody User user) {
        dictionaryService.getkeyByValue(new String[]{"accountStatus", "gender", "deptName", "role"}, user);
        if (userService.insertUser(user)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

}
