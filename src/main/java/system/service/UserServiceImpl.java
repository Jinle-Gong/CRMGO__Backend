package system.service;

import com.alibaba.fastjson.JSON;
import common.entity.RedisKey;
import common.util.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.entity.Menu;
import system.entity.User;
import system.mapper.AuthorizationMapper;
import system.mapper.MenuMapper;
import system.mapper.RoleMapper;
import system.mapper.UserMapper;
import system.util.CurrentLoginUser;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname UserServiceImpl
 * @Description:
 * @Date 2020/8/14 21:00
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil rs;
    @Resource
    private CurrentLoginUser currentLoginUser;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private AuthorizationMapper authMapper;

    //用于用户名密码登录
    @Override
    public User getUser(User user) {
        User obtainedUser = userMapper.getUser(user);
        return obtainedUser;
    }

    //开启事务
    @Transactional
    @Override
    public boolean insertUser(User user) {
        if (userMapper.insertSelective(user) > 0) return true;
        return false;
    }

    @Override
    public String createValidateCode(String uphone) {
        //生成六位验证码
        String serverCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        //redis暂存下验证码.
        //String,对key进行分组，使用冒号
        rs.setex(RedisKey.VALCODE_KEY + uphone, 5 * 60, serverCode);
        return serverCode;
    }

    @Override
    public User loginByPhone(String uphone, String valCode) {
        //检测验证码的正确性。
        boolean codeValid = checkValidateCode(uphone, valCode.trim());
        if (!codeValid) {
            return null;
        }
        //检测是否是用户。是的话，合法账户登录。执行登录逻辑
        //否的话，注册新用户，并加标志表示这是一个新用户，执行登录逻辑，返回
        User loginUser = retrieveUser(uphone);
        return loginUser;
    }

    @Override
    public boolean checkValidateCode(String uphone, String valCode) {
        String serverCode = rs.get(RedisKey.VALCODE_KEY + uphone);
        //验证码有效期五分钟，五分钟后为空
        if (serverCode != null && serverCode.equals(valCode)) {
            return true;
        }
        return false;
    }

    @Override
    public User retrieveUser(String uphone) {
        //从redis中查看用户是否存在
        String userStr = rs.hget(RedisKey.USER_KEY, uphone);//第1参数为map的名称，第2参数为map的key
        //如果不存在，从数据库获取用户信息，若存在，以hash类型把用户保存到redis
        if (userStr == null) {
            User temp = new User();
            temp.setUphone(uphone);
            //通过电话号码，查看数据库中是否存在用户记录
            User retrievedUser = userMapper.getUserByPhone(temp);
            if (retrievedUser != null) {
                //数据库存在记录，加入缓存
                rs.hset(RedisKey.USER_KEY, uphone, JSON.toJSONString(retrievedUser));
                return retrievedUser;
            } else {
                //说明用户未注册
                return null;
            }
        } else {
            //把userStr转成用户对象
            User parsedUser = JSON.parseObject(userStr, User.class);
            return parsedUser;
        }
    }

    @Override
    public List<User> getUserByProps(User user) {
        return userMapper.selectUserByProps(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public boolean update(User user) {
        if (userMapper.updateByPrimaryKeySelective(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer uid) {
        if (userMapper.deleteByPrimaryKey(uid) > 0) return true;
        return false;
    }

    @Override
    public List<Menu> getMenus() {
        System.out.println("当前用户 ------ " + currentLoginUser.getLoginUser());
        //从当前用户中拿到角色; 通过角色查询auth_id; 再通过auth_id获取menu_id
        String role = currentLoginUser.getLoginUser().getRole();
        String authId = roleMapper.getAuthIdByRole(role);
        String menuId = authMapper.getMenuIdByAuth(authId);
        List<Menu> firstLevel = menuMapper.selectMenuByLevel(menuId, 1);
        List<Menu> secondLevel = menuMapper.selectMenuByLevel(menuId, 2);
        List<Menu> thirdLevel = menuMapper.selectMenuByLevel(menuId, 3);
        for (Menu menuII : secondLevel) {
            for (Menu menuIII : thirdLevel) {
                if (menuII.getMid().equals(menuIII.getPid())) {
                    menuII.getChildren().add(menuIII);
                }
            }
        }
        for (Menu menu : firstLevel) {
            for (Menu menuII : secondLevel) {
                if (menu.getMid().equals(menuII.getPid())) {
                    menu.getChildren().add(menuII);
                }
            }
        }
        return firstLevel;
    }
}
