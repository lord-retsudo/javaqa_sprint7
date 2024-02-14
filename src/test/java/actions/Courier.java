package actions;

import data.ApiUrls;
import data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {

    @Step("Create courier and get Response")
    public Response createCourierAndGetResponse(CourierData courierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(courierData)
                .when()
                .post(ApiUrls.COURIER);
        return response;
    }

    @Step("Courier authorization and get response")
    public Response getResponseLoginCourier(CourierData courierData) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierData)
                .when()
                .post(ApiUrls.LOGIN);
        return response;
    }

    @Step("Remove courier")
    public void deleteCourier(String idCourier) {
        given()
                .auth().oauth2("")
                .delete(ApiUrls.COURIER + idCourier);
    }
}
