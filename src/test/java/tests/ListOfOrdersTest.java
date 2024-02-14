package tests;

import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import actions.Order;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

@DisplayName("Get list of orders")
public class ListOfOrdersTest extends BaseTest {

    @Test
    @DisplayName("Test of getting list of orders")
    @Description("getting list of orders")
    public void checkListOfOrderNotNull() {
        Order order = new Order();
        OrderData orderData = new OrderData();
        order.getListOfOrders()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
