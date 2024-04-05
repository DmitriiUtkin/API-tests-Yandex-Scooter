import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)

public class AddOrderParameterizedTest {

    private Order order;
    private OrderClient orderClient;
    private CourierClient courierClient;
    private String[] color;
    private int track;
    private int id;

    public AddOrderParameterizedTest(String[] color) {
        this.order = OrderGenerator.base();
        this.color = color;
        this.orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}}
        );
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        Courier courier = CourierGenerator.base();
        courierClient = new CourierClient();
        ValidatableResponse response = new CourierClient().add(courier);
        assertEquals(201, response.extract().statusCode());
        assertTrue(response.extract().path("ok"));

        ValidatableResponse loginResponse = new CourierClient().login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        assertEquals(200, loginResponse.extract().statusCode());
    }

    @After
    public void deleteTestData () {
        courierClient.delete(id);
        orderClient.cancelOrder(track);
        ValidatableResponse cancelResponse = orderClient.cancelOrder(track);
        cancelResponse.statusCode(200).body("ok", equalTo(true));
    }

    @Test
    @DisplayName("При создании заказа можно указать один из цветов — BLACK или GREY, оба цвета, не указывать цвет, тело ответа содержит track.")
    public void creationOfOrder(){
            order.setColor(color);
            ValidatableResponse response = orderClient.addOrder(order);
            response.statusCode(201).body("track", notNullValue());
            track = response.extract().path("track");
    }
}
