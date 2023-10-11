package br.ce.wcaquino.rest;



import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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

    //Método Hamcrest
    @Test
    public void devoConhecerMatchersHamcrest() {
        assertThat("Maria", Matchers.is("Maria"));
        assertThat(128, Matchers.is(128));
        assertThat(128, Matchers.isA(Integer.class));
        assertThat(128d, Matchers.isA(Double.class));
        assertThat(128d, Matchers.greaterThan(120d));
        assertThat(128d, Matchers.lessThan(130d));

        List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
        assertThat(impares, Matchers.hasSize(5));
        assertThat(impares, Matchers.contains(1, 3, 5, 7, 9));
        assertThat(impares, Matchers.containsInAnyOrder(1, 3, 5, 9, 7));
        assertThat(impares, Matchers.hasItem(1));
        assertThat(impares, Matchers.hasItems(1, 9));

        assertThat("Marco", not("Marcos"), not("Marcus"));
//        assertThat("Marco", anyOf (is("Marcos"), is("mario")));
        //assertThat("Marco", anyOf(containsString("Marco"),containsString("Marco")));
        assertThat("Marcos", anyOf(CoreMatchers.is("Marcos"),CoreMatchers.is("Marcus")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));

    }

        //Validando Body
    @Test
    public void devoValidarBody() {
        given()
                .when()
                .get("https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime")
                .then()
                .statusCode(200)
                .body(containsString("main"))
                .body((Matcher<?>) is(not(nullValue())));

    }
}