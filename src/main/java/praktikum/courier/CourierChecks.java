package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertTrue;

public class CourierChecks {
    @Step("Проверка успешной авторизации курьера")
    public int loginSuccess(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id")
                ;
        return id;
    }
    @Step("Проверка успешности создания пользователя")
    public void created(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok")
                ;
        assertTrue(created);
    }

    @Step("Формирование сообщения об ошибке")
    public String message(String expectedMessage) {
        return expectedMessage;
    }
}