package api;

import api.request.AddToShoppingListRequest;
import api.request.ApiUserConnectRequest;
import api.response.ApiSearchResults;
import api.response.ApiUserConnectResult;
import api.response.shoppinglist.ShoppingListResponse;
import api.response.shoppinglist.Item;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;



public class SpoonacularService {
    private SpoonacularApi api;
    private String API_KEY = "82edbd35678d4210a138c8a53a47688f";

    public SpoonacularService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SpoonacularApi.class);
    }

    public ApiSearchResults findRecipes(String query, Integer number) {
        Call<ApiSearchResults> call = api.findRecipes(API_KEY, query, number);
        return RetrofitUtils.execute(call);
    }

    public ApiUserConnectResult connect(@Body ApiUserConnectRequest request) {
        Call<ApiUserConnectResult> call = api.connect(request, API_KEY);
        return RetrofitUtils.execute(call);
    }

    public ShoppingListResponse generate(String username, Integer startDate, Integer endDate, String hash){
        Call<ShoppingListResponse> call = api.generate(API_KEY, username, startDate, endDate, hash);
        return  RetrofitUtils.execute(call);
    }

    public ShoppingListResponse addItem(@Body AddToShoppingListRequest request, String hash, String username){
        Call<ShoppingListResponse> call = api.addItem(request, API_KEY, hash, username);
        return RetrofitUtils.execute(call);
    }

    public ShoppingListResponse deleteItem(String hash, String username, Integer id){
        Call<ShoppingListResponse> call = api.deleteItem(API_KEY, hash, username, id);
        return RetrofitUtils.execute(call);
    }



}
