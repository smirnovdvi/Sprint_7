package praktikum.courier;

import io.qameta.allure.Step;
import lombok.Value;

import java.util.concurrent.ThreadLocalRandom;

@Value
public class Courier {
    String login;
    String password;
    String firstName;

    @Step("Генерация случайного курьера")
    public static Courier random() {
        int suffix = ThreadLocalRandom.current().nextInt(100, 100_000);
        return new Courier("Jack" + suffix, "P@ssw0rd123", "Sparrow");
    }
}