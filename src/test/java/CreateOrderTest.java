import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {

    User user;
    Order order;
    String accessToken;

    @Before
    public void init() {

        UserClient userClient = new UserClient();
        user = User.validUserCreate();
        order = new Order();
        userClient.createUser(user);
        accessToken = Token.receivingToken(user);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией.")
    public void createOrderTokenTest() {
        Response response = Order.orderToken(Ingredients.orderIngredients(), accessToken);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации.")
    public void createOrderWithOutTokenTest() {
        Response response = Order.orderWithOutToken(Ingredients.orderIngredients());
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов.")
    public void createOrderWithoutIngredientsTest() {
        Response response = Order.orderWithOutToken(Ingredients.orderWithoutIngredients());
        response.then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов.")
    public void createOrderWithIncorrectIngredientsTest() {

        Response response = Order.orderWithOutToken(Ingredients.orderWithIncorrectIngredients());
        response.then()
                .assertThat()
                .statusCode(500);
    }

   @After
    public void delete() {
        if (accessToken != null) {
            UserClient.deleteUser(accessToken);
        }
    }
}