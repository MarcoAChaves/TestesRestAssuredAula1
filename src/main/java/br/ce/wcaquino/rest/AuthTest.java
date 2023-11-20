package br.ce.wcaquino.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarSWAPI(){
        given()
                .log().all()
                .when()
                .get("https://swapi.co/api/peaple/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke Skywalker"));
    }
}
