package br.ce.wcaquino.rest;

import io.restassured.matcher.RestAssuredMatchers;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import static io.restassured.RestAssured.given;

public class SchemaTest {

    @Test
    public void DeveValidarSchemaTest (){
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/userXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }

    @Test(expected= SAXParseException.class)
    public void naoDeveValidarSchemaTest (){
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/userXML")
                .then()
                .log().all()
                .statusCode(200)
                .body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
    }
}
