package tests;

import actions.Courier;
import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("Tests of courier authorization")
public class LoginCourierTest extends BaseTest {
    private Courier courier;
    private CourierData courierData;
    private String idCourier = "0";
    @Before
    @Override
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        courier = new Courier();
        courierData = new CourierData();
    }
    @After
    public void setDown(){
        System.out.println("ID of deleted courier " + idCourier);
        courier.deleteCourier(idCourier);
    }
    @Test
    @DisplayName("Test of courier authorization")
    @Story("Courier login")
    @Description("Success courier authorization return id")
    public void checkLoginCourier() {
        courier.createCourierAndGetResponse(courierData);
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        responseLoginCourier
                .then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);

        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of authorization without login")
    @Story("Courier login")
    @Description("Send all fields of courier without login")
    public void checkLoginCourierWithoutLogin() {
        courier.createCourierAndGetResponse(courierData);
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setLogin("");
        courier.getResponseLoginCourier(courierData)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of authorization without password")
    @Story("Courier login")
    @Description("Send all fields of courier without password")
    public void checkLoginCourierWithoutPassword() {
        courier.createCourierAndGetResponse(courierData);
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setPassword("");
        courier.getResponseLoginCourier(courierData)
                .then()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of authorization with wrong or nonexistent login")
    @Story("Courier login")
    @Description("error return with wrong login")
    public void checkLoginCourierWrongLogin() {
        courier.createCourierAndGetResponse(courierData);
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setLogin("UsaTest");
        courier.getResponseLoginCourier(courierData)
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of authorization with wrong password")
    @Story("Courier login")
    @Description("error return with wrong password")
    public void checkLoginCourierWrongPassword() {
        courier.createCourierAndGetResponse(courierData);
        Response responseLoginCourier = courier.getResponseLoginCourier(courierData);
        courierData.setPassword("Qwe");
        courier.getResponseLoginCourier(courierData)
                .then()
                .assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        idCourier = responseLoginCourier
                .then()
                .extract()
                .path("id").toString();
    }
}
