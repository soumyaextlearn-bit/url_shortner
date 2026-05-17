package com.soumya.urlshortner.tests;

import com.soumya.urlshortner.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Listeners(
        com.soumya.urlshortner.listeners.TestListener.class
)
public class RateLimitApiTest extends BaseTest {

    @Test
    public void shouldReturn429WhenRateLimitExceeded(){

        String requestBody = """
                {
                    "url":"https://google.com",
                    "expiryMinutes":10
                }
                """;

        Response response = null;

        for(int i = 0; i < 6; i++){

            response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
            .when()
                    .post("/shorten");
        }

        assertEquals(
                response.statusCode(),
                429
        );
    }
}