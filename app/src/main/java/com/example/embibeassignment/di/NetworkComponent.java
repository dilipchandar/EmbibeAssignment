package com.example.embibeassignment.di;

import com.example.embibeassignment.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface NetworkComponent {

    void inject(MainActivity mainActivity);

}
