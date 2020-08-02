package com.example.countryname;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.countryname.Model.Post;
import com.example.countryname.services.JsonPlaceHolderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    t1.setText("Code "+response.code());
                    return;
                }

                List<Post> Post = response.body();


                for(Post c : Post){
                    String content = "";
                    content += "UserId: "+c.getUserId()+"\n";
                    content += "ID: "+c.getId()+"\n";
                    content += "Title:  "+c.getTitle()+"\n";
                    content += "Body:  "+c.getBody()+"\n\n";
                    t1.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t1.setText("Error: "+t.getMessage());
            }
        });

    }
}