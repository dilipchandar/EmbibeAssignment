package com.example.embibeassignment.di;

import android.app.Application;
import android.content.Context;

import com.example.embibeassignment.MyRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.themoviedb.org/";

    private Application application;

    public NetworkModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }


    @Singleton
    @Provides
    static Retrofit provideRetrofit() {

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static MyRepo provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(MyRepo.class);
    }
}