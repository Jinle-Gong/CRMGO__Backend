package system.service;

import system.entity.Menu;
import system.entity.User;

import java.util.List;

/**
 * @author Jeff Gong
 * @Classname UserService
 * @Description:
 * @Date 2020/8/14 20:51
 */
public interface UserService {
    User getUser(User user);

    boolean insertUser(User user);

    String createValidateCode(String uphone);

    boolean checkValidateCode(String uphone, String valCode);

    User loginByPhone(String uphone, String valCode);

    User retrieveUser(String uphone);

    //多条件查询
    List<User> getUserByProps(User user);

    List<User> getAllUsers();

    //修改一项或多项
    boolean update(User user);

    boolean delete(Integer uid);

    List<Menu> getMenus();
}
