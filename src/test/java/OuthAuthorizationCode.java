import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class OuthAuthorizationCode {

	public static void main(String[] args) {
		
		
//		#######  Get Access Code by hitting the url on the browser using Selenium    #######
		
//		 WebDriver driver = new ChromeDriver();
//		 driver.get("https://accounts.google.com/o/oauth2/v2/auth?auth_url=https://accounts.google.com/o/oauth2/v2/auth&scope=https://www.googleapis.com/auth/userinfo.email&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=Test");	 
//		 driver.findElement(By.xpath("//input[@type='email']")).sendKeys("akshay220glamourous@gmail.com");
//		 driver.findElement(By.xpath("//button//span[contains(.,'Next')]")).click();
//		 //Its not working somehow, after clicking next it is throwing error url is not safe
//		 String url=driver.getCurrentUrl();
		
		//Google has stopped the Gmail login automation
		 String url="https://rahulshettyacademy.com/getCourse.php?state=Test&code=4%2F0AQSTgQErfbAeVPjC0pQScwdyNl0gliKeEYcKG0SxPZr4qpoQtiIEhde8gIKnNA4IoadHdQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		 String partialCode=url.split("code=")[1];
		 String code= partialCode.split("&scope")[0];
		 System.out.println("Code is " +  code);
		 
//		#######Get Access Token using Access Code##############
		 
		//we have to add Encoding Enabled as false because Rest Assured will by default convert special characters from Code string
        // into numeric value
		 
		String response = given().urlEncodingEnabled(false)
				  .queryParam("code", code)
		         . queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		          .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		          .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		          .queryParam("grant_type", "authorization_code")
		          .when().log().all()
		          .post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(response);
		String accessToken= js.getString("access_token");
		
		System.out.println("Access Token is --"+ accessToken);
		
//		#######Get details using Access token#######
		String response1 = given()
				  .queryParam("access_token", accessToken)
				  .when().log().all()
				  .get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response1);
		
		


	}

}
