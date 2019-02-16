package com.develop.perlasoft.repository;

import android.os.AsyncTask;

import com.develop.perlasoft.dao.AsistentesDAO;
import com.develop.perlasoft.database.SigecapDB;
import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.AsistentesData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class AsistentesRepository
{
    private final SigecapDB sDB;
    private static AsistentesRepository sInstance;


    public AsistentesRepository(SigecapDB sigecapDB)
    {
        this.sDB = sigecapDB;
    }

    public static AsistentesRepository getInstance(final SigecapDB sigecapDB)
    {
        if (sInstance == null){
            synchronized (AsistentesRepository.class){
                sInstance = new AsistentesRepository(sigecapDB);
            }
        }
        return sInstance;
    }

    public Observable<List<AsistentesData>> getAsistentes()
    {
        return sDB.getAsistentesDao().getAsistentes();
    }

    public Completable InsertAsistente(Asistentes asistente)
    {
        return Completable.fromAction(() ->{
            sDB.getAsistentesDao().insertAsistente(asistente);
        });
    }

    public Completable InsertAllAsistente(List<Asistentes> asistente)
    {
        return Completable.fromAction(() ->{
            sDB.getAsistentesDao().insertAsistente(asistente);
        });
    }

    public Completable UpdateAsistente(Asistentes asistente)
    {
        return Completable.fromAction(() ->{
            sDB.getAsistentesDao().updateAsistente(asistente);
        });
    }

    public Completable DeleteAsistente(Asistentes asistente)
    {
        return Completable.fromAction(() ->{
            sDB.getAsistentesDao().deleteAsistente(asistente);
        });
    }


}
