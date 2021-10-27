package common.entity;

/**
 * @author Jeff Gong
 * @Classname RedisKey
 * @Description:
 * @Date 2020/8/8 15:04
 */
public interface RedisKey {
  static final String VALCODE_KEY = "code:";
  static final String USER_KEY = "userHash";
  static final String WEATHER_KEY = "weacherList:";
  static final String BLACKLIST_KEY = "blacklist:";
  static final String DICTIONARY_KEY = "dictionaryHash";//数据字典

  /**
   * 商品类型 树结构
   */
  String TYPE_TREE = "typeTree";

}
