package APIPackage.RestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class App 
{
    public static void main( String[] args )
    {
       
     RestAssured.baseURI ="https://rahulshettyacademy.com";    
     //Add Place
     String response= given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
     body(Files.AddPlace()).
     when().post("maps/api/place/add/json").
     then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).
     header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
     //equalTo method comes from hamcrest.matcherpackage
     
     //To extract the data from the response we have to use the JsonPath class
     JsonPath js = new JsonPath(response);
     String placeId= js.getString("place_id");
     
     System.out.println("PlaceID is " +placeId);
     
     //Update Address
     String updatedAddress= "70 winter walk, USA";
     given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
     body("{\r\n"
     		+ "\"place_id\":\""+placeId+"\",\r\n"
     		+ "\"address\":\""+updatedAddress+"\",\r\n"
     		+ "\"key\":\"qaclick123\"\r\n"
     		+ "}\r\n"
     		+ "").
     when().put("maps/api/place/update/json").
     then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
     
     
     //Get Place with Updated Address
     String response1= given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).
     when().get("maps/api/place/get/json").
     then().log().all().assertThat().statusCode(200).extract().response().asString();
     
     JsonPath js1 = new JsonPath(response1);
     String address= js1.getString("address");
     
     if(address.equalsIgnoreCase(updatedAddress)) {
    	 System.out.println("Address Updated Successfully");
    	 
     }
     
     
     
    }
}
