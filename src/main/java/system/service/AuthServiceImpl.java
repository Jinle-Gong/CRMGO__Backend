package system.service;

import org.springframework.stereotype.Service;
import system.entity.Authorization;
import system.mapper.AuthorizationMapper;
import system.mapper.MenuMapper;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Jeff Gong
 * @Classname AuthServiceImpl
 * @Description:
 * @Date 2020/8/14 21:28
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthorizationMapper authMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public boolean delete(Integer authId) {
        if (authMapper.deleteByPrimaryKey(authId) > 0) return true;
        return false;
    }

    @Override
    public boolean insert(Authorization record) {
        correctMenus(record);
        if (authMapper.insertSelective(record) > 0) return true;
        return false;
    }

    @Override
    public Authorization selectByPrimaryKey(Integer authId) {
        return authMapper.selectByPrimaryKey(authId);
    }

    @Override
    public boolean update(Authorization record) {
        correctMenus(record);
        if (authMapper.updateByPrimaryKeySelective(record) > 0) return true;
        return false;
    }

    @Override
    public List<Authorization> getAuthByProps(Authorization auth) {
        return authMapper.selectAuthByProps(auth);
    }

    @Override
    public List<Authorization> getAllAuthorizations() {
        return authMapper.getAllAuthorizations();
    }

    @Override
    public List<String> selectDistinctAuth() {
        return authMapper.selectDistinctAuth();
    }

    //辅助方法，当菜单中只有子菜单，缺少父类时，将父类添加进去
    public void correctMenus(Authorization record) {
        String[] menuId = record.getMenuId().split(",");
        System.out.println("menuId-----------------");
        System.out.println(Arrays.toString(menuId));
        Set<String> menuSet = new HashSet<>(Arrays.asList(menuId));
        for (String s : menuId) {
            String pid = menuMapper.getPid(s);
            if (pid != null && !pid.equals("0") && !menuSet.contains(pid)) menuSet.add(pid);
        }
        List<String> menuList = new ArrayList<>(menuSet);
        String menuStr = String.join(",", menuList);
        record.setMenuId(menuStr);
    }
}
