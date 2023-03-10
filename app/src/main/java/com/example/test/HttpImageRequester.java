package com.example.test;
//서버 연동으로 이미지 데이터를 얻기 위한 개발자 클래스
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교제에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
public class HttpImageRequester {

    HttpImageTask http;

    //외부에서 이미지 데이터 획득 목적으로 호출
    public void request(String url, HashMap<String, String> param, HttpImageCallback callback) {
        http = new HttpImageTask(url, param, callback);
        http.execute();
    }

    //외부에서 HTTP 통신 취소 목적으로 호출
    public void cancel() {
        if(http != null)
            http.cancel(true);
    }

    private class HttpImageTask extends AsyncTask<Void, Void, Bitmap> {
        String url;
        HashMap<String, String> param;
        HttpImageCallback callback;

        public HttpImageTask(String url, HashMap<String, String> param, HttpImageCallback callback) {
            this.url = url;
            this.param = param;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... nothing) {
            Bitmap response = null;
            String postData = "";
            PrintWriter pw = null;

            //add~~~~~~~~~~~~~~
            try {
                URL text=new URL(url);
                HttpURLConnection http=(HttpURLConnection)text.openConnection();
                http.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
                http.setConnectTimeout(10000);
                http.setReadTimeout(10000);
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                //데이터를 웹의 Query 문자열 형태로 변형
                if(param != null && param.size()>0){
                    Iterator<Map.Entry<String, String>> entries=param.entrySet().iterator();
                    int index=0;
                    while (entries.hasNext()){
                        if(index != 0){
                            postData=postData+"&";
                        }
                        Map.Entry<String, String> mapEntry=(Map.Entry<String, String>) entries.next();
                        postData= postData+mapEntry.getKey()+"="+ URLEncoder.encode(mapEntry.getValue(),"UTF-8");
                        index++;
                    }
                    //데이터 전송
                    pw=new PrintWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
                    pw.write(postData);
                    pw.flush();
                }
                //데이터 수신
                response= BitmapFactory.decodeStream(http.getInputStream());
            }catch (Exception e){
                e.printStackTrace();
                response=null;
            } finally {
                {
                    try{
                        if(pw != null) pw.close();
                    }catch (Exception e){}
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //add~~~~~~~~~~~~
            //callback에 데이터 전달
            this.callback.onResult(result);

        }
    }
}