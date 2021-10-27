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
import system.entity.Authorization;
import system.service.AuthService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname AdminController
 * @Description:
 * @Date 2020/8/15 16:35
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Resource
    AuthService authService;
    @Resource
    DictionaryService dictionaryService;

    @GetMapping("/getAllAuthWithPage")
    public ResponseEntity getAllAuthWithPage(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "5") Integer pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Authorization> auths = authService.getAllAuthorizations();
        if (auths != null && auths.size() != 0)
            dictionaryService.getViewObjectList(new String[]{"authStatus"}, auths);
        PageData info = new PageData(auths);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

    @GetMapping("/getAllAuth")
    public ResponseEntity getAllAuth() {
        List<Authorization> auths = authService.getAllAuthorizations();
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, auths));
    }

    @DeleteMapping("/deleteAuth/{aid}")
    public ResponseEntity deleteAuth(@PathVariable("aid") int aid) {
        if (authService.delete(aid)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PutMapping("/updateAuth")
    public ResponseEntity updateAuth(@RequestBody Authorization auth) {
        dictionaryService.getkeyByValue(new String[]{"authStatus"}, auth);
        if (authService.update(auth)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @PostMapping("/insertAuth")
    public ResponseEntity insertAuth(@RequestBody Authorization auth) {
        if (authService.insert(auth)) return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        else return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_ERROR));
    }

    @GetMapping("/getDistinctAuth")
    public ResponseEntity getDistinctAuth() {
        List<String> auths = authService.selectDistinctAuth();
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, auths));
    }

    @GetMapping("/searchAuth")
    public ResponseEntity searchAuth(Authorization auth, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Authorization> auths = authService.getAuthByProps(auth);
        if (auths != null && auths.size() != 0)
            dictionaryService.getViewObjectList(new String[]{"authStatus"}, auths);
        PageData info = new PageData(auths);
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, info));
    }

}
