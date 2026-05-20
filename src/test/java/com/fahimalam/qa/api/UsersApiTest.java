package com.fahimalam.qa.api;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Rest-Assured smoke tests against the JSONPlaceholder sandbox.
 * Demonstrates GET / POST chaining, schema-light assertions, and
 * TestNG parallel-safe API coverage that runs alongside the UI suite.
 */
public class UsersApiTest {

    @BeforeClass
    public void setUp() {
        io.restassured.RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(groups = "api")
    public void listUsersReturnsTenUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("size()", equalTo(10))
                .body("[0].email", containsString("@"))
                .body("[0].address.city", not(emptyString()));
    }

    @Test(groups = "api")
    public void getUserByIdReturnsExpectedRecord() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("username", equalTo("Bret"))
                .body("company.name", not(emptyString()));
    }

    @Test(groups = "api")
    public void createPostEchoesPayloadWithGeneratedId() {
        String payload = """
                { "title": "qa portfolio", "body": "rest-assured smoke", "userId": 1 }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("qa portfolio"))
                .body("userId", equalTo(1))
                .body("id", greaterThan(0));
    }

    @Test(groups = "api")
    public void unknownUserReturns404() {
        given()
                .when()
                .get("/users/9999")
                .then()
                .statusCode(404);
    }
}
