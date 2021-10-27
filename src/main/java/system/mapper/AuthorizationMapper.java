package system.mapper;

import system.entity.Authorization;

import java.util.List;

public interface AuthorizationMapper {
    int deleteByPrimaryKey(Integer authId);

    int insert(Authorization record);

    int insertSelective(Authorization record);

    Authorization selectByPrimaryKey(Integer authId);

    int updateByPrimaryKeySelective(Authorization record);

    int updateByPrimaryKey(Authorization record);

    //多条件查询
    List<Authorization> selectAuthByProps(Authorization authorization);

    List<Authorization> getAllAuthorizations();

    String getMenuIdByAuth(String aid);

    List<String> selectDistinctAuth();
}
