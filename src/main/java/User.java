import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User extends MainRequest{

    public String email;
    public String password;
    public String name;

    public User(String email, String password, String name) {

        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Валидное создание пользователя.")
    public static User validUserCreate() {

        final String email = (RandomStringUtils.randomAlphabetic(100) + "@yandex.ru").toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(100).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(100).toLowerCase();

        return new User(email, password, name);
    }

    @Step("Создание пользователя без обязательного поля Почта")
    public static User userCreateWithoutEmail() {

        final String password = RandomStringUtils.randomAlphabetic(100).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(100).toLowerCase();

        return new User(null, password, name);
    }

    @Step("Создание пользователя без обязательного поля Пароль")
    public static User userCreateWithoutPassword() {

        final String email = (RandomStringUtils.randomAlphabetic(100) + "@yandex.ru").toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(100).toLowerCase();

        return new User(email, null, name);
    }
    @Step("Создание пользователя без обязательного поля Имя")
    public static User userCreateWithoutName() {

        final String email = (RandomStringUtils.randomAlphabetic(100) + "@yandex.ru").toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(100).toLowerCase();

        return new User(email, password, null);
    }
}