package testCases;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_VedioGameAPI 
{
	@Test(priority = 1)
	public void test_getAllVideoGames()
	{
		given()
		
		.when()
			.get("http://localhost:8080/app/videogames")
			
		.then()
			.statusCode(200);
		
	}
	
	@Test(priority = 2)
	public void test_addNewVideoGame()
	{
		HashMap data = new HashMap();
		
		data.put("id", "100");
		data.put("name","Spider-Man");
		data.put("releaseDate","2019-12-06T17:42:07.162Z");
		data.put("reviewScore","5");
		data.put("category","Adventure");
		data.put("rating","Universal");
		
		Response res = 
		
		given()
			.contentType("application/json")
			.body(data)
			
		.when()
			.post("http://localhost:8080/app/videogames")
			
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}
	
	@Test(priority = 3)
	public void test_getVideoGame()
	{
		given()
		
		.when()
			.get("http://localhost:8080/app/videogames/100")
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("VideoGame.name",equalTo("Spider-Man"));
	}
	
	@Test(priority = 4)
	public void test_updateVideoGame()
	{
		HashMap data = new HashMap();
		
		data.put("id", "100");
		data.put("name","Ant-Man");
		data.put("releaseDate","2019-12-06T17:42:07.162Z");
		data.put("reviewScore","4");
		data.put("category","Adventure");
		data.put("rating","Universal");
		
		given()
			.contentType("application/json")
			.body(data)
			
		.when()
			.put("http://localhost:8080/app/videogames/100")
			
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("VideoGame.name",equalTo("Ant-Man"));
			
	}
	
	@Test(priority = 5)
	public void test_deleteVideoGame() throws InterruptedException
	{
		Response res=
				given()
				.when()
					.delete("http://localhost:8080/app/videogames/100")
				.then()
					.statusCode(200)
					.log().body()
					.extract().response();
				
				Thread.sleep(3000);
				String jsonString=res.asString();
				
				Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
		
		
	}

}
