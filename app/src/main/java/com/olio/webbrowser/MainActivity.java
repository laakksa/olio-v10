package com.olio.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    WebView web;
    EditText addressBar;
    int cursor;
    ArrayList<String> cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cache = new ArrayList<>();
        cursor = 0;
        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        addressBar = (EditText) findViewById(R.id.addressBar);
        addressBar.setMaxLines(1);
        //Loads page from url when enter key is pressed.
        addressBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    String urlString = addressBar.getText().toString();
                    loadPage(urlString, true);
                    return true;
                }
                return false;
            }
        });
    }

    //Loads page from url, sets cursor to last of list if user loads a new page.
    //Stores last 10 pages and removes the oldest one if a new one is added.
    public void loadPage(String urlinput, boolean newinput){
        String urlString;
        if(!urlinput.contains("https://")) {
            urlString = "https://" + urlinput;
        } else{
            urlString = urlinput;
        }
        web.loadUrl(urlString);
        addressBar.setText(urlString);
        if (newinput){
            if(cache.size() == 11){
                cache.remove(0);
            }
            cache.add(urlString);
            cursor = cache.size() - 1;
        }
    }


    public void refreshPage(View v){
        web.reload();
    }

    //Loads the previous page if there is one
    public void previousPage(View v){
        if(cursor >= 1){
            loadPage(cache.get(cursor-1), false);
            cursor--;
        }
    }

    //Loads the next page if there is one
    public void nextPage(View v){
        if (cursor < cache.size() - 1){
            loadPage(cache.get(cursor+1), false);
            cursor++;
        }
    }

    public void shoutOut(View v){
        web.evaluateJavascript("javascript: shoutOut()", null);
    }
}