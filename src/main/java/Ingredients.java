import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;


public class Ingredients extends MainRequest{

    public ArrayList<String> ingredients;

    private static final String INGREDIENTS_PATH = "ingredients";

    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Создание заказа с ингредиентами.")
    public static Ingredients orderIngredients() {
        ValidatableResponse response = given()
                .spec(baseSpecification())
                .when()
                .get(ingredients())
                .then()
                .statusCode(200);

        ArrayList<String> ingredients = new ArrayList<>();

        List<String> bun = response.extract().jsonPath().getList("data.findAll{it.type =='bun'}._id");
        List<String> sauce = response.extract().jsonPath().getList("data.findAll{it.type =='sauce'}._id");
        List<String> main = response.extract().jsonPath().getList("data.findAll{it.type =='main'}._id");

        ingredients.add(bun.get(nextInt(0,bun.size())));
        ingredients.add(sauce.get(nextInt(0,sauce.size())));
        ingredients.add(main.get(nextInt(0,main.size())));

        return new Ingredients(ingredients);
    }

    @Step("Создание заказа без ингредиентов.")
    public static Ingredients orderWithoutIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    @Step("Создание заказа с неверным хешем ингредиентов.")
    public static Ingredients orderWithIncorrectIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        String someIngredient = (RandomStringUtils.randomAlphabetic(3));

        ingredients.add(someIngredient);

        return new Ingredients(ingredients);
    }
}