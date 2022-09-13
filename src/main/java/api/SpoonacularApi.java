package api;
import api.request.AddToShoppingListRequest;
import api.request.ApiUserConnectRequest;
import api.response.ApiSearchResults;
import api.response.ApiUserConnectResult;
import api.response.shoppinglist.Aisle;
import api.response.shoppinglist.ShoppingListResponse;
import api.response.shoppinglist.Item;
import retrofit2.Call;
import retrofit2.http.*;

public interface SpoonacularApi {

    @GET("/recipes/complexSearch")
    Call<ApiSearchResults> findRecipes(
            @Query("apiKey") String apiKey,
            @Query("query") String query,
            @Query("number") Integer number
    );

    @POST("/users/connect")
    Call<ApiUserConnectResult> connect(
            @Body ApiUserConnectRequest request,
            @Query("apiKey") String apiKey
    );

    @POST("/mealplanner/{username}/shopping-list/{start-date}/{end-date}")
    Call<ShoppingListResponse> generate(
            @Query("apiKey") String apiKey,
            @Query("username") String username,
            @Query("start-date") Integer startDate,
            @Query("end-date") Integer endDate,
            @Query("hash") String hash
            );

    @POST("/mealplanner/{username}/shopping-list/items")
    Call<ShoppingListResponse> addItem(
            @Body AddToShoppingListRequest request,
            @Query("apiKey") String apiKey,
            @Query("hash") String hash,
            @Query("username") String username
            );


    @DELETE("/mealplanner/{username}/shopping-list/items/{id}")
    Call<ShoppingListResponse> deleteItem(
            @Query("apiKey") String apiKey,
            @Query("username") String username,
            @Query("hash") String hash,
            @Query("id") Integer id);
}
