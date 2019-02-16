package com.develop.perlasoft.viewmodels;

import android.app.Application;

import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.TiposDataCk;
import com.develop.perlasoft.repository.TiposDataRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;

public class TiposDataViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final TiposDataRepository tdRepository;

    public TiposDataViewModel(Application application) {
        super(application);



        tdRepository = ((SigecapApplication)application).getTiposDataRepository();
    }


    public Observable<List<TiposData>> getTD()
    {
        return tdRepository.getTiposData();
    }

    public Observable<List<TiposData>> getTDbyId(int id_tipo)
    {
        return tdRepository.getTiposDatabyId(id_tipo);
    }

    public Observable<List<TiposDataCk>> getTDCKbyId(int id_tipo)
    {
        return tdRepository.getTiposDataCkbyId(id_tipo);
    }
}
