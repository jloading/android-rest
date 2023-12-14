package com.example.greertingcard;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogApiService {
    @GET("api/breeds/image/random")
    Call<DogResponse> getRandomDogImage();
}
