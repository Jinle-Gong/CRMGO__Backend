package common.service;


import java.util.List;
import java.util.Map;

/**
 * ClassName: DictionaryService
 *
 * @author
 * @version 0.1
 * @Description: 数据字典的service层。
 * @date: 2020/8/14 17:23
 * @since JDK 1.8
 */
public interface DictionaryService {

    /**
     * 把数据库中的数据字典放入到redis中
     */
    void initDictionaryToRedis();

    /**
     * 通过fieldCode把数据字典放入到redis中
     */
    void initDictionaryToRedisByFieldCode(String fieldCode);

    /**
     * 根据指定 字段名 和 该字段值（0，1标号来表示） ，获得对应名称
     *
     * @param fieldCode 字段名
     * @param dicKey    字段值
     * @return
     */
    String getDicValueFromRedis(String fieldCode, String dicKey);

    /**
     * 根据指定 字段名 和 该字段值（0，1标号来表示） ，获得对应名称
     *
     * @param fieldCode 字段名
     * @param dicKeys   字段值
     * @return
     */
    List<String> getMoreDicValueFromRedis(String fieldCode, String... dicKeys);

    /**
     * 把从数据库中查出来的数据，加上数据字典，给前端传递过去
     *
     * @param string
     * @param objectList
     * @return
     */
    List getViewObjectList(String[] string, List objectList);

    /**
     * 根据fieldCode返回该组所有的记录
     *
     * @param fieldCode
     * @return
     */
    List<Map<String, String>> getSetByFieldCode(String fieldCode);

    /**
     * 根据fieldCode，value找到对应的key
     *
     * @param fieldCode
     * @param value
     * @return
     */
    String getKeyByFieldCodeAndValue(String fieldCode, String value);

    boolean getkeyByValue(String[] fieldCodes, Object object);

}
