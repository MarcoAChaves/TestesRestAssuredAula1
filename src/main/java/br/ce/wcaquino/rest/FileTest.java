package br.ce.wcaquino.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.registerParser;
import static org.hamcrest.Matchers.*;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo(){

        given()
        .log().all()
        .when()
                .post("http://estapi.wcaquino.me/upload")
        .then()
        .log().all()
        .statusCode(404);
    }

    @Test
    public void deveFazerUploadArquivo(){

        given()
                .log().all()
                .multiPart("arquivo", new File("C:\\Workspace/Teste.txt"))
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .statusCode(200)
        .body("name", is("Teste.txt"));
    }
}
