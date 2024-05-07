import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    @Step(value = "Создание курьера")
    public ValidatableResponse add(Courier courier) {
        return given()
                .spec(getRequestSpec())
                .body(courier)
                .post(COURIER_PATH)
                .then()
                .spec(getResponseSpec());
            }

    @Step(value = "Авторизация")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getRequestSpec())
                .body(credentials)
                .post(LOGIN_PATH)
                .then()
                .spec(getResponseSpec());
    }

    @Step(value = "Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getRequestSpec())
                .delete(COURIER_PATH + "/" + id)
                .then()
                .spec(getResponseSpec());
            }

}
