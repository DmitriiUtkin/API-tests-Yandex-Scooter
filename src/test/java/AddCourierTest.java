import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class CourierAddTest {

//    private CourierClient courierClient;
//    private Courier courier;
//    private int id;
//
//    @Before
//    public void setUp() {
//        RestAssured.baseURI("https://qa-scooter.praktikum-services.ru/");
//    }
//    public void addTestData() {
//        courierClient = new CourierClient();
//        courier = CourierGenerator.base();
////        courier.setPassword(null);
//    }
//
//    @After
//    public void deleteTestData() {
//        courierClient.delete(id);
//    }
//
//    @Test
//    public void courierCanBeAdded () {
//
//        Response response = given()
//                .header("Content-type", "application/json")
//                .and()
//                .body()
//                .when()
//                .post("/api/v1/courier")
//                .then().statusCode(201);




//        ValidatableResponse response = courierClient.add(courier);
//        int statusCode = response.extract().statusCode();
//        boolean ok = response.extract().path("ok");
//        assertEquals(statusCode, 201);
//        assertTrue(ok);
//
//        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
//        id = loginResponse.extract().path("id");
//        int loginStatusCode = loginResponse.extract().statusCode();
//        assertNotNull(id);
//        assertEquals(loginStatusCode, 200);

    }
}
