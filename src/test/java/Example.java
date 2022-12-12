import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Example {
    @Test
    public void test() {
        given().
                baseUri("test").
                header("test", "test").
                param("test", "test").
                when().
                then();
    }

    @Test
    public void statusCode() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void test1() {
        String season = "2017";
        given().
                pathParam("raceSeason", season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                log().all().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitName[0]", equalTo("Albert Park Grand Prix Circuit"));
    }

    @Test
    void test2() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits", not(empty()));
    }

    @Test
    public void test3() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitName[0]", equalTo("Albert Park Grand Prix Circuit"),
                        "MRData.CircuitTable.Circuits.circuitId[0]", is(equalTo("albert_park")),
                        "MRData.CircuitTable.Circuits.circuitName[1]", equalTo("Circuit of the Americas"),
                        "MRData.CircuitTable.Circuits.circuitId.size()", equalTo(20));
    }

    @Test
    public void extractResponse() {
        Response response = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
    }

    @Test
    public void extractSingleValueFromResponse() {
        Response response = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

        JsonPath jsonPath = new JsonPath(response.asString());
        System.out.println(jsonPath.getString("MRData.CircuitTable.Circuits.circuitName[0]"));
    }

    @Test
    public void extractSingleValueFromResponse1() {
        String name = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("MRData.CircuitTable.Circuits.circuitName[0]");
        System.out.println(name);
    }

    @Test
    public void hamcrestAndTestNGAssertsToExtractedValue() {
        String name = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("MRData.CircuitTable.Circuits.circuitName[0]");
        assertThat(name, equalTo("Albert Park Grand Prix Circuit")); //hamcrest
        Assert.assertEquals(name, "Albert Park Grand Prix Circuit"); //testNG
    }

    @Test
    void hamcrest() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat()
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]", hasItem("Circuit of the Americas"))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]", not(hasItem("Circuit of the Americas1")))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]",
                        hasItems("Albert Park Grand Prix Circuit", "Bahrain International Circuit"))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]",
                        contains("Albert Park Grand Prix Circuit", "Circuit of the Americas", "Bahrain International Circuit"))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]",
                        containsInAnyOrder("Circuit of the Americas", "Albert Park Grand Prix Circuit",  "Bahrain International Circuit"))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]", is(not(empty())))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]", is(not(emptyArray())))
                .body("MRData.CircuitTable.Circuits.circuitName[0,1,2]", hasSize(3))
                .body("MRData.CircuitTable.Circuits.url[0,1,2]", everyItem(startsWith("http")))
                .body("MRData.CircuitTable.Circuits[0]", hasKey("circuitName"))
                .body("MRData.CircuitTable.Circuits[0]", hasValue("Albert Park Grand Prix Circuit"))
                .body("MRData.CircuitTable.Circuits[0]", hasEntry("circuitName", "Albert Park Grand Prix Circuit"))
                .body("MRData.CircuitTable.Circuits[0]", not(equalTo(Collections.EMPTY_MAP)))
        ;
    }
    @Test
    void allOfTest(){
        given().
                log().all().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                body("MRData.CircuitTable.Circuits.circuitName[0]",
                        allOf(
                                startsWith("Albert Park"),
                                containsString("Park")));
    }
    @Test
    void anyOfTest(){
        given().
                log().all().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                body("MRData.CircuitTable.Circuits.circuitName[0,1]",
                        anyOf(
                                hasItems("Albert Park Grand Prix Circuit",
                                        "Circuit of the Americas")
                                ,hasItems("123","888")));

    }
    @DataProvider(name="seasonsAndNumberOfRaces")
    public Object[][] createTestDataRecords() {
        return new Object[][] {
                {"2017"},
                {"2016"},
                {"1966"}
        };
    }
    @Test(dataProvider="seasonsAndNumberOfRaces")
    public void test_NumberOfCircuits_ShouldBe_DataDriven(String season) {
        given().
                pathParam("raceSeason",season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                log().body();
    }
}

