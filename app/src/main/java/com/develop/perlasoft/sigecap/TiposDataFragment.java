package com.develop.perlasoft.sigecap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.viewmodels.TiposDataViewModel;

import java.util.List;

public class TiposDataFragment extends Fragment {

    private TiposDataViewModel mViewModel;


    @BindView(R.id.rvTiposData)
    RecyclerView rvTiposData;



    public static TiposDataFragment newInstance() {
        return new TiposDataFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.tipos_data_fragment, container, false);



        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TiposDataViewModel.class);


        // TODO: Use the ViewModel

    }



    @Override
    public void onStart() {
        super.onStart();


    }



    private void suscribeUi(LiveData<List<TiposData>> listLiveData)
    {
        //ACTUALIZAR LA LISTA CUANDO SUCEDAN CAMBIOS
        listLiveData.observe(this, new Observer<List<TiposData>>() {
            @Override
            public void onChanged(List<TiposData> tiposData) {

            }
        }
       );
    }
}
