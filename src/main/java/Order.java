import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * Создание заказа.
 * Шаги: создание заказа с/без авторизации, получение списка заказов
 */
public class Order extends MainRequest{
    @Step("Создание заказа с авторизацией.")
    public static Response orderToken(Ingredients ingredients, String token){
        return given()
                .spec(baseSpecification())
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(ingredients)
                .when()
                .post(orders());
    }

    @Step("Создание заказа без авторизации.")
    public static Response orderWithOutToken(Ingredients ingredients) {
        return given()
                .spec(baseSpecification())
                .body(ingredients)
                .when()
                .post(orders());
    }

    @Step("Получение заказов конкретного пользователя, авторизованный пользователь.")
    public static Response ordersListToken(String token) {
        return given()
                .headers(
                        "Authorization", "Bearer " + token,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(baseSpecification())
                .when()
                .get(orders());
    }

    @Step("Получение заказов конкретного пользователя, неавторизованный пользователь.")
    public static Response ordersListWithoutToken() {
        return given()
                .spec(baseSpecification())
                .when()
                .get(orders());
    }
}