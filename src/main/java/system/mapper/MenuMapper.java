package system.mapper;

import org.apache.ibatis.annotations.Param;
import system.entity.Menu;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectMenuByLevel(@Param("menuId") String menuId, @Param("level") Integer level);

    String getPid(String mid);
}
