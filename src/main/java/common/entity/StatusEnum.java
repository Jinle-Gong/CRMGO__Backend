package common.entity;

/**
 * @author JAVASM
 * @Classname StatusEnum
 * @Description:
 * @Date 2020/8/3 18:30
 * @since JDK 1.8
 */
public enum StatusEnum {
  OPS_SUC(20000, "操作成功"),
  OPS_ERROR(50000, "操作失败"),
  LOGIN_SUC(20001, "登录成功"),
  LOGIN_ERROR(50001, "用户名密码有误!"),
  PHONE_INVALID(50002, "手机号码不存在"),
  UPLOAD_SUC(20002, "文件上传成功"),
  UPLOAD_ERROR(50002, "文件上传失败"),
  NO_LOGIN(50003, "未登陆"),
  TOKEN_EXPIRE(52000, "TOKEN过期"),
  TOKEN_INVALID(53000, "TOKEN无效"),;

  private Integer code;
  private String msg;

  StatusEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
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
