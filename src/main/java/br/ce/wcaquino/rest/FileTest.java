package br.ce.wcaquino.rest;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;

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

    @Test
    public void naoDeveFazerUploadArquivoGrande(){

        given()
                .log().all()
                .multiPart("arquivo", new File("C:\\Workspace/Teste2.txt"))
                .when()
                .post("http://restapi.wcaquino.me/upload")
                .then()
                .log().all()
                .time(lessThan(3000L))
                .statusCode(413);
    }
    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
                .log().all()
                .when()
                .get("http://restapi.wcaquino.me/download")
                .then()
                .log().all()
                .statusCode(200)
        .extract().asByteArray();

        File imagem = new File("C\\Downloads/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

        Assert.assertThat(imagem.length(), lessThan(100000L));
    }
}
