package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;
    private int performLoginAndGetId(Courier courier) {
        Credentials creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        return check.loginSuccess(loginResponse);
    }

    @Test
    @DisplayName("Курьер успешно создается")
    public void createCourierSuccess() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);
        courierId = performLoginAndGetId(courier);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCreationFailed() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        ValidatableResponse duplicateResponse = client.createCourier(courier);
        duplicateResponse.assertThat()
                .statusCode(409)
                .body("message", equalTo(check.message("Этот логин уже используется. Попробуйте другой.")));// Проверка ошибки дублирования
    }

    @Test
    @DisplayName("Необходимо передать все обязательные поля для создания курьера")
    public void missingFieldsInCourierCreationFailed() {
        var courier = new Courier(null, "password", "firstName"); // Пропущенное обязательное поле
        ValidatableResponse createResponse = client.createCourier(courier);
        createResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo(check.message("Недостаточно данных для создания учетной записи"))); // Проверка отсутствия обязательного поля
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void validRequestReturnsCorrectStatusCode() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);
    }


    @Test
    @DisplayName("Если отсутствует обязательное поле, запрос возвращает ошибку")
    public void missingFieldReturnsError() {
        var courier = new Courier(null, "password", "firstName"); // Пропущенное обязательное поле
        ValidatableResponse createResponse = client.createCourier(courier);
        createResponse.assertThat()
                .statusCode(400)
                .body("message", equalTo(check.message("Недостаточно данных для создания учетной записи"))); // Проверка отсутствия обязательного поля
    }

    @Test
    @DisplayName("Если создать курьера с логином, который уже существует, возвращается ошибка")
    public void existingLoginReturnsError() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        ValidatableResponse duplicateResponse = client.createCourier(courier);
        duplicateResponse.assertThat()
                .statusCode(409)
                .body("message", equalTo(check.message("Этот логин уже используется. Попробуйте другой."))); // Проверка существования логина
        courierId = performLoginAndGetId(courier);
    }

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            client.delete(courierId);
        }
    }
}