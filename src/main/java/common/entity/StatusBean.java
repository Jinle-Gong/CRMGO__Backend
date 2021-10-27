package common.entity;

/**
 * @author JAVASM
 * @Classname StatusBean
 * @Description:
 * @Date 2020/8/3 18:34
 * @since JDK 1.8
 */
//失败时只返回状态码
public class StatusBean {
  private Integer code;
  private String msg;

  public Integer getCode() {
    return code;
  }

  public StatusBean(StatusEnum se) {
    this.code = se.getCode();
    this.msg = se.getMsg();
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
