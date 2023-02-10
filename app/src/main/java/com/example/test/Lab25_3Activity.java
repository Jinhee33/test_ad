package com.example.test;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
public class Lab25_3Activity extends AppCompatActivity {



    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab25_3);
        recyclerView=findViewById(R.id.lab3_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add~~~~~~~~~~~~~~~~
        RetrofitService networkService = RetrofitFactory.create();
        networkService.getList("101").enqueue(new Callback<PageListModel>() {
            @Override
            public void onResponse(Call<PageListModel> call, Response<PageListModel> response) {
                if (response.isSuccessful()) {
                    MyAdapter adapter = new MyAdapter(response.body().articles);
                    recyclerView.setAdapter(adapter);
                    Log.d(TAG, "onResponse: 성공, 결과\n" + recyclerView.toString());
                } else {
                    Log.d(TAG, "onResponse: 실패");
                }
            }

            @Override
            public void onFailure(Call<PageListModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        //~~~~~~~~~~~~~~~~~~~~
    }
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitleView;
        public TextView itemTimeView;
        public TextView itemDescView;
        public ImageView itemImageView;

        public ItemViewHolder(View view) {
            super(view);
            itemTitleView=view.findViewById(R.id.lab3_item_title);
            itemTimeView=view.findViewById(R.id.lab3_item_time);
            itemDescView=view.findViewById(R.id.lab3_item_desc);
            itemImageView=view.findViewById(R.id.lab3_item_image);
        }
    }
    class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {
        List<ItemModel> articles;
        public MyAdapter(List<ItemModel> articles) {
            this.articles=articles;
        }
        @Override
        public int getItemCount() {
            return articles.size();
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_lab3, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

            ItemModel item=articles.get(position);

            String author = item.table_num == null || item.table_num.isEmpty() ? "101" : item.table_num;
            String titleString =  author+" - "+ item.menu_name;

            holder.itemTitleView.setText(titleString);
            holder.itemDescView.setText(item.menu_price);
        }

    }

}