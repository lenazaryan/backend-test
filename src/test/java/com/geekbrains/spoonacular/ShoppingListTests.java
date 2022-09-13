package com.geekbrains.spoonacular;
import api.request.AddToShoppingListRequest;
import api.request.ApiUserConnectRequest;
import api.response.ApiUserConnectResult;
import api.response.shoppinglist.Item;
import api.response.shoppinglist.ShoppingListResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingListTests extends BaseTest {
    private static Integer idForRemove;
    private static ApiUserConnectResult connectedUser;

    @Test
    @Order(1)
    void connectUser200(){
        connectedUser = given()
                .contentType(ContentType.JSON)
                .body(new ApiUserConnectRequest(
                        "randomUsername", "randomFirstName",
                        "randomLastName", "random@email.com"))
                .expect()
                .log()
                .all()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .body("status", is("success"))
                .body("username", notNullValue())
                .body("hash", notNullValue())
                .when()
                .post("/users/connect")
                .as(ApiUserConnectResult.class);
    }
    @Test
    @Order(2)
    void getEmptyShoppingList() {
        ShoppingListResponse actualResult = given()
                .pathParam("username", connectedUser.getUsername())
                .queryParam("hash", connectedUser.getHash())
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .body("aisles", hasSize(0))
                .body("cost", is(0.0F))
                .body("startDate", is(notNullValue()))
                .body("endDate", is(notNullValue()))
                .when()
                .get("/mealplanner/{username}/shopping-list")
                .as(ShoppingListResponse.class);
        System.out.println(actualResult);

    }

    @Test
    @Order(3)
    void addItemToShoppingList() {
        AddToShoppingListRequest request = AddToShoppingListRequest.builder()
                .item("1kg apple pie")
                .aisle("pie")
                .parse(true)
                .build();

        Item addedItem = given()
                .pathParam("username", connectedUser.getUsername())
                .queryParam("hash", connectedUser.getHash())
                .body(request)
                .expect()
                .log()
                .body()
                .when()
                .post("/mealplanner/{username}/shopping-list/items")
                .as(Item.class);

        idForRemove = addedItem.id;

    }

    @Test
    @Order(4)
    void deleteItem() {
        given()
                .pathParam("username", connectedUser.getUsername())
                .pathParam("id", idForRemove)
                .queryParam("hash", connectedUser.getHash())
                .expect()
                .log()
                .body()
                .when()
                .delete("/mealplanner/{username}/shopping-list/items/{id}");

        getEmptyShoppingList();
    }

    @Test
    @Order(5)
    void generateShoppingList(){

        given()
                .pathParam("username", connectedUser.getUsername())
                .pathParam("start-date", 20001212)
                .pathParam("end-date", 20001211)
                .queryParam("hash", connectedUser.getHash())
                .expect()
                .statusCode(200)
                .log()
                .body()
                .when()
                .post("/mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .asPrettyString();
        }
}
