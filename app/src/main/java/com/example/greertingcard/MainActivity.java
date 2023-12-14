package com.example.greertingcard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView breedText;
    private DogApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_dog);
        breedText = findViewById(R.id.text_breed);
        Button randomButton = findViewById(R.id.button_random);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DogApiService.class);

        randomButton.setOnClickListener(v -> fetchRandomDogImage());
    }

    private void fetchRandomDogImage() {
        service.getRandomDogImage().enqueue(new Callback<DogResponse>() {
            @Override
            public void onResponse(Call<DogResponse> call, Response<DogResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DogResponse dogResponse = response.body();
                    String imageUrl = dogResponse.getMessage();
                    // Mostrar la imagen utilizando Glide u otra biblioteca
                    Glide.with(MainActivity.this).load(imageUrl).into(imageView);
                } else {
                    // Manejo de errores
                    try {
                        Log.e("API Error", "Error en la respuesta de la API. Cuerpo: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<DogResponse> call, Throwable t) {
                // Manejo de error
                Log.e("API Failure", "Fallo en la solicitud de la API", t);
            }
        });
    }

}
