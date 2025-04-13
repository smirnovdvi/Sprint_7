package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

public class OrderClient extends Client {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post("/orders")
                .then().log().all();
    }

    @Step("Список заказов получен")
    public ValidatableResponse listOrders() {
        return spec()
                .when()
                .get("/orders")
                .then().log().all();
    }
}
