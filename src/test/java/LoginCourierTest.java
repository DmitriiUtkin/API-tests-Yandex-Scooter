import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    public void addTestData() {
        courierClient = new CourierClient();
        courier = CourierGenerator.base();
    }

    @After
    public void deleteTestData() {
        if (id != 0) {
        courierClient.delete(id);
    }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void courierCanLogin() {
        courierClient.add(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));
        assertEquals(200, response.extract().statusCode());
        assertNotNull(response.extract().path("id"));
    }

    @Test
    @DisplayName("Курьер не может авторизоваться без логина")
    public void courierCannotLoginWithoutLogin() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin(null);
        ValidatableResponse response = courierClient.login(courierCredentials);
        assertEquals(400, response.extract().statusCode());
        assertEquals("Недостаточно данных для входа", response.extract().path("message"));
    }

    @Test
    @DisplayName("Курьер не может авторизоваться без пароля")
    public void courierCannotLoginWithoutPassword() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setPassword(null);
        ValidatableResponse response = courierClient.login(courierCredentials);
        assertEquals(400, response.extract().statusCode());
        assertEquals("Недостаточно данных для входа", response.extract().path("message"));
    }

    @Test
    @DisplayName("Курьер не может авторизоваться с несуществующим логином")
    public void courierCannotLoginWithWrongLogin() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin("13579");
        ValidatableResponse response = courierClient.login(courierCredentials);
        assertEquals(404, response.extract().statusCode());
        assertEquals("Учетная запись не найдена", response.extract().path("message"));
    }

    @Test
    @DisplayName("Курьер не может авторизоваться c неверным паролем")
    public void courierCannotLoginWithWrongPassword() {
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setPassword("24680");
        ValidatableResponse response = courierClient.login(courierCredentials);
        assertEquals(404, response.extract().statusCode());
        assertEquals("Учетная запись не найдена", response.extract().path("message"));
    }

}



