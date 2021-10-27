package common.entity;

/**
 * @author JAVASM
 * @Classname ResponseBean
 * @Description:
 * @Date 2020/8/3 18:42
 * @since JDK 1.8
 */
//成功时返回状态码和数据
public class ResponseBean extends StatusBean {
  private Object data;

  public ResponseBean(StatusEnum se, Object data) {
    super(se);
    this.data = data;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
