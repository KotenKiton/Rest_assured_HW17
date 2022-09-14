package tests;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresinHwTests  {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    @DisplayName("getStatusCode404")
    void getUserNotFound() {

        get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("200test")
    void postRegisterUserSuccess() {
        String body = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .log().all() // Раскроет всё тело запроса
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("201test")
    void postNameAndJobValues() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .log().all() // Раскроет всё тело запроса
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("NullValue200")
    void putUpdatedAtNotNull() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .log().all() // Раскроет всё тело запроса
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all() // Раскроет всё тело ответа
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("MissingPassword400")
    void postMissingPassword() {
        String body = "{ \"email\": \"sydney@fife\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .log().all() // Раскроет всё тело запроса
                .post("https://reqres.in/api/register")
                .then()
                .log().all() // Раскроет всё тело ответа
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
