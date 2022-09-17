package com.geekbrains.db.model;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MinimarketApi {

        @GET("/api/v1/products")
        Call<List<Product>> getProducts();

        @GET("/api/v1/products/{id}")
        Call<Product> getProductById(@Path("id") long id);

        @POST("/api/v1/products")
        Call<Object> createProduct(@Body Product product);

        @PUT("/api/v1/products")
        Call<Object> updateProduct(@Body Product product);

        @DELETE("/api/v1/products/{id}")
        Call<Object> deleteProduct(@Path("id") long id);

}
