package praktikum.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest {
    private final OrderClient client = new OrderClient();
    private String[] colors;

    public OrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        });
    }

    @Test
    @DisplayName("Проверка создания заказа с различными цветами")
    public void testCreateOrder() {
        var order = new Order(colors);
        ValidatableResponse createResponse = client.createOrder(order);
        CommonAssertions.checkTrackExists(createResponse);
    }
}