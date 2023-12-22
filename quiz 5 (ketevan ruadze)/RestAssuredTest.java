import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestAssuredTest {

    @DataProvider(name = "circuitsData")
    public Object[][] getCircuitsData() {
        return new Object[][]{
                {1, "Country1"},
                {5, "Country2"}
        };
    }

    @Test(dataProvider = "circuitsData")
    public void testCircuitCountry(int circuitIndex, String expectedCountry) {
        Response response = RestAssured.get("http://ergast.com/api/f1/2017/circuits.json");

        String circuitId = response.jsonPath().getString("MRData.CircuitTable.Circuits[" + (circuitIndex - 1) + "].circuitId");

        Response circuitResponse = RestAssured.get("http://ergast.com/api/f1/circuits/" + circuitId + ".json");

        String actualCountry = circuitResponse.jsonPath().getString("MRData.CircuitTable.Circuits[0].Location.locality");
        assert actualCountry.equals(expectedCountry) : "Country mismatch for circuit " + circuitIndex;
    }
}
