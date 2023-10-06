package br.ce.wcaquino.rest;


import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class OlaMundoTest {

    @Test
    public void testOlaMundo(){
        Response response = RestAssured.request (Method.GET, "https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime");
        System.out.println(response.getBody().asString());
        System.out.println("Status code: " + response.statusCode());

    }

    //Melhorando o primeiro código

    @Test
    public void devoConhecerOutrasFormasRestAssured() {
        Response response = RestAssured.request (Method.GET, "https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

        RestAssured.get("https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime").then().statusCode(200);
        System.out.println("Status code: " + response.statusCode());

        //Usando um modo fluente
        given()
        .when()
                .get("https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime")
        .then()
                .statusCode(200);

    }
}