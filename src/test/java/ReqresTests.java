import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sun.webkit.perf.WCGraphicsPerfLogger.log;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class ReqresTests extends TestBase {

    @Test
    @DisplayName("Get the single user test")
    public void getSingleUser() {
        String userFirstName = "Janet";
        String userLastName = "Weaver";
        String userEmail =  "janet.weaver@reqres.in";
        int userId = 2;

        given()
                .get("/api/users/2")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.id", equalTo(userId))
                .body("data.email", equalTo(userEmail))
                .body("data.first_name", equalTo(userFirstName))
                .body("data.last_name", equalTo(userLastName));
    }

    @Test
    @DisplayName("Successful login test")
    public void userSuccessfulLogin() {
        String body = "{" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("Unsuccessful login test")
    public void userUnsuccessfulLogin() {
        String body = "{ \"email\": \"sydney@fife\" }";
        String error = "Missing email or username";

        given()
                .log().uri()
                .log().body()
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is(error));

    }

    @Test
    @DisplayName("User delete test")
    public void userDelete() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }

    @Test
    @DisplayName("Edite user data")
    public void userDataEdite() {

        String body = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        String userName = "morpheus";
        String userJob = "zion resident";

        given()
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .log().uri()
                .log().body()
                .patch("/api/users/2")
                .then()
                .log().status()
                .statusCode(200)
                .body("name", is(userName))
                .body("job", is(userJob));
    }

}



