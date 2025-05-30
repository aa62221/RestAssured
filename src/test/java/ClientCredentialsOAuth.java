import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;


public class ClientCredentialsOAuth {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Response response = given()
				         . formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				          .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				          .formParam("grant_type", "client_credentials")
				          .formParam("scope", "trust")
				          .when().log().all()
				          .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token");
		
		System.out.println("Response time: " + response.time() + " ms");
		//You can also assert the response time using below code
		//LessThan() comes from hamcrest library
		response.then().assertThat().time(lessThan(2000L));
	     
		 String res = response.asString();
		 JsonPath js = new JsonPath(res);
	     String accessToken= js.getString("access_token");
	     
	     String response2 = given()
		         . queryParam("access_token", accessToken)
		         .when().log().all()
		         .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		          
		         
		System.out.println(response2);
		
		
		

	}

}
