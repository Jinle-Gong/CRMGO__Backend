package system.service;

import system.entity.Authorization;

import java.util.List;

/**
 * @author Jeff Gong
 * @Classname AuthService
 * @Description:
 * @Date 2020/8/14 21:28
 */
public interface AuthService {
    boolean delete(Integer authId);

    boolean insert(Authorization record);

    Authorization selectByPrimaryKey(Integer authId);

    boolean update(Authorization record);

    List<Authorization> getAuthByProps(Authorization role);

    List<Authorization> getAllAuthorizations();

    List<String> selectDistinctAuth();

    void correctMenus(Authorization record);
}
