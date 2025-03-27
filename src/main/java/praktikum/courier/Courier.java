package praktikum.courier;

import io.qameta.allure.Step;

import java.util.concurrent.ThreadLocalRandom;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    @Step("Генерация случайного курьера")
    public static Courier random() {
        int suffix = ThreadLocalRandom.current().nextInt(100, 100_000);
        return new Courier("Jack" + suffix, "P@ssw0rd123", "Sparrow");
    }
    @Step("Получение логина курьера")
    public String getLogin() {
        return login;
    }
    @Step("Получение пароля курьера")
    public String getPassword() {
        return password;
    }
    @Step("Получение имени курьера")
    public String getFirstName() {
        return firstName;
    }
}
