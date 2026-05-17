package com.soumya.urlshortner.tests;

import com.soumya.urlshortner.base.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Listeners(
        com.soumya.urlshortner.listeners.TestListener.class
)
public class ShortenUrlApiTest extends BaseTest {

    @Test
    public void shouldCreateShortUrl(){

        String requestBody = """
                {
                    "url":"https://google.com",
                    "expiryMinutes":10
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/shorten")
        .then()
                .statusCode(200)
                .body("shortUrl", notNullValue());
    }
}