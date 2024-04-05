import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AddCourierTest {

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
    @DisplayName("Курьера можно создать, запрос возвращает правильный код ответа и ok: true")
    public void courierCanBeAdded () {
        ValidatableResponse response = courierClient.add(courier);
        int statusCode = response.extract().statusCode();
        boolean ok = response.extract().path("ok");
        assertEquals(statusCode, 201);
        assertTrue(ok);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        int loginStatusCode = loginResponse.extract().statusCode();
        assertNotNull(id);
        assertEquals(loginStatusCode, 200);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void courierCannotBeDuplicated () {
        ValidatableResponse firstResponse = courierClient.add(courier);
        assertEquals(201, firstResponse.extract().statusCode());
        assertTrue(firstResponse.extract().path("ok"));

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

        ValidatableResponse secondResponse = courierClient.add(courier);
        assertEquals(409, secondResponse.extract().statusCode());
        assertEquals("Этот логин уже используется", secondResponse.extract().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    public void courierCannotBeAddedWithoutLogin () {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.add(courier);

        assertEquals(400, response.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    public void courierCannotBeAddedWithoutPassword () {
        courier.setPassword(null);
        ValidatableResponse response = courierClient.add(courier);

        assertEquals(400, response.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без имени")
    public void courierCannotBeAddedWithoutFirstName () {
        courier.setFirstName(null);
        ValidatableResponse response = courierClient.add(courier);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

        assertEquals(400, response.extract().statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void courierCannotBeAddedWithTheSameLogin () {
        courierClient.add(courier);
        Courier courierWithTheSameLogin = new Courier(courier.getLogin(), "W24yp07", "Freddy");
        ValidatableResponse response = courierClient.add(courierWithTheSameLogin);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

        assertEquals(409, response.extract().statusCode());
        assertEquals("Этот логин уже используется", response.extract().path("message"));
    }

    }

