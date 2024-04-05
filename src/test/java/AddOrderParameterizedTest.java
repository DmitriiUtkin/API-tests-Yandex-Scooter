import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class AddOrderParametrizedTest {
        private Order order;
        private OrderClient orderClient;
        private String[] color;
        private int track;



    public AddOrderParametrizedTest(String[] color) {
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

    @After
    public void deleteTestData () {
        orderClient.cancelOrder(track);
    }


    @Test
    public void creationOfOrder(){
            order.setColor(color);
            ValidatableResponse response = orderClient.addOrder(order);
            response.statusCode(201).body("track", notNullValue());
            track = response.extract().path("track");
    }
}
