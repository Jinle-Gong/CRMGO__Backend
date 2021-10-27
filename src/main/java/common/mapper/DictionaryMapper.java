package common.mapper;

import common.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: Dictionary
 *
 * @author
 * @version 0.1
 * @Description: 数据字典类的dao层。
 * @date: 2020/8/14 17:07
 * @since JDK 1.8
 */

public interface DictionaryMapper {
    /**
     * 获得字典表中的所有数据，在初始化的时候放入到redis缓存中
     *
     * @return
     */
    List<Dictionary> findAll();

    /**
     * 通过fileCode字段查找到该组的所有key-value
     *
     * @param fieldCode
     * @return
     */
    List<Dictionary> findListByFieldCode(String fieldCode);

    /**
     * 根据fieldCode找到该组下所有的key，value
     *
     * @param fieldCode
     * @return
     */
    List<Map<String, String>> findSetByFieldCode(String fieldCode);

    /**
     * 根据f，key找到ieldCode对应的value
     *
     * @param fieldCode
     * @param value
     * @return
     */
    String findKeyByFieldCodeAndValue(@Param("field_code") String fieldCode, @Param("dic_value") String value);

}
