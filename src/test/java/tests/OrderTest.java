package tests;

import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import actions.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Test of creating order with various colors")
@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private List<String> color = null;

    @Parameterized.Parameters (name = "Цвет самоката: {0}")
    public static Object[][] getCredentials() {
        return new Object[][] {
                { List.of("Black")},
                { List.of("Gray")},
                { List.of("Black", "Gray")},
                { List.of("")},
        };
    }

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Test
    @DisplayName("Test of creating order with various colors or without color")
    @Story("Order")
    @Description("Send color BLACK или GREY at order, both colors or without color")
    public void checkCreateOrder(){
        Order order = new Order();
        OrderData orderData = new OrderData("Ivan",
                "Ivanov",
                "Lenina 13",
                4,
                "+79148888888",
                1,
                "2024-02-12",
                "no comment",
                new String[]{color.toString()});

        order.createOrderGetResponse(orderData).then().assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}
