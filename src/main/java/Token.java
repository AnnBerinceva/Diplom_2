import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Token extends MainRequest{

    @Step("Получение токена доступа.")
    public static String receivingToken(User user) {
        String accessToken = "";
        Response response = given()
                .spec(baseSpecification())
                .and()
                .body(user)
                .when()
                .post(loginUser());

        if (response.statusCode() == 200) {
            JsonPath path = response.jsonPath();

            String fullAccessToken = path.get("accessToken");
            int lastFileSeparatorIndex = fullAccessToken.lastIndexOf("Bearer ");
            accessToken = fullAccessToken.substring(lastFileSeparatorIndex + 7);
        }
        return accessToken;
    }

    @Step("Обновление токена.")
    public String refreshToken(UserClient user) {
        String refreshToken = "";
        Response response = given()
                .spec(baseSpecification())
                .and()
                .body(user)
                .when()
                .post(loginUser());

        if (response.statusCode() == 200) {
            JsonPath path = response.jsonPath();
            refreshToken = path.get("refreshToken");
        }
        return refreshToken;
    }
}