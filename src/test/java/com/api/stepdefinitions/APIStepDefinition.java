package com.api.stepdefinitions;

import static io.restassured.RestAssured.given;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.*;

import com.api.util.Constants;
import com.api.util.Utilities;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


/**
 * This class is used to glue the steps mentioned in the feature file
 * 
 * @author vandana
 *
 */

public class APIStepDefinition {
	private static Logger log = LogManager.getLogger(APIStepDefinition.class.getName());
	private static String requestBody = "{\n" +
			"  \"id\": 1,\n" +
			"   \"name\": \"dogsUpdated\"\n" +
			"  }";

	/**
	 * This method send set the base URI
	 *
	 * @param
	 */
	@Given("^I set the base URI$")
	public void setURI() {
		RestAssured.baseURI = Constants.BASE_URI;

	}

	/**
	 * This method send the api call to find the pet by id
	 * 
	 * @param idNum
	 */

	@Then("^I find the pet by id \"(.*)\"$")
	public void findPetById(String idNum) {
		log.info("Before calling the API for the origin code ... findPetById()");
		Response resp = given()
				.when()
				.get(Constants.RESOURCE_URI_PET + "/{" + Constants.PET_ID + "}",idNum)
				.then().extract().response();
		log.info("Response : " + resp.asString());
		JsonPath js = Utilities.rawToJson(resp);
		Assert.assertEquals(200, resp.statusCode());
		System.out.println("Pet name for provided ID is : "+js.get("tags.name").toString());
	}
	/**
	 * This method send the api call to update the pet name
	 *
	 * @param
	 */
	@Then("^I update the pet name")
	public void updatePetById() {
		log.info("Before calling the API for the origin code ... updatePetById()");
		Response resp = given()
				.header("Content-type", "application/json")
				.body(requestBody)
				.when()
				.put(Constants.RESOURCE_URI_PET)
				.then().extract().response();
		log.info("Response : " + resp.asString());
		JsonPath js = Utilities.rawToJson(resp);
		Assert.assertEquals(200, resp.statusCode());
		Assert.assertEquals("dogsUpdated", resp.jsonPath().getString("name"));
		System.out.println( resp.jsonPath().getString("name"));
	}

	/**
	 * This method send the api call to delete the pet by id
	 * Pre-req: Make sure data with pet id is present
	 * @param idNum
	 */

	@Then("^I delete the pet \"(.*)\"$")
	public void deletePetById(String idNum) {
		log.info("Before calling the API for the origin code ... deletePetById()");
		Response resp = given()
				.header(Constants.API_KEY, Constants.API_VALUE)
				.when()
				.delete(Constants.RESOURCE_URI_PET+"/{"+Constants.PET_ID+"}",idNum)
				.then().extract().response();
		log.info("Response : " + resp.asString());
		Assert.assertEquals(200, resp.statusCode());

	}
}
