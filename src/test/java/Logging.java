import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Logging {
    @Test
    public void statusCode() {
        given().
                baseUri("http://ergast.com").
                log().all().
                when().
                get("/api/f1/2017/circuits.json").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void testLogParams() {
        String originalText = "test";
        given().
                param("text", originalText).
                log().params().
                //log().all().
                        when().
                get("http://md5.jsontest.com").
                then().
                log().body().
                //log().all().
                        assertThat().
                statusCode(200);
    }
    @Test
    public static void getResponseStatus(){
        given().queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .log().parameters()
                .when()
                .get("http://demo.guru99.com/V4/sinkministatement.php")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void logOnlyIfError() {
        String originalText = "test";
        given().
                param("text", originalText).
                when().
                post("http://md5.jsontest.com").
                then().
                log().ifError().
                assertThat().
                statusCode(200);
    }
    @Test
    public void logOnlyIfValidationFails() {
        given().
                baseUri("http://ergast.com").
                when().
                get("/api/f1/2017/circuits.json").
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(400);
    }

}