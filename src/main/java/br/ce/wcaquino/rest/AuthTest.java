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

    @Test
    public void deveObterClima(){
        given()
                .log().all()
                .queryParam("q", "Fortaleza, BR")
                .queryParam("appid", "369fb700fa828ff0dfdce8bcea766c74")
                .queryParam("units", "metric")
                .when()
                .get("https://api.oppenweathermap.org/data/2.5/weather")
                .then()
                .log().all()
                .statusCode(200)
        .body("name", Matchers.is("Fortaleza"))
        .body("coord.lon", is(-38.52f))
        .body("main.temp", greaterThan(25f));
    }
}


