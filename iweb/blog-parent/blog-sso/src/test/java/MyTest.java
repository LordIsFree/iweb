import com.blog.BlogSSO;
import com.blog.data.param.LoginParams;
import com.blog.data.vo.Result;
import com.blog.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = BlogSSO.class)
public class MyTest {
    @Autowired
    LoginService loginService;
    @Test
    public void test1(){
        LoginParams loginParams = new LoginParams();
        loginParams.setAccount("admin");
        loginParams.setPassword("admin");
        Result result = loginService.login(loginParams);
        System.out.println(result);
    }
}
