package com.example.newssolo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvArticles = findViewById(R.id.rvArticles);

        articles = new ArrayList<>();

        ArticleAdapter articleAdapter = new ArticleAdapter(this, articles);
        rvArticles.setAdapter(articleAdapter);

        rvArticles.setLayoutManager(new LinearLayoutManager(this));

        int numItems = 3;
        String tickers = "FB";

        String url = String.format("https://stocknewsapi.com/api/v1?tickers=%s,&items=%d&token=i0rpdgcnbrcgaimxbclxhztmuu6sk8jm79zcludj&fbclid=IwAR0pguARasu-pDs_Jcy4Wc4fCL_JIXCjRc_JYwsSN57xOSCnhleL3I2LDHA", tickers,numItems);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("data");// results is an array in the json
                    articles.addAll(Article.fromJsonArray(results));
                    articleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}