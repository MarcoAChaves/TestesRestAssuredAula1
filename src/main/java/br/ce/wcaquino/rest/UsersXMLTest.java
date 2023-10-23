package br.ce.wcaquino.rest;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;

public class UsersXMLTest {
    @Test
    public void devoTrabalharComXml (){
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)
                .body("user.name", is("Ana Julia"))
                .body("user.@id", is("3"))
                .body("user.filhos.name.size()", is(2))
                .body("user.filhos.name[0]", is("Zezinho"))
                .body("user.filhos.name[1]", is("Luizinho"))
                .body("user.filhos.name", hasItem("Zezinho"))
                .body("user.filhos.name", hasItems("Zezinho", "Luizinho"));
    }
}
