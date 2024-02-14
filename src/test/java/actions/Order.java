package actions;

import data.ApiUrls;
import data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order {

    @Step("Create order and get response")
    public Response createOrderGetResponse(OrderData orderData){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(orderData)
                        .when()
                        .post(ApiUrls.ORDERS);
        return response;
    }

    @Step("Get order list")
    public Response getListOfOrders(){
        Response response = given()
                .get(ApiUrls.ORDERS);
        return response;
    }
}
