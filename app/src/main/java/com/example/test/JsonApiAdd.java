package com.example.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class JsonApiAdd extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Menu> menuList;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab25_3);

        recyclerView = findViewById(R.id.lab3_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuList = new ArrayList<>();

        adapter = new MenuAdapter(menuList);
        recyclerView.setAdapter(adapter);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.35/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // Create API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Call API
        Call<List<Menu>> call = apiService.getMenus();
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                if (response.isSuccessful()) {
                    menuList.clear();
                    menuList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    Toast.makeText(JsonApiAdd.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JsonApiAdd.this,"Failed :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // API service interface
    public interface ApiService {
        @GET("OrderList")
        Call<List<Menu>> getMenus();
    }
}

// Menu model class
class Menu {
    private String tableNum;
    private String menuPrice;
    private String menuName;

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}

// Menu adapter class
class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Menu> menuList;

    public MenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lab25_3, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menuItem = menuList.get(position);
        holder.tableNum.setText(menuItem.getTableNum());
        holder.menuName.setText(menuItem.getMenuPrice());
        holder.menuPrice.setText(menuItem.getMenuName());
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        public TextView tableNum;
        public TextView menuName;
        public TextView menuPrice;

        public MenuViewHolder(View itemView) {
            super(itemView);
            tableNum = itemView.findViewById(R.id.table_num);
            menuName = itemView.findViewById(R.id.menu_name);
            menuPrice = itemView.findViewById(R.id.menu_price);
        }
    }
}