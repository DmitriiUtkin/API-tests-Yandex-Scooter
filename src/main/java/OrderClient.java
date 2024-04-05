import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ADD_ORDER_PATH = "/api/v1/orders";
    private static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";


    @Step(value = "Создание заказа")
    public ValidatableResponse addOrder (Order order) {
        return given()
                .spec(getRequestSpec())
                .body(order)
                .when()
                .post(ADD_ORDER_PATH)
                .then()
                .spec(getResponseSpec());
    }

    @Step(value = "Получение списка заказов")
    public ValidatableResponse getOrdersList(int id) {
        return given()
                .spec(getRequestSpec())
                .queryParam("courierId", id)
                .get(ADD_ORDER_PATH)
                .then()
                .spec(getResponseSpec());

    }

    @Step(value = "Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getRequestSpec())
                .body(track)
                .when()
                .put(CANCEL_ORDER_PATH)
                .then()
                .spec(getResponseSpec());
            }

}
