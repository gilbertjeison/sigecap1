package com.develop.perlasoft.viewmodels;

import android.app.Application;

import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.entities.CapacitacionesCargos;
import com.develop.perlasoft.model.CapacitacionesData;
import com.develop.perlasoft.repository.CapacitacionesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class CapacitacionesViewModel extends AndroidViewModel {

    private final CapacitacionesRepository mRepository;

    public CapacitacionesViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SigecapApplication)application).getCapacitacionesRepository();
    }

    public Single<Long> saveCapacitacion(Capacitaciones capacitaciones){
        return mRepository.InsertCapacitacionObs(capacitaciones);
    }

    public Observable<List<CapacitacionesData>> getCapacitaciones(){
        return mRepository.getCapacitaciones();
    }

    public Completable insertAllCapCar(List<CapacitacionesCargos> capcar){
        return mRepository.InsertAllCapCar(capcar);
    }
}
