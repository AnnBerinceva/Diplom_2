import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTest {

    private UserClient userClient;
    User user;
    Order order;
    String accessToken;

    @Before
    public void init() {

        Token token = new Token();
        userClient = new UserClient();
        user = User.validUserCreate();
        order = new Order();
        userClient.createUser(user);
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя, авторизованный пользователь.")
    public void ordersListTokenTest() {

        String token = Token.receivingToken(user);
        Response response = Order.ordersListToken(token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders.total", notNullValue());
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя, неавторизованный пользователь.")
    public void ordersListWithoutTokenTest() {

        Response response = Order.ordersListWithoutToken();
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void delete() {
        userClient.delete(accessToken);
    }
}
