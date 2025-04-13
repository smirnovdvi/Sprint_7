package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.junit.Assert.assertNotNull;

public class CommonAssertions {
    @Step("Заказ успешно создан")
    public static void checkTrackExists(ValidatableResponse response) {
        Integer trackNumber = response
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        assertNotNull(trackNumber);
    }
}