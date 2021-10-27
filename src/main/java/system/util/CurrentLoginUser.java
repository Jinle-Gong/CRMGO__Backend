package system.util;

import org.springframework.stereotype.Component;
import system.entity.User;
import system.service.UserService;

import javax.annotation.Resource;

/**
 * @author Jeff Gong
 * @Classname CurrentLoginUser
 * @Description: 若想在程序其他地方获取当前用户对象，从当前线程中获取
 * @Date 2020/8/11 20:17
 */
@Component
public class CurrentLoginUser {
    private ThreadLocal<User> user = new ThreadLocal<>();

    @Resource
    private UserService us;

    public void setLoginUser(String uphone) {
        User retrievedUser = us.retrieveUser(uphone);
        user.set(retrievedUser);
    }

    public User getLoginUser() {
        return user.get();
    }
}
