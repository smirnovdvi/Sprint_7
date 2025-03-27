package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.orders.OrderClient;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class ListOrdersTest {
    private final OrderClient client = new OrderClient();

    @Test
    @DisplayName("В тело ответа возвращается список заказов")
    public void listOrdersReturnsList() {
        ValidatableResponse response = client.listOrders();
        List<?> orders = response
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders"); // предполагаем, что поле "orders" содержит массив заказов
        assertFalse(orders.isEmpty());
    }
}