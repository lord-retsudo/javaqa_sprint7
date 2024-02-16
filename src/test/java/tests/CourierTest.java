package tests;

import actions.Courier;
import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;


@DisplayName("Tests of creating courier")
public class CourierTest extends BaseTest {
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
    public void deleteCourier(){
        System.out.println("ID of deleted courier " + idCourier);
        courier.deleteCourier(idCourier);
    }


    @Test
    @DisplayName("Test of creating courier")
    @Story ("Creating courier")
    @Description ("Courier creating possibility")
    public void checkCreateCourier() {
        courier.createCourierAndGetResponse(courierData)
                .then()
                .assertThat()
                .body("ok",equalTo(true))
                .and()
                .statusCode(SC_CREATED);

        idCourier = courier.getResponseLoginCourier(courierData)
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of creating two same couriers")
    @Story ("Creating courier")
    @Description ("No creating two same couriers")
    public void checkCreateSameCourier() {
        courier.createCourierAndGetResponse(courierData);
        courier.createCourierAndGetResponse(courierData)
                .then()
                .assertThat()
                .body("message", notNullValue())
                .and()
                .statusCode(SC_CONFLICT);
        idCourier = courier.getResponseLoginCourier(courierData)
                .then()
                .extract()
                .path("id").toString();
    }

    @Test
    @DisplayName("Test of creating courier without login")
    @Story ("Creating courier")
    @Description ("send all required fields for creating courier without login")
    public void checkRequiredFieldsCreateCourierWithoutLogin() {
    courierData.setLogin("");
    courier.createCourierAndGetResponse(courierData)
            .then().body("message", notNullValue())
            .and()
            .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Test of creating courier without password")
    @Story ("Creating courier")
    @Description ("send all required fields for creating courier without password")
    public void checkRequiredFieldsCreateCourierWithoutPassword() {
    courierData.setPassword("");
    courier.createCourierAndGetResponse(courierData)
            .then().body("message", notNullValue())
            .and()
            .statusCode(SC_BAD_REQUEST);
    }
}
