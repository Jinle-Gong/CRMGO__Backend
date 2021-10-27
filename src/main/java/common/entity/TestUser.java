package common.entity;

import com.javasm.common.service.DictionaryService;
import com.javasm.common.service.impl.DictionaryServiceImpl;
import org.aspectj.weaver.ast.Var;

/**
 * ClassName: TestUser
 *
 * @author
 * @version 0.1
 * @Description:
 * @date: 2020/8/15 10:13
 * @since JDK 1.8
 */
public class TestUser {
  private String userName;
  private String userSex;
  private String userNation;

  public TestUser() {
  }


  public TestUser(String userName, String userSex, String userNation) {
    this.userName = userName;
    this.userSex = userSex;
    this.userNation = userNation;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  public String getUserNation() {
    return userNation;
  }

  public void setUserNation(String userNation) {
    this.userNation = userNation;
  }

  @Override
  public String toString() {
    return "TestUser{" +
            "userName='" + userName + '\'' +
            ", userSex='" + userSex + '\'' +
            ", userNation='" + userNation + '\'' +
            '}';
  }
}
