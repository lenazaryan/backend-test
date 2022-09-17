package com.geekbrains.minimarket;
import com.geekbrains.db.model.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import static org.hamcrest.Matchers.*;

public class MinimarketApiTest {
    private final MinimarketService service = new MinimarketService();
    private static MinimarketApi api;

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "http://minimarket1.herokuapp.com/market";
        RestAssured.requestSpecification = new RequestSpecBuilder().build();
    }


    @Test
    void getCategoryByIdSuccess() {
        RestAssured.given()
                .pathParam("id", 1)
                .log()
                .all()
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .log()
                .body()
                .when()
                .get("/api/v1/categories/{id}")
                .as(Categories.class);
    }

    @Test
    void getCategoryByIdNotFound() {
        RestAssured.given()
                .pathParam("id", 444)
                .log()
                .all()
                .expect()
                .statusCode(404)
                .body("status", is(404))
                .body("message", is("Unable to find category with id: 444"))
                .body("timestamp", is(notNullValue()))
                .log()
                .body()
                .when()
                .get("/api/v1/categories/{id}")
                .as(Categories.class);
    }

    @Test
    void getProductByIdSuccess() {
        RestAssured.given()
                .pathParam("id", 444)
                .log()
                .all()
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1000L))
                .log()
                .body()
                .body("title", is("Banana"))
                .body("price", is(70))
                .body("categoryTitle", is("Food"))
                .when()
                .get("/api/v1/products/{id}")
                .as(Products.class);
    }

    @Test
    void getProductByIdNotFound() {
        String actual = RestAssured.given()
                .pathParam("id", 4444)
                .log()
                .all()
                .expect()
                .statusCode(404)
                .time(lessThanOrEqualTo(2000L))
                .log()
                .body()
                .when()
                .get("/api/v1/products/{id}")
                .asPrettyString();
//        String expected = readResourceAsString("expectedNotFoundProduct.json");
//        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.when(IGNORING_VALUES));
    }

    @Test
    void addProduct() throws IOException {
        Product obj = new Product(null, "Bread", 100, "Food");
        ObjectResponse product = service.createProduct();
        System.out.println(product);
        ObjectResponse updatedProducts = service.getProducts();
        System.out.println(updatedProducts);
    }


    @Test
    void deleteProduct(){
        RestAssured.given()
                .log()
                .all()
                .pathParam("id", 123)
                .expect()
                .statusCode(oneOf(200, 204))
                .when()
                .delete("/api/v1/products/{id}");
    }


    public String readResourceAsString(String resourceName) {
        String path = getClass().getSimpleName() + FileSystems.getDefault().getSeparator() + resourceName;
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            assert inputStream != null;
            byte[] data = inputStream.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
