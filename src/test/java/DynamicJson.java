import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import APIPackage.RestAssured.Files;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicJson {

	@Test(dataProvider= "BooksParameter")
    public void addBook(String isbn, String aisle) {
		//Add Book
	    RestAssured.baseURI ="http://216.10.245.166";    
	     //Add Place
	     String response= given().log().all().header("Content-Type","application/json").
	     body(Files.addBook(isbn,aisle)).
	     when().post("Library/Addbook.php").
	     then().log().all().assertThat().statusCode(200).extract().response().asString();
	     
	     JsonPath js = new JsonPath(response);
	     String bookId= js.getString("ID");
	     System.out.println("Book ID: " + bookId);
	     
	  // Delete Book
	        String deletePayload = "{\"ID\" : \"" + bookId + "\"}";
	        System.out.println("Delete Payload: " + deletePayload);

	        Response deleteResponse = given().log().all()
	                .header("Content-Type", "application/json")
	                .body(deletePayload)
	                .when().delete("Library/DeleteBook.php")
	                .then().log().all()
	                .extract().response();

	        // Print status code and response body for the delete request
	        int statusCode = deleteResponse.getStatusCode();
	        String responseBody = deleteResponse.getBody().asString();
	        System.out.println("Delete Status Code: " + statusCode);
	        System.out.println("Delete Response Body: " + responseBody);

	        // Assert the response
	        deleteResponse.then().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
	    	     
	    	     
	  } 
	
	@DataProvider(name ="BooksParameter")
	  public Object[][] getData(){
		  return new Object[][] {{"AAA","111"},{"BBB","222"}};
		  
		  }
	
	
	
	
	
	

}
