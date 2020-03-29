package com.example.embibeassignment.ui;

import android.app.Application;


import com.example.embibeassignment.di.DaggerNetworkComponent;
import com.example.embibeassignment.di.NetworkComponent;

public class App extends Application {
    private NetworkComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        //needs to run once to generate it
        component = DaggerNetworkComponent.builder()
                .build();
    }
    public NetworkComponent getComponent() {
        return component;
    }
}
