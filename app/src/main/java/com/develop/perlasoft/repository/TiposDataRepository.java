package com.develop.perlasoft.repository;

import com.develop.perlasoft.database.SigecapDB;
import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.TiposDataCk;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.reactivex.Observable;

public class TiposDataRepository {

    private static TiposDataRepository sIntance;

    private final SigecapDB sigecapDB;
    private MediatorLiveData<List<TiposData>> mObservable;

    public TiposDataRepository(SigecapDB sigecapDB) {
        this.sigecapDB = sigecapDB;
    }

    public static TiposDataRepository getInstance(final SigecapDB sigecapDB)
    {
        if (sIntance == null) {
            synchronized (TiposDataRepository.class) {
                sIntance = new TiposDataRepository(sigecapDB);
            }
        }
        return sIntance;
    }

    public LiveData<List<TiposData>> getTiposDataById()
    {
        return mObservable;
    }

    public Observable<List<TiposData>> getTiposData()
    {
        return sigecapDB.getTiposDataDao().getTiposData();
    }

    public Observable<List<TiposData>> getTiposDatabyId(int id_tipo)
    {
        return sigecapDB.getTiposDataDao().getTiposDataById(id_tipo);
    }

    public Observable<List<TiposDataCk>> getTiposDataCkbyId(int id_tipo)
    {
        return sigecapDB.getTiposDataDao().getTiposDataCkById(id_tipo);
    }
}
