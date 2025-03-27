package praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class OrderTest {
    private final OrderClient client = new OrderClient();

    @Test
    @DisplayName("Можно указать один из цветов — BLACK или GREY")
    public void specifyOneColor() {
        var order = new Order(new String[] {"BLACK"}); // Передаем массив цветов
        ValidatableResponse createResponse = client.createOrder(order);
        CommonAssertions.checkTrackExists(createResponse);
    }

    @Test
    @DisplayName("Можно указать оба цвета")
    public void specifyBothColors() {
        var order = new Order(new String[] {"BLACK", "GREY"}); // Передаем массив цветов
        ValidatableResponse createResponse = client.createOrder(order);
        CommonAssertions.checkTrackExists(createResponse);
    }

    @Test
    @DisplayName("Можно совсем не указывать цвет")
    public void doNotSpecifyColor() {
        var order = new Order(null); // Передаем null для отсутствия цвета
        ValidatableResponse createResponse = client.createOrder(order);
        CommonAssertions.checkTrackExists(createResponse);
    }

    @Test
    @DisplayName("Тело ответа содержит track")
    public void responseContainsTrack() {
        var order = new Order(new String[] {"GREY"}); // Передаем массив цветов
        ValidatableResponse createResponse = client.createOrder(order);
        CommonAssertions.checkTrackExists(createResponse);
    }
}