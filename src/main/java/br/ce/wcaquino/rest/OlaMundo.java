package br.ce.wcaquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.w3c.dom.ls.LSOutput;

public class OlaMundo {

    public static void main(String[] args) {

        Response response = RestAssured.request (Method.GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString());
    }
}
