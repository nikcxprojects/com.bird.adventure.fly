package com.bird.adventure.fly;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.push.YandexMetricaPush;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("b4f9aba0-6f8c-4b8f-9b9c-85c1905cd1b2").build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetricaPush.init(getApplicationContext());
    }
}