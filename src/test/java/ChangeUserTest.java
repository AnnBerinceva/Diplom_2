import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserTest {

    UserClient userClient;
    Token token;
    User user, newUser;
    String accessToken;

    @Before
    public void init() {
        userClient = new UserClient();
        token = new Token();
        user = User.validUserCreate();
        newUser = User.validUserCreate();
        userClient.createLoginUser(user);
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Изменение данных пользователя c token.")
    public void changeUserTest() {
        Response response = userClient.changeUser(newUser, accessToken);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(newUser.email))
                .body("user.name", equalTo(newUser.name));
    }

    @Test
    @DisplayName("Изменение данных пользователя без token.")
    public void changeUserWithOutTokenTest() {

        Response response = userClient.changeUserWithOutToken(newUser);
        response.then()
                .assertThat()
               .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
    @After
    public void delete() {
        if (accessToken != null) {
            UserClient.deleteUser(accessToken);
        }
    }
}