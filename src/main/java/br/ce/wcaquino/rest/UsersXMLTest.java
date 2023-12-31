package br.ce.wcaquino.rest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UsersXMLTest {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;


    @BeforeClass
    public static void setup(){
        baseURI = "https://restapi.wcaquino.me";
        port = 80;
        basePath = "";

    }
    @Test
    public void devoTrabalharComXml() {


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

    @Test
    public void devoTrabalharComXmlNoRaiz() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML/3")
                .then()
                .statusCode(200)
                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))
                .body("name.size()", is(1))
                //.detachRootPath("user.filhos")
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))
                .appendRootPath("filhos")
                .body("name", hasItem("Zezinho"))
                .body("name", hasItems("Zezinho", "Luizinho"));
    }

    @Test
    public void devoFazerPesquisasAvancadasComXml() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body("users.user.size()", is(3))
                .body("users.user.findAll{it.age.toInteger() <=25}.size()", is(2))
                .body("users.user.@id", hasItems("1", "2", "3"))
                .body("users.user.find{it.age == 25}.name", is("Maria Joaquina"))
                .body("users.user.findAll{it.name.toString().contains('n')}.name.", hasItems("Maria Joaquina", "Ana Julia"))
                .body("users.user.salary.find{it != null}.toDouble()", is(1234.5678))
                .body("users.user.age.collect{it.toInteger() * 2}", hasItems(40, 50, 60))
                .body("users.user.name.findAll{it.toString().startsWith('Maria')}.collect{it.toString().toUpperCase()}", is("MARIA JOAQUINA"));

    }
   /* @Test
    public void devoFazerPesquisasAvancadasComXmlEJava() {
        ArrayList<NodeImpl> nomes = given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .extract().path("users.user.name.findAll{it.toString().contains('n')}");
                Assert.assertEquals("Maria Joaquina".toUpperCase(), nomes.get(0).toString().toUpperCase());
                Assert.assertTrue("ANA JULIA".equalsIgnoreCase(nomes.get(1).toString()));

        System.out.println();

    }*/

    @Test
    public void devoFazerPesquisasAvancadasComXPath(){
        given()
                .when()
                .get("https://restapi.wcaquino.me/usersXML")
                .then()
                .statusCode(200)
                .body(hasXPath("count(/users/user)", is("3")))
                .body(hasXPath("/users/user[@id = '2']"))
                .body(hasXPath("//user[@id = '1']"))
                .body(hasXPath("//name[text() = 'Luizinho']/../../name", is ("Ana Julia")))
                .body(hasXPath("//name[text() = 'Ana Julia']/following-sibling::filhos", allOf (containsString("Zezinho"), containsString("Luizinho"))))
                .body(hasXPath("/users/user/name", is("João da Silva")))
                .body(hasXPath("/users/user[2]/name", is("Maria Joaquina")))
                .body(hasXPath("//name", is("João da Silva")))
                .body(hasXPath("/users/user[last()]/name", is("Ana Julia")))
                .body(hasXPath("count(/users/user/name[contains(., 'n')])", is("2")))
                .body(hasXPath("//user[age < 24]/name", is("Ana Julia")))
                .body(hasXPath("//user[age > 20 and age <  30]/name", is("Maria Joaquina")));
    }
    @Test
    public void atributosEstaticos() {


        given()
                .log().all()
                .when()
                .get("/usersXML/3")
                .then()
                .statusCode(200)

                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos")
                .body("name.size()", is(2))

                .detachRootPath("filhos")
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))
                .appendRootPath("filhos")
                .body("name", hasItem("Zezinho"))
                .body("name", hasItems("Zezinho", "Luizinho"))
        ;

    }

    @Test
    public void requestResponseSpecification() {

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.log(LogDetail.ALL);
        reqSpec  = reqBuilder.build();

        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        resSpec = resBuilder.build();

        requestSpecification = reqSpec;
        responseSpecification = resSpec;



        given()
                //.spec(reqSpec)
                .when()
                .get("/usersXML/3")
                .then()
                //.statusCode(200)
                //.spec(resSpec)

                .rootPath("user")
                .body("name", is("Ana Julia"))
                .body("@id", is("3"))

                .rootPath("user.filhos")
                .body("name.size()", is(2))

                .detachRootPath("filhos")
                .body("filhos.name[0]", is("Zezinho"))
                .body("filhos.name[1]", is("Luizinho"))
                .appendRootPath("filhos")
                .body("name", hasItem("Zezinho"))
                .body("name", hasItems("Zezinho", "Luizinho"));

    }
}

