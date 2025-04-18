package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import java.util.Map;

public class CourierClient extends Client {

    private static final String COURIER = "courier";

    @Step("Логин курьера")
    public ValidatableResponse logIn(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER + "/login")
                .then().log().all();
    }

    @Step("Создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER)
                .then().log().all();
    }

    @Step("Удалить курьера")
    public void delete(int id) {
        spec()
                .body(Map.of("id", id))
                .when()
                .delete(COURIER + "/" + id)
                .then().log().all();
    }
}
