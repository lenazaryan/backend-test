package com.geekbrains.spoonacular.complexSearch;
import api.response.ApiSearchResults;
import com.geekbrains.spoonacular.BaseTest;
import io.restassured.RestAssured;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.stream.Stream;
import static net.javacrumbs.jsonunit.core.Option.*;
import static org.hamcrest.Matchers.*;


public class ComplexSearchTest extends BaseTest {


    @Test
    void testSearchBread() {

        ApiSearchResults actually = RestAssured.given()
                .param("number", 3)
                .param("limitLicense", true)
                .param("query", "bread")
                .log()
                .uri()
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .body("totalResults", is(175))
                .body("results", hasSize(3))
                .log()
                .body()
                .when()
                .get("/recipes/complexSearch")
                .body()
                .as(ApiSearchResults.class);
        actually.getResults().get(0).getImage();

        String expected = readResourceAsString("expected.json");
        JsonAssert.assertJsonEquals(
                expected,
                actually,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );
    }

    @ParameterizedTest
    @MethodSource("resources")
    void testImageRecognize(String image) {

        RestAssured.given()
                .log()
                .all()
                .param("imageUrl", image)
                .expect()
                .statusCode(200)
                .body("status", is("success"))
                .body("category", is("burger"))
                .body("probability", greaterThan(0.6f))
                .log()
                .all()
                .when()
                .get("/food/images/classify");
    }

    public static Stream<Arguments> resources() {
        Arguments f1 = Arguments.of("https://cdn.discordapp.com/icons/525976020919123981/f2ccc3ec3e36988bfa65da0bdae715c8.jpg");
        Arguments f2 = Arguments.of("https://burger-king-kupon.ru/wp-content/uploads/2022/03/1648284144_48dc525c690ab68339a7226c1087654a.png");
        Arguments f3 = Arguments.of("https://bigoven-res.cloudinary.com/image/upload/t_recipe-256/hanger-steak-sandwich-with-bourbon-creamed-spinach-2204420.jpg");
        return Stream.of(f1, f2, f3);
    }


    @Test
    void connectUserSuccess(){

        RestAssured.given()
                .log()
                .all()
                .body(Map.of("username", "random", "firstName", "random", "lastname", "random", "email", "random"))
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .header("Content-Type", "application/json")
                .body("status", is("success"))
                .body("username", isA(String.class))
                .body("spoonacularPassword", isA(String.class))
                .body("hash", isA(String.class))
                .log()
                .all()
                .when()
                .post("/users/connect");
    }

    @Test
    void connectUserWithoutBodyFailure400(){

        RestAssured.given()
                .log()
                .all()
                .expect()
                .statusCode(400)
                .time(lessThanOrEqualTo(1500L))
                .header("Content-Type", "application/json")
                .body("status", is("failure"))
                .body("code", is(400))
                .body("message", is("Could not parse JSON body."))
                .log()
                .all()
                .when()
                .post("/users/connect");

    }

    @Test
    void generateShoppingListSuccessful200(){

        Object actual = RestAssured.given()
                .log()
                .all()
                .queryParam("apiKey", "82edbd35678d4210a138c8a53a47688f")
                .queryParam("hash", "cfb46db8e2cff3e37fe328a89320e14cf18efa8c")
                .pathParams("username", "murphy-erdman19")
                .pathParam("start-date", "2022-02-06")
                .pathParam("end-date", "2022-02-28")
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .header("Content-Type", "application/json")
                .body("aisles", hasSize(0)) // actual is (LinkedHashMap), aisles is(ArrayList)
                .body("cost", is(0.0F))
                .body("startDate", is(1644105600))
                .body("endDate", is(1646006400))
                .when()
                .post("/mealplanner/{username}/shopping-list/{start-date}/{end-date}")
                .as(Object.class);
        System.out.println(actual);
    }

    @Test
    void addToShoppingListSuccessful200(){

        Object actualRes = RestAssured.given()
                .log()
                .all()
                .queryParam("hash", "cfb46db8e2cff3e37fe328a89320e14cf18efa8c")
                .pathParams("username", "murphy-erdman19")
                .body(Map.of("item", "1 package baking powder", "aisle", "Baking", "parse", true))
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .header("Content-Type", "application/json")
                .when()
                .post("/mealplanner/{username}/shopping-list/items")
                .as(Object.class);

        String expected = readResourceAsString("addedToShoppingList.json");
        JsonAssert.assertJsonEquals(
                expected,
                actualRes,
                JsonAssert.when(IGNORING_VALUES));
    }

    @Test
    void getShoppingListSuccessful200(){

        LinkedHashMap actualRes = RestAssured.given()
                .log()
                .all()
                .queryParam("hash", "cfb46db8e2cff3e37fe328a89320e14cf18efa8c")
                .pathParams("username", "murphy-erdman19")
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .body("aisles[0].items[0].name", is("baking powder")) // array element by index contains exact value
                .header("Content-Type", "application/json;charset=utf-8")
                .when()
                .get("mealplanner/{username}/shopping-list")
//                .asPrettyString();
//                .as(Object.class); // will print actualRes in LinkedHashMap -> then possible to print actualRes as LinkedHashMap and get any key, example
                .as(LinkedHashMap.class);
        Object cost = actualRes.get("cost");
        System.out.println(cost);

//        String expected = readResourceAsString("getShoppingListWithItem.json");
//        JsonAssert.assertJsonEquals(
//                expected,
//                actualRes,
//                JsonAssert.when(IGNORING_ARRAY_ORDER));

    }

    @Test
    void deleteShoppingListSuccessful200(){
        RestAssured.given()
                .log()
                .all()
                .queryParam("hash", "cfb46db8e2cff3e37fe328a89320e14cf18efa8c")
                .pathParams("username", "murphy-erdman19")
                .pathParam("id", "1297577")
                .expect()
                .statusCode(200)
                .time(lessThanOrEqualTo(1500L))
                .body("status", is("success"))
                .when()
                .delete("/mealplanner/{username}/shopping-list/items/{id}");
    }


    private String readResourceAsStringV2(String resName){
        try(InputStream is = new FileInputStream("resName")) {
            byte[] data = is.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stream<Arguments> resourcesV2(){
        String directory = getClass().getSimpleName() + FileSystems.getDefault().getSeparator();
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i <= 3 ; i++) {
            URL resource = getClass().getResource(directory + i + ".png");
            assert resource != null;
            String path = resource.getFile();
            File file = new File(path);
            arguments.add(Arguments.of(file));
        }
        return arguments.stream();
    }

}