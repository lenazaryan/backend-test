package com.geekbrains.spoonacular;

import api.SpoonacularApi;
import api.SpoonacularService;
import api.request.ApiUserConnectRequest;
import api.response.ApiUserConnectResult;
import api.response.shoppinglist.ShoppingListResponse;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpoonacularServiceTest extends BaseTest{
    private SpoonacularService service = new SpoonacularService();
    private final String HASH = "3e633e8b71919a216e114cedfed16137cc788464";
    private final String USERNAME = "orville41";

    @Test
    void generateList(){

         ShoppingListResponse newList = service.generate(USERNAME, 2022-02-06, 2022-03-07, HASH);
         System.out.println(newList);
     }
}
