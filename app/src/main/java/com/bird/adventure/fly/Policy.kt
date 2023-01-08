package com.bird.adventure.fly

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class Policy : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        //supportActionBar?.hide()

        val web: WebView = findViewById(R.id.web_browser)
        val web_no_connect: LinearLayout = findViewById(R.id.no_connect)

        val data = intent.getStringExtra("polisy")

        //проверка инета
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                if (isAvailable) {
                    web.visibility = View.VISIBLE
                    web_no_connect.visibility = View.GONE
                } else {
                    web.visibility = View.GONE
                    web_no_connect.visibility = View.VISIBLE
                }
            }
        }


        val url = data.toString()

        val serverURL = "http://185.253.45.16/lander/policy2/index.html"

        web.loadUrl(serverURL)
        web.settings.javaScriptEnabled = true
        web.canGoBack()
        web.settings.loadsImagesAutomatically = true
        //добавление кэша и куки
        web.settings.setAppCacheEnabled(false)
        web.settings.databaseEnabled = true
        web.settings.domStorageEnabled = true; // Открываем кеш DOM
        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

        //взаимодествие до прогрузки страницы в вэбвью
        web.webViewClient = object : WebViewClient() {

            //начало заргузки страницы
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.bird.adventure.fly')})()")

            }

            //завершение заргузки страницы
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }

        }

    }

    override fun onBackPressed() {
        val web_browser: WebView = findViewById(R.id.web_browser)
        if (web_browser.canGoBack())
            web_browser.goBack()
        else
            finish()
    }

}