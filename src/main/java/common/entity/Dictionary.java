package common.entity;

/**
 * ClassName: Dictionary
 *
 * @author
 * @version 0.1
 * @Description: 数据字典的实体类
 * @date: 2020/8/14 16:58
 * @since JDK 1.8
 */
public class Dictionary {
  private String fieldCode;
  private String fieldName;
  private Integer dicKey;
  private String dicValue;

  public Dictionary() {
  }

  public Dictionary(String fieldCode, String fieldName, Integer dicKey, String dicValue) {
    this.fieldCode = fieldCode;
    this.fieldName = fieldName;
    this.dicKey = dicKey;
    this.dicValue = dicValue;
  }

  public String getFieldCode() {
    return fieldCode;
  }

  public void setFieldCode(String fieldCode) {
    this.fieldCode = fieldCode;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Integer getDicKey() {
    return dicKey;
  }

  public void setDicKey(Integer dicKey) {
    this.dicKey = dicKey;
  }

  public String getDicValue() {
    return dicValue;
  }

  public void setDicValue(String dicValue) {
    this.dicValue = dicValue;
  }

  @Override
  public String toString() {
    return "Dictionary{" +
            "fieldCode='" + fieldCode + '\'' +
            ", fieldName='" + fieldName + '\'' +
            ", dicKey=" + dicKey +
            ", dicValue='" + dicValue + '\'' +
            '}';
  }
}
