import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    public static void setBaseURI() {
        RestAssured.baseURI = "https://reqres.in";
    }
}
