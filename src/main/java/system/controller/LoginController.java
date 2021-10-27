package system.controller;

import common.entity.RedisKey;
import common.entity.ResponseBean;
import common.entity.StatusBean;
import common.entity.StatusEnum;
import common.exception.MyException;
import common.util.JwtUtil;
import common.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import system.entity.Menu;
import system.entity.User;
import system.service.UserService;
import system.util.CurrentLoginUser;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Resource
    RedisUtil redisService;
    @Resource
    CurrentLoginUser currentLoginUser;

    //通过手机号登录，获取验证码
    @PostMapping("valCode")
    public ResponseEntity validateCode(String uphone) {
        if (userService.retrieveUser(uphone) != null) {
            System.out.println("正在获取验证码---uphone---" + uphone);
            String validateCode = userService.createValidateCode(uphone);
            System.out.println(validateCode);
            return ResponseEntity.ok(new StatusBean(StatusEnum.OPS_SUC));
        } else {
            throw new MyException(StatusEnum.PHONE_INVALID);
        }
    }

    /**
     * 输入验证码，点击登录
     *
     * @param map:{"uphone":123123123,"valCode":"1231231"}
     * @param session
     * @return
     */
    @PostMapping("loginByPhone")
    public ResponseEntity login(@RequestBody Map map) {
        String uphone = (String) map.get("uphone");
        String valCode = (String) map.get("valCode");
        User user = userService.loginByPhone(uphone, valCode);
        if (user == null) {
            throw new MyException(StatusEnum.LOGIN_ERROR);
        } else {
            //登录成功，生成token字符串
            Map<String, Object> cliam = new HashMap<>();
            cliam.put("uphone", uphone);
            String token = JwtUtil.generate(cliam);
            //把token放在响应头中，返回给客户端
            user.setPassword("");
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", token);
            return new ResponseEntity(new ResponseBean(StatusEnum.LOGIN_SUC, user), headers, HttpStatus.OK);
        }
    }

    @PostMapping("/loginByPassword")
    public ResponseEntity login(@RequestBody User user) {
        User retrievedUser = userService.getUser(user);
        if (retrievedUser != null) {
            Map<String, Object> cliam = new HashMap<>();
            cliam.put("uphone", retrievedUser.getUphone());
            String token = JwtUtil.generate(cliam);
            //把token放在响应头中，返回给客户端
            retrievedUser.setPassword("");
            HttpHeaders headers = new HttpHeaders();
            headers.add("token", token);
            return new ResponseEntity(new ResponseBean(StatusEnum.LOGIN_SUC, retrievedUser), headers, HttpStatus.OK);
        } else {
            throw new MyException(StatusEnum.LOGIN_ERROR);
        }
    }

    //退出时，将token加入黑名单
    @GetMapping("logout")
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisService.setex(RedisKey.BLACKLIST_KEY + token, JwtUtil.getRemainTime(token), "123");
    }

    @GetMapping("/getMenus")
    public ResponseEntity getMenus() {
        List<Menu> menus = userService.getMenus();
        return ResponseEntity.ok(new ResponseBean(StatusEnum.OPS_SUC, menus));
    }

}
