package system.mapper;

import system.entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUserByPhone(User user);

    User getUser(User user);//验证用户名和密码，用于登录

    List<User> selectUserByProps(User user);

    List<User> getAllUsers();

}
