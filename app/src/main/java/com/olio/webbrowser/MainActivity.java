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

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    WebView web;
    EditText addressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = (WebView) findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        addressBar = (EditText) findViewById(R.id.addressBar);
        addressBar.setMaxLines(1);
        addressBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    String urlString = addressBar.getText().toString();
                    loadPage(urlString);
                    return true;
                }
                return false;
            }
        });
    }
    public void loadPage(String urlinput){
        String urlString = "https://" + urlinput;
        web.loadUrl(urlString);
        addressBar.setText(urlString);
    }
    public void refreshPage(View v){
        web.reload();
    }
}