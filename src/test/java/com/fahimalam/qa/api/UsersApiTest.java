package com.fahimalam.qa.api;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Rest-Assured smoke tests against the public reqres.in sandbox.
 * Demonstrates GET/POST chaining, schema-light assertions, and
 * TestNG parallel-safe API coverage that runs alongside the UI suite.
 */
public class UsersApiTest {

    @BeforeClass
    public void setUp() {
        io.restassured.RestAssured.baseURI = "https://reqres.in";
    }

    @Test(groups = "api")
    public void listUsersReturnsPageMetadata() {
        given()
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("page", equalTo(2))
                .body("data", hasSize(greaterThan(0)))
                .body("data[0].email", containsString("@"));
    }

    @Test(groups = "api")
    public void createUserReturnsIdentifier() {
        String payload = """
                { "name": "fahim", "job": "sdet" }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("fahim"))
                .body("job", equalTo("sdet"))
                .body("id", not(emptyString()))
                .body("createdAt", not(emptyString()));
    }

    @Test(groups = "api")
    public void unknownUserReturns404() {
        given()
                .when()
                .get("/api/users/9999")
                .then()
                .statusCode(404);
    }
}
