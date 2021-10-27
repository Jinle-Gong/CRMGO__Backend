import com.javasm.common.entity.TestUser;
import com.javasm.common.service.DictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TestDictionary
 *
 * @author
 * @version 0.1
 * @Description:
 * @date: 2020/8/15 10:09
 * @since JDK 1.8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestDictionary {

  @Resource
  private DictionaryService dictionaryService;


  @Test
  public void testMethod() {

    System.out.println("模拟一个数据表，说明使用");

    List<TestUser> userList = new ArrayList<>();
    userList.add(new TestUser("小花", "1", "1"));
    userList.add(new TestUser("小明", "2", "2"));

    String[] strings = {"userSex", "userNation"};
//        String userSex = dictionaryService.getDicValueFromRedis("userSex", testUser.getUserSex());
//        String userNation = dictionaryService.getDicValueFromRedis("userNation", testUser.getUserNation());

//        testUser.setUserSex(userSex);
//        testUser.setUserNation(userNation);

    List viewObjectList = dictionaryService.getViewObjectList(strings, userList);
    System.out.println(viewObjectList);

//        System.out.println(testUser);

  }


}
