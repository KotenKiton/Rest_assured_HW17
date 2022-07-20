package tests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresinHwTests {

    @Test
    @DisplayName("404test")
    void getStatusCode404() {

        get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("200test")
    void postStatusCode200() {
        String body = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("201test")
    void postStatusCode201() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    @DisplayName("NullValue200")
    void putNullValue200() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .body(body)
                .contentType(JSON)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
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
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}