package com.example.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;
/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
public class MainActivity extends AppCompatActivity {
    TextView titleView;
    TextView dateView;
    TextView contentView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView=(TextView)findViewById(R.id.lab1_title);
        dateView=(TextView)findViewById(R.id.lab1_date);
        contentView=(TextView)findViewById(R.id.lab1_content);
        imageView=(ImageView)findViewById(R.id.lab1_image);

        //add~~~~~~~~~~~~~~~~~~~~~
        //서버에 전송할 데이터
        HashMap<String, String> map=new HashMap<>();
        map.put("name","kkang");

        //문자열 HTTP 요청
        HttpRequester httpRequester=new HttpRequester();
        httpRequester.request("http://192.168.100.35:8000/files/test.json", map, httpCallback);
    }

    //문자열 결과 획득 callback 클래스
    HttpCallback httpCallback=new HttpCallback() {
        @Override
        public void onResult(String result) {
            //결과 json parsing
            try{
                JSONObject root=new JSONObject(result);
                titleView.setText(root.getString("title"));
                dateView.setText(root.getString("date"));
                contentView.setText(root.getString("content"));

                String imageFile=root.getString("file");
                if(imageFile!=null && !imageFile.equals("")){
                    //결과 문자열에 이미지 파일 정보가 있다면 다시 이미지 데이터 요청
                    HttpImageRequester imageRequester=new HttpImageRequester();
                    imageRequester.request("http://192.168.100.35:8000/files/"+imageFile, null, imageCallback);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    //획득한 이미지 데이터를 받기 위한 callback
    HttpImageCallback imageCallback=new HttpImageCallback() {
        @Override
        public void onResult(Bitmap d) {
            imageView.setImageBitmap(d);
        }
    };

}