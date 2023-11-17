package br.ce.wcaquino.rest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class EnvioDadosTest {

    @Test
    public void deveEnviarValorViaQuery(){
    given()
            .log().all()
            .when()
                .get("https://restapi.wcaquino.me/v2/users?format=xml")
            .then()
        .log().all()
        .statusCode(200)
    .contentType(ContentType.XML);
    }
}
