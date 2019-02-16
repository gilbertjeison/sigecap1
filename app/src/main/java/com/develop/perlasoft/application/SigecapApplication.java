package com.develop.perlasoft.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.develop.perlasoft.database.SigecapDB;
import com.develop.perlasoft.repository.AsistentesRepository;
import com.develop.perlasoft.repository.CapacitacionesRepository;
import com.develop.perlasoft.repository.TiposDataRepository;

import androidx.appcompat.app.AppCompatActivity;

public class SigecapApplication extends Application {

    private static Context context;
    private static SigecapApplication myApplication;
    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        myApplication = this;
        SigecapApplication.context = getApplicationContext();

        TypefaceProvider.registerDefaultIconSets();
    }

    public SigecapDB getDatabase()
    {
        return SigecapDB.getInstance(this,appExecutors);
    }

    public AsistentesRepository getAsistentesRepository()
    {
       return AsistentesRepository.getInstance(getDatabase());
    }

    public TiposDataRepository getTiposDataRepository()
    {
        return TiposDataRepository.getInstance(getDatabase());
    }

    public CapacitacionesRepository getCapacitacionesRepository()
    {
        return CapacitacionesRepository.getInstance(getDatabase());
    }

    public static void setActionBarTitle(Activity activity, String title)
    {
        ((AppCompatActivity)activity).getSupportActionBar().setTitle(title);
    }



}
