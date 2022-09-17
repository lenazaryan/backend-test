package com.geekbrains.db.model;

import api.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.List;


public class MinimarketService {
    private final MinimarketApi api;

    public MinimarketService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MinimarketApi.class);
    }


    public ObjectResponse getProducts () throws IOException {
        Response<List<Product>> response = api.getProducts().execute();
        ObjectResponse objectResponse = new ObjectResponse();
        if (response.isSuccessful()) {
            objectResponse.setObject(response.body());
        } else {
            objectResponse.setError(response.errorBody().toString());
        }
        return objectResponse;
    }


    public ProductResponse getProductById (Long id) throws IOException {
        Response<Product> response = api.getProductById(id).execute();
        ProductResponse productResponse = new ProductResponse();
        if (response.isSuccessful()) {
            productResponse.setProduct(response.body());
        } else {
            productResponse.setError(response.errorBody().string());
        }
        return productResponse;
    }


    public ObjectResponse createProduct() throws IOException {
            Response<Object> response = api.createProduct(new Product()).execute();
            ObjectResponse objectResponse = new ObjectResponse();
            if (response.isSuccessful()) {
                objectResponse.setObject(response.body());
            } else {
                objectResponse.setError(response.errorBody().string());
            }
            return objectResponse;
    }

    public ObjectResponse updateProduct() throws IOException {
        Response<Object> response = api.updateProduct(new Product()).execute();
        ObjectResponse objectResponse = new ObjectResponse();
        if (response.isSuccessful()) {
            objectResponse.setObject(response.body());
        } else {
            objectResponse.setError(response.errorBody().string());
        }
        return objectResponse;
    }

    public ObjectResponse deleteProduct(Long id) throws IOException {
        Response<Object> response = api.deleteProduct(id).execute();
        ObjectResponse objectResponse = new ObjectResponse();
        if (response.code() == 200) {
            objectResponse.setObject(response.headers());
        } else {
            objectResponse.setError(response.errorBody().string());
        }
        return objectResponse;
    }

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://minimarket1.herokuapp.com/market")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
