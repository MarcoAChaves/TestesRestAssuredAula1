package br.ce.wcaquino.rest;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void naoDeveacessarSemSenha(){
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void deveFazerAutenticacaoBasica(){
        given()
                .log().all()
                .when()
                .get("https://admin:senha@restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
        .body("status", is("logado"));
    }

    @Test
    public void deveFazerAutenticacaoBasica2(){
        given()
                .log().all()
                .auth().basic("admin", "senha")
                .when()
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void deveFazerAutenticacaoBasicaChallange(){
        given()
                .log().all()
                .auth().preemptive().basic("admin", "senha")
                .when()
                .get("https://restapi.wcaquino.me/basicauth2")
                .then()
                .log().all()
                .statusCode(200)
                .body("status", is("logado"));
    }

    @Test
    public void naoDeveAcessarSemSenha(){
        given()
                .log().all()
                .when()
                .get("https://restapi.wcaquino.me/basicauth")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    public void deveFazerAutenticacaoComTokenJWT(){

        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "wagner@aquino");
        login.put("senha", "123456");


//Login na API
// Receber o token
        String token = given()
                .log().all()
                .body(login)
                .contentType(ContentType.JSON)
                .when()
                .post("https://barrigarest.wcaquino.me/signin")
                .then()
                .log().all()
                .statusCode(200)
        .extract().path("token");

        //Obter as contas
        given()
                .log().all()
                //enviando token
                .header("Authorization", "JWT", token)
                .when()
                .get("https://barrigarest.wcaquino.me/contas")
                .then().log().all()
        .statusCode(200)
        .body("name", hasItem("Conta de teste"));
    }

    @Test
    public void deveAcessarAplicacaoWeb(){
        //login
        String cookie = given()
                .log().all()
                .formParam("email", "wagner@aquino")
                .formParam("senha", "123456")
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .when()
                .post("http://seubarriga.wcaquino.me/logar")
                .then()
                .log().all()
                .statusCode(200)
        .extract().header("set-cookie");

        cookie = cookie.split("=")[1].split(";")[0];
        System.out.println("Cookie: " + cookie);

        //Obter contas
        String body = given()
                .log().all()
                .cookie("connect.sid", cookie)
                .when()
                .get("http://seubarriga.wcaquino.me/contas")
                .then()
                .log().all()
                .statusCode(200)
        .body("html.body.table.tbody.tr[0].td[0]", is("Conta 2533244560436800"))
        .extract().body().asString();

        System.out.println("--------------------------------");
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, body);
        System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
    }

}


