package common.service.impl;

import common.entity.Dictionary;
import common.entity.RedisKey;
import common.mapper.DictionaryMapper;
import common.service.DictionaryService;
import common.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DictionaryServiceImpl
 *
 * @author
 * @version 0.1
 * @Description:
 * @date: 2020/8/14 17:25
 * @since JDK 1.8
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    DictionaryMapper dicMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public void initDictionaryToRedis() {
        List<Dictionary> dictionaryList = dicMapper.findAll();
        initDictionaryToRedisMethod(dictionaryList);
    }

    @Override
    public void initDictionaryToRedisByFieldCode(String fieldCode) {
        List<Dictionary> dictionaryList = dicMapper.findListByFieldCode(fieldCode);
        initDictionaryToRedisMethod(dictionaryList);
    }

    private void initDictionaryToRedisMethod(List<Dictionary> dictionaryList) {
        if (dictionaryList.isEmpty()) {
            throw new RuntimeException("数据库中无该数据字典，无法初始化数据到redis!");
//            logger.warn("数据字典为空，无法初始化数据到redis!");//日志用
        } else {
            Map<String, String> dicMap = new HashMap<>(dictionaryList.size());
            for (Dictionary dic : dictionaryList) {
                System.out.println(dic.getFieldCode());
                dicMap.put(dic.getFieldCode() + "_" + dic.getDicKey(), dic.getDicValue());
            }
            redisUtil.hmset(RedisKey.DICTIONARY_KEY, dicMap);
            System.out.println("初始化数据字典到redis成功!");
//            logger.warn("初始化数据字典到redis成功!");//日志用
        }
    }


    @Override
    public String getDicValueFromRedis(String fieldCode, String dicKey) {
        fieldCode = camelToUnderline(fieldCode);//把驼峰转换成下划线格式
        String key = fieldCode + "_" + dicKey;
        String dicValue = redisUtil.hget(RedisKey.DICTIONARY_KEY, key);
        if (dicValue == null) {
            //第一次调用，需要把数据字典写到redis缓存
            initDictionaryToRedisByFieldCode(fieldCode);
//            getDicValueFromRedis(fieldCode,dicKey);//递归调用自身取出该字段的名称
            dicValue = redisUtil.hget(RedisKey.DICTIONARY_KEY, key);
        }
        return dicValue;
    }


    @Override
    public List<String> getMoreDicValueFromRedis(String fieldCode, String... dicKeys) {
        fieldCode = camelToUnderline(fieldCode);//把驼峰转换成下划线格式
        //构建哈希表的field
        for (int i = 0; i < dicKeys.length; i++) {
            dicKeys[i] = fieldCode + "_" + dicKeys[i];
        }
        List<String> dicValueList = redisUtil.hmget(RedisKey.DICTIONARY_KEY, dicKeys);
        //若divValue 的值为空
        if (dicValueList == null) {
            initDictionaryToRedisByFieldCode(fieldCode);
        }
//        int i = 0;
//        for (String dicValue : dicValueList) {
//            if (dicValue == null){
//                dicValueList.set(i,dicKeys[i]);
//                i++;
//            }
//        }
        return dicValueList;
    }

    //  首字母大写的方法
    public String upperFirstLatter(String letter) {
        return letter.substring(0, 1).toUpperCase() + letter.substring(1);
    }

    @Override
    public List getViewObjectList(String[] fieldCodes, List objectList) {

        for (Object o : objectList) {
            for (String fieldCode : fieldCodes) {
                try {
                    Method getmethod = o.getClass().getMethod("get" + upperFirstLatter(fieldCode), null);
                    Method setmethod = o.getClass().getMethod("set" + upperFirstLatter(fieldCode), String.class);
                    String str = getDicValueFromRedis(fieldCode, (String) getmethod.invoke(o, null));
                    setmethod.invoke(o, str);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return objectList;
    }

    @Override
    public List<Map<String, String>> getSetByFieldCode(String fieldCode) {

        List<Map<String, String>> setByFieldCode = dicMapper.findSetByFieldCode(fieldCode);

        return setByFieldCode;
    }

    @Override
    public String getKeyByFieldCodeAndValue(String fieldCode, String value) {
        return dicMapper.findKeyByFieldCodeAndValue(fieldCode, value);
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean getkeyByValue(String[] fieldCodes, Object object) {


        for (String fieldCode : fieldCodes) {

            try {
                Method getmethod = object.getClass().getMethod("get" + upperFirstLatter(fieldCode), null);
                Method setmethod = object.getClass().getMethod("set" + upperFirstLatter(fieldCode), String.class);
                String value = (String) getmethod.invoke(object, null);
                if (checkInt(value)) {
                    String str = getKeyByFieldCodeAndValue(camelToUnderline(fieldCode), (String) getmethod.invoke(object, null));
                    setmethod.invoke(object, str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean checkInt(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
