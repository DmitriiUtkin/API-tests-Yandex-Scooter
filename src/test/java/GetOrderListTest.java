import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetOrderListTest {
    private Order order;
    private OrderClient orderClient;
    private CourierClient courierClient;
    private String[] color;
    private int track;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        orderClient = new OrderClient();
        Courier courier = CourierGenerator.base();
        courierClient = new CourierClient();
        ValidatableResponse response = new CourierClient().add(courier);
        assertEquals(201, response.extract().statusCode());
        assertTrue(response.extract().path("ok"));

        ValidatableResponse loginResponse = new CourierClient().login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
    }

    @After
    public void deleteTestData () {
        courierClient.delete(id);

        orderClient.cancelOrder(track);
    }

    @Test
    @DisplayName("В теле ответа возвращается список заказов")
    public void getOrdersListTest() {
        orderClient.getOrdersList(id)
                .assertThat()
                .statusCode(200)
                .and()
                .body(not(empty()));
    }

}
