import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;

public class MainRequest {

    public static String orders() {
        return "/orders";
    }

    public static String ingredients() {
        return "/ingredients";
    }

    public static String authRegister() {
        return "/auth/register";
    }

    public static String loginUser() {
        return "/auth/login";
    }

    public static RequestSpecification baseSpecification(){

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .addFilter(new AllureRestAssured())
                .build();
    }
}