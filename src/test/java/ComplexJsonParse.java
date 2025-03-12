import APIPackage.RestAssured.Files;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(Files.CoursePrice());
		
		int count =js.getInt("courses.size()");
		System.out.println(count);
		
		int purchaseAmount =js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		String firstTitle =js.getString("courses[0].title");
		System.out.println(firstTitle);
		
		//print all the titles and their respective Prices
		
		for(int i=0;i<count;i++) {
			String title = js.getString("courses["+i+"].title");
			String price = js.getString("courses["+i+"].price").toString();
			
			System.out.println(title);
			System.out.println(price);
			
		}

		
		
	}

}
