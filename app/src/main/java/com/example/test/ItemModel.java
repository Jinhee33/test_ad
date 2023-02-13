package com.example.test;

import com.google.gson.annotations.SerializedName;

public class ItemModel {
    public long id;
    public String author;
    public String title;
    public String description;
    public String urlToImage;
    public String publishedAt;
    @SerializedName("table_Num")
    public String table_num;
    public String menu_name;
    public String menu_price;
}
