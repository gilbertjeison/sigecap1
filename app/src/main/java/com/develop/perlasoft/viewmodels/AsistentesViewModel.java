package com.develop.perlasoft.viewmodels;

import android.app.Application;

import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.model.AsistentesData;
import com.develop.perlasoft.repository.AsistentesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class AsistentesViewModel extends AndroidViewModel {

    private final AsistentesRepository mRepository;

    public AsistentesViewModel(@NonNull Application application) {
        super(application);

        mRepository = ((SigecapApplication)application).getAsistentesRepository();
    }

    public Completable saveAsistente(Asistentes asistente){
        return mRepository.InsertAsistente(asistente);
    }

    public Completable saveAllAsistente(List<Asistentes> asistente){
        return mRepository.InsertAllAsistente(asistente);
    }

    public Completable updateAsistentes(Asistentes asistente){
        return mRepository.UpdateAsistente(asistente);
    }

    public Completable deleteAsistentes(Asistentes asistente){
        return mRepository.DeleteAsistente(asistente);
    }

    public Observable<List<AsistentesData>> getAsistentes()
    {
        return mRepository.getAsistentes();
    }
}
