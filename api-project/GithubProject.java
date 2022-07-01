package liveProject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class GithubProject {
	RequestSpecification reqSpec;
	String sshKey;
	int id;

	@BeforeClass
	public void setUp() {
		reqSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				.setBaseUri("https://api.github.com").build();
	}

	@Test(priority = 1)
	public void addSSH() {
		Response response = given().spec(reqSpec).body("{\r\n" + "   \"title\":\"TestAPIKey\",\r\n"
				+ "   \"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCQP/IxBsBv/+AMskv/ETxTsbs5AgAmHXtsCFvzvYiwu406oVR3xxONn3zXAvZWM/1+wpFvoT3ZngyDBHZorEyfqLxKzo2CReCHE9xshOTJortFDo9nQ2Ca4N4iWeJ7qYaPTUHmlwTcU9Fe6ql0Mnf7D9CsCPdLNJ+iXhfufaelIrlbXwYZbZXWwkMUFFC8gqlbBQm+6mlFbx/TmLr4kT5QzpkY9FExKgGchUMWynRryoBPB9W0Brfju9MYkB/NAm0tVkhdBTyHPaX6rUTkTkjJ5VCf7A57yrAle0MbWvYluFl8O9XJKrK7Fyg9Td9tS4oMNqVoHvji7eT10aZlJgwv\"\r\n"
				+ "}").when().post("/user/keys");

		System.out.println("Response for post: " + response.asPrettyString());

		response.then().statusCode(201);
		id = response.then().extract().body().path("id");
	}

	@Test(priority = 2)
	public void getSSH() {
		Response response = given().spec(reqSpec).pathParam("keyId", id).when().get("/user/keys/{keyId}");
		System.out.println("Response for getSSH: "+response.prettyPrint());
		response.then().statusCode(200);
	}

	@Test(priority = 3)
	public void deleteSSH() {
		Response response = given().spec(reqSpec).pathParam("keyId", id).when().delete("/user/keys/{keyId}");
		System.out.println("Response for getSSH: "+response.prettyPrint());
		response.then().statusCode(204);
	}

}
