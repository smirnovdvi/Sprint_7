package praktikum.courier;

import io.qameta.allure.Step;
import lombok.Value;

@Value
public class Credentials {
    String login;
    String password;

    @Step("Создание учетных данных из курьера")
    public static Credentials fromCourier(Courier courier) {
        return new Credentials(courier.getLogin(), courier.getPassword());
    }
}