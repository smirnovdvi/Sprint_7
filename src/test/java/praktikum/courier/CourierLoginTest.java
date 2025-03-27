package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotEquals;

public class CourierLoginTest {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void courierCanLogIn() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loginSuccess(loginResponse);

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    public void allFieldsAreRequiredForLogin() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var incompleteCreds = new Credentials(null, "password"); // Пропущенное обязательное поле
        ValidatableResponse loginResponse = client.logIn(incompleteCreds);
        loginResponse.assertThat()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа")); // Проверка отсутствия обязательного поля
    }

    @Test
    @DisplayName("Система вернет ошибку, если неправильно указать логин или пароль")
    public void invalidLoginOrPasswordReturnsError() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var incorrectCreds = new Credentials("incorrectLogin", "incorrectPassword"); // Неправильные учетные данные
        ValidatableResponse loginResponse = client.logIn(incorrectCreds);
        loginResponse.assertThat()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена")); // Проверка наличия курьера
    }

    @Test
    @DisplayName("Если какого-то поля нет, запрос возвращает ошибку")
    public void missingFieldReturnsError() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        // Отправляем запрос с отсутствующим полем 'login'
        var incompleteCreds = new Credentials(null, "password"); // Пропущенное обязательное поле
        ValidatableResponse loginResponse = client.logIn(incompleteCreds);
        loginResponse.assertThat()
                .statusCode(400)
                .body("message", containsString("Недостаточно данных для входа")); // Проверка отсутствия обязательного поля
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void nonexistentUserReturnsError() {
        var nonexistentCreds = new Credentials("nonexistentLogin", "password"); // Несуществующий курьер
        ValidatableResponse loginResponse = client.logIn(nonexistentCreds);
        loginResponse.assertThat()
                .statusCode(404)
                .body("message", containsString("Учетная запись не найдена")); // Проверка существования курьера
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void successfulLoginReturnsId() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loginSuccess(loginResponse);

        assertNotEquals(0, courierId); // Проверка возврата id
    }
}