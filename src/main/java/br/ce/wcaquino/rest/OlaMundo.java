package br.ce.wcaquino.rest;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class OlaMundo {

    public static void main(String[] args) {

        Response response = RestAssured.request (Method.GET, "https://stackoverflow.com/questions/67179242/noclassdeffounderror-getting-this-error-at-runtime");
        System.out.println(response.getBody().asString());
        System.out.println("Status code: " + response.statusCode());
    }
}
