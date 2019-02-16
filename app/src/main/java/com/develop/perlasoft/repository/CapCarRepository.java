package com.develop.perlasoft.repository;

import com.develop.perlasoft.database.SigecapDB;
import com.develop.perlasoft.entities.CapacitacionesCargos;

import java.util.List;

import io.reactivex.Completable;

public class CapCarRepository {
    private final SigecapDB sigecapDB;
    private static CapCarRepository sInstance;

    public CapCarRepository(SigecapDB sigecapDB) {
        this.sigecapDB = sigecapDB;
    }

    public static CapCarRepository getInstance(final SigecapDB sigecapDB1)
    {
        if (sInstance == null) {
            synchronized (CapCarRepository.class) {
                sInstance = new CapCarRepository(sigecapDB1);
            }
        }
        return sInstance;
    }

    public Completable InsertAllCapCar(List<CapacitacionesCargos> capcar)
    {
        return Completable.fromAction(() ->{
            sigecapDB.getCapCapDao().insertAllCapCar(capcar);
        });
    }
}
