package system.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import system.entity.Authorization;
import system.entity.Department;
import system.entity.User;
import system.mapper.AuthorizationMapper;
import system.mapper.MenuMapper;
import system.mapper.RoleMapper;
import system.service.AuthService;
import system.service.DeptService;
import system.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jeff Gong
 * @Classname UserTest
 * @Description:
 * @Date 2020/8/14 22:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class UserTest {
    @Resource
    private UserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private AuthService authService;
    @Resource
    private AuthorizationMapper authorizationMapper;

    @Test
    public void testGetUserByPhone() {
        User user = userService.retrieveUser("13576521316");
        System.out.println(user);
    }

    @Test
    public void testCorrectMenu() {
        Authorization authorization = new Authorization();
        authorization.setAuthName("abc");
        authorization.setMenuId("40004");
        authService.correctMenus(authorization);
        System.out.println(authorization);
    }

    @Test
    public void testGetMenuPid() {
        System.out.println(menuMapper.getPid("40004"));
    }

    @Test
    public void insertAuthTest() {
        Authorization authorization = new Authorization();
        authorization.setAuthName("abc");
        authorization.setMenuId("200,20002,20001");
        System.out.println(authorizationMapper.insertSelective(authorization));
    }

    @Test
    public void testGetDistinctAuth() {
        List<String> auths = authService.selectDistinctAuth();
        System.out.println(auths);
    }

    @Test
    public void testLoginByPassword() {
        User temp = new User();
        temp.setUsername("admin");
        temp.setPassword("admin");
        User user = userService.getUser(temp);
        System.out.println(user);
    }

    @Test
    public void getUserByPropTest() {
        User temp = new User();
        temp.setAccountStatus("1");
        temp.setDeptName("3");
        temp.setGender("1");
        temp.setPosition("总监");
        temp.setRole("销售总监");
        temp.setPosition("总监");
        temp.setUname("赵家瑞");
        temp.setUsername("zhao");
        List<User> users = userService.getUserByProps(temp);
        System.out.println(users);
    }

    @Test
    public void insertUserTest() {
        User temp = new User();
        temp.setDeptName("3");
        temp.setUsername("123");
        temp.setPassword("123");
        temp.setUphone("123");
        System.out.println(userService.insertUser(temp));
    }

    @Test
    public void updateUserTest() {
        User temp = new User();
        temp.setUsername("12111");
        temp.setPassword("123");
        temp.setUphone("123");
        temp.setUid(11);
        System.out.println(userService.update(temp));
    }

    @Test
    public void deptTest() {
        Department dept = new Department();
        dept.setDeptName("信息");
        System.out.println(deptService.selectDeptByProps(dept));
    }

    @Test
    public void getSubmenuTest() {
        User user = new User();
        user.setRole("9");
//    String menuId = roleMapper.getMenuIdByRole(user.getRole());
//    System.out.println("menuId   ------   "+menuId);
//    List<Menu> firstLevel = menuMapper.selectMenuByLevel(menuId, 1);
//    System.out.println("first level -------" + firstLevel);
    }
}
