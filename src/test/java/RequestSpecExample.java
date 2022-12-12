import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecExample {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void before() {
        requestSpecification =  given().
                baseUri("http://ergast.com");
    }
    @Test
    public void statusCode() {
//        given().
//                baseUri("http://ergast.com").
//                when().
//                get("/api/f1/2017/circuits.json").
//                then().
//                assertThat().
//                statusCode(200);

        RequestSpecification requestSpecification =  given().
                baseUri("http://ergast.com");

        given().spec(requestSpecification).
                baseUri("http://ergast.com").
                when().
                get("/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200);
    }
    @Test
    public void statusCode1() {
        given().spec(requestSpecification).
                baseUri("http://ergast.com").
                when().
                get("/api/f1/2019/circuits.json").
                then().
                assertThat().
                statusCode(200);
    }
}
