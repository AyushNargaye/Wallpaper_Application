package com.example.wallpaperapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private EditText searchWall;
    private ImageView IVSearch;
    private RecyclerView RLCategory, RLWallpapers;
    private ProgressBar PLoading;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<RLCategoryModel> rlCategoryModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Accessing the API key from BuildConfig
        String apiKey = BuildConfig.MY_API_KEY;

        searchWall = findViewById(R.id.searchWall);
        IVSearch = findViewById(R.id.IVSearch);
        RLCategory = findViewById(R.id.RLCategory);
        RLWallpapers = findViewById(R.id.RLWallpapers);
        PLoading = findViewById(R.id.PLoading);
        wallpaperArrayList = new ArrayList<>();
        rlCategoryModelArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
        RLCategory.setLayoutManager(linearLayoutManager);
        categoryRVAdapter = new CategoryRVAdapter(rlCategoryModelArrayList, this, this::onCategoryClick);
        RLCategory.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RLWallpapers.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter = new WallpaperRVAdapter(wallpaperArrayList, this);
        RLWallpapers.setAdapter(wallpaperRVAdapter);

        getCategories();
        getWallpapers();

        IVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStr = searchWall.getText().toString();
                if(searchStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter Your Desired Wallpaper Keywords to Search", Toast.LENGTH_SHORT).show();
                } else {
                    getWallpapersByCategory(searchStr);
                }
            }
        });

    }
    private void getWallpapersByCategory(String category){
        wallpaperArrayList.clear();
        PLoading.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=1000&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                PLoading.setVisibility(View.GONE);
                try {
                    photoArray = response.getJSONArray("photos");
                    for (int i=0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(MainActivity.this, "Failed to Load Wallpapers.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", BuildConfig.MY_API_KEY);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    private void getWallpapers(){
        wallpaperArrayList.clear();
        PLoading.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=1000&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                PLoading.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for(int i=0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to Load Wallpapers..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization", BuildConfig.MY_API_KEY);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    private void getCategories(){
        rlCategoryModelArrayList.add(new RLCategoryModel("Technology", "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHR1Y2hub2xvZ318ZW58MHX8MHx8&ixlib-rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Programming", "https://images.unsplash.com/photo-1542831371-29b0f74f9713?ixid=MnwxMjA3fDB8MHXZZWFyY2h8MXX8cHJvZ3JhbW1pbmd8ZW58MHX8MHx8&ixlib-rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Nature", "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Travel", "https://images.pexels.com/photos/672358/pexels-photo-672358.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Architecture", "https://images.pexels.com/photos/256150/pexels-photo-256150.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Arts", "https://images.pexels.com/photos/1194420/pexels-photo-1194420.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500 = 18w = 500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Music", "https://images.pexels.com/photos/4348093/pexels-photo-4348093.jpeg?auto-compress&cs=tiny ?auto-compress&cs=tinysrgb&dpr = 18w = 500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Abstract", "https://images.pexels.com/photos/2110951/pexels-photo-2110951.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Cars", "https://images.pexels.com/photos/3802510/pexels-photo-3802510.jpeg?auto-compress&cs=tinysrgb&dpr=1&w=500"));
        rlCategoryModelArrayList.add(new RLCategoryModel("Flowers", "https://images.pexels.com/photos/1086178/pexels-photo-1086178.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"));
        categoryRVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCategoryClick(int position) {
        String category = rlCategoryModelArrayList.get(position).getCategory();
        getWallpapersByCategory(category);
    }
}

