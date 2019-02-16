package com.develop.perlasoft.di;

import android.content.Context;

import com.develop.perlasoft.application.SigecapApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    SigecapApplication sApplication;

    public AppModule(SigecapApplication sApplication) {
        this.sApplication = sApplication;
    }

    @Provides
    public Context getAppContext()
    {
        return sApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    SigecapApplication provideApplication()
    {
        return sApplication;
    }
}
