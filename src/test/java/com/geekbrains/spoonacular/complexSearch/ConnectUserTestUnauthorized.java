package com.geekbrains.spoonacular.complexSearch;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class ConnectUserTestUnauthorized {

    @BeforeAll
    static void beforeAll(){
        RestAssured.baseURI = "https://api.spoonacular.com";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .build();
    }

    @Test
    void connectUserUnauthorized401() {

        RestAssured.given()
                .log()
                .all()
                .body(Map.of("username", "random", "firstName", "randomName", "lastname", "randomLastName", "email", "random@gmail.com"))
                .expect()
                .statusCode(401)
                .time(lessThanOrEqualTo(1500L))
                .header("Content-Type", "application/json")
                .body("status", is("failure"))
                .body("code", is(401))
                .body("message", is("You are not authorized."))
                .log()
                .all()
                .when()
                .post("/users/connect");
    }

}
