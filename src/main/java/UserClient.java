import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;

/**
 * Создание пользователя.
 * Шаги: создание, логин, изменение и удаление пользователя.
 */
public class UserClient extends MainRequest{
     @Step("Создание уникального пользователя.")
     public Response createUser(User user){
         return given()
                 .spec(baseSpecification())
                 .and()
                 .body(user)
                 .when()
                 .post(authRegister());
     }

     @Step("Логин пользователя.")
     public Response loginUser(User user) {
         return given()
                 .spec(baseSpecification())
                 .when()
                 .and()
                 .body(user)
                 .post(loginUser());
     }

     @Step ("Изменение данных пользователя c token.")
     public Response changeUser(User user, String authentication) {

         return given()
                 .headers(
                         "Authorization", "Bearer " + authentication,
                         "Content-Type",
                         ContentType.JSON,
                         "Accept",
                         ContentType.JSON)
                 .spec(baseSpecification())
                 .when()
                 .and()
                 .body(user)
                 .patch("/auth/user");
     }

     @Step ("Изменение данных пользователя без token.")
     public Response changeUserWithOutToken(User user) {
         return given()
                 .spec(baseSpecification())
                 .when()
                 .body(user)
                 .and()
                 .patch(authUser());
     }

     @Step ("Выход из системы.")
     public void logoutUser(String token){
         given()
                 .spec(baseSpecification())
                 .when()
                 .and()
                 .body(token)
                 .post("/auth/logout");
     }

     @Step("Регистрация и логин клиента")
     public void createLoginUser(User user) {

         createUser(user);
         loginUser(user);
     }

     @Step ("Удаление пользователя.")
     public static void deleteUser(String token) {
         given()
                 .header("Authorization", "Bearer " + authentication,
                         "Content-Type",
                         ContentType.JSON,
                         "Accept",
                         ContentType.JSON)
                 .spec(baseSpecification())
                 .when()
                 .delete("auth/user")
                 .then()
                 .statusCode(202);
     }
     public void delete(String accessToken) {
     }
 }