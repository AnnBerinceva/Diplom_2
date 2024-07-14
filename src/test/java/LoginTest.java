import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {

    UserClient userClient;
    User user, incorrectUser;
    Token token;
    Order order;
    String accessToken;

    @Before
    public void init() {
        userClient = new UserClient();
        token = new Token();
        user = User.validUserCreate();
        order = new Order();
        userClient.createUser(user);
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Логин пользователя.")
    public void loginUserTest() {
        Response response = userClient.loginUser(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин пользователя - ошибка.")
    public void loginUserFailTest() {

        incorrectUser = User.validUserCreate();

        Response response = userClient.loginUser(incorrectUser);
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

    @After
    public void delete() {
        userClient.delete(accessToken);
    }
}