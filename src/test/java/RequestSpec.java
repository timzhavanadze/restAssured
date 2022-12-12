import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RequestSpec {
    Response response;
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI = "http://ergast.com/api/f1/2017/circuits.json";
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.request(Method.GET);
    }

    @Test
    public void GetWeatherDetails()
    {
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
    }

    @Test
    public void validateStatusCodes(){
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Correct status code returned");
    }
}

