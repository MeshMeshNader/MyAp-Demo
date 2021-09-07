package com.demo.myapps.WebView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.demo.myapps.R;

public class WebView extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;
    String url = "";
    android.webkit.WebView mWebView;
    String user = "";
    String pwd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initViews();
    }

    private void initViews() {

        Intent intent = getIntent();
        url = intent.getStringExtra("linkToWeb");
        user = intent.getStringExtra("email");
        pwd = intent.getStringExtra("pass");


        refreshLayout = findViewById(R.id.swipe);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        mWebView = findViewById(R.id.web_view);
        mWebView.requestFocus();
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        mWebView.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);

                view.loadUrl("javascript:window.onload= (function(){"
                        + "var selectElementName = document.querySelector('input[type=\"email\"]');"
                        + "if(selectElementName){selectElementName.value =  \"" + user + "\";}"
                        + "var selectElementName = document.querySelector('input[type=\"text\"]');"
                        + "if(selectElementName){selectElementName.value =  \"" + user + "\";}"
                        + "var selectElementpassword = document.querySelector('input[type=\"password\"]');"
                        + "if(selectElementpassword){selectElementpassword.value =  \"" + pwd + "\";}"
                        + "})();");
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(android.webkit.WebView view, int progress) {

                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                mWebView.reload();
                refreshLayout.setRefreshing(false);
            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()
                        == MotionEvent.ACTION_UP && mWebView.canGoBack()) {
                    //handler.sendEmptyMessage(1);
                    mWebView.goBack();
                    return true;
                }

                return false;
            }
        });

    }
}