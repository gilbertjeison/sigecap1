package com.develop.perlasoft.repository;

import com.develop.perlasoft.database.SigecapDB;
import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.entities.CapacitacionesCargos;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class CapacitacionesRepository {
    private final SigecapDB sigecapDB;
    private static CapacitacionesRepository sInstance;

    public CapacitacionesRepository(SigecapDB sigecapDB) {
        this.sigecapDB = sigecapDB;
    }

    public static CapacitacionesRepository getInstance(final SigecapDB sigecapDB1)
    {
        if (sInstance == null) {
            synchronized (CapacitacionesRepository.class) {
                sInstance = new CapacitacionesRepository(sigecapDB1);
            }
        }
        return sInstance;
    }

    public Completable InsertCapacitacion(Capacitaciones capacitaciones)
    {
        return Completable.fromAction(() ->{
            sigecapDB.getCapacitacionesDao().insertCapacitacion(capacitaciones);
        });
    }

    public Single<Long> InsertCapacitacionObs(Capacitaciones capacitaciones)
    {

         return sigecapDB.getCapacitacionesDao().insertCapacitacion(capacitaciones);

    }

    public Completable InsertAllCapCar(List<CapacitacionesCargos> capcar)
    {
        return Completable.fromAction(() ->{
            sigecapDB.getCapCapDao().insertAllCapCar(capcar);
        });
    }
}
