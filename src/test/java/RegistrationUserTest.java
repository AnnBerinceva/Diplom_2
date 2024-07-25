import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class RegistrationUserTest {

    User user;
    UserClient userClient;
    Token token;
    String accessToken;

    @Before
    public void init() {

        userClient = new UserClient();
        token = new Token();
        user = User.validUserCreate();
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Создание уникального пользователя.")
    public void createUserTest() {
        Response response = userClient.createUser(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание уже существующего пользователя")
    public void failRegistrationExistedUserTest() {

        User user = User.validUserCreate();
        userClient.createUser(user);
        Response response = userClient.createUser(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Имя")
    public void userCreateWithoutNameFail() {

        User user = User.userCreateWithoutName();
        Response response = userClient.createUser(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Пароль")
    public void userCreateWithoutPasswordFail() {

        User user = User.userCreateWithoutPassword();
        Response response = userClient.createUser(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля Почта")
    public void userCreateWithoutEmailFail() {

        User user = User.userCreateWithoutEmail();
        Response response = userClient.createUser(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void delete() {

        String accessToken = Token.receivingToken(user);
        UserClient.deleteUser(accessToken);
    }
}