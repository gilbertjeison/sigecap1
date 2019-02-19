package com.develop.perlasoft.sigecap;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.perlasoft.adapters.CapacitacionesAdapter;
import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.model.CapacitacionesData;
import com.develop.perlasoft.viewmodels.CapacitacionesViewModel;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CapacitacionesFragment extends Fragment implements CapacitacionesAdapter.OnPressHandler{

    private CapacitacionesViewModel mViewModel;
    public static final CompositeDisposable mDisposable = new CompositeDisposable();
    Unbinder unbinder;
    @BindView(R.id.fab_add_cap)
    FloatingActionButton fabAddCap;
    @BindView(R.id.recCapacitaciones)
    RecyclerView recyclerView;
    @BindView(R.id.empty_viewc)
    TextView txtNoData;
    private String TAG = CapacitacionesFragment.class.getSimpleName();
    private List<CapacitacionesData> listCap;
    CapacitacionesAdapter capAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.capacitaciones_fragment, container, false);
        unbinder = ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CapacitacionesViewModel.class);

        listCap = new ArrayList<>();
        capAdapter = new CapacitacionesAdapter(getActivity(), listCap, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(capAdapter);

        mDisposable.add(mViewModel.getCapacitaciones()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<CapacitacionesData>>(){
                    @Override
                    public void onNext(List<CapacitacionesData> capacitacionesData) {
                        listCap.clear();
                        listCap.addAll(capacitacionesData);
                        capAdapter.notifyDataSetChanged();

                        if (listCap.size()>0)
                        {
                            txtNoData.setVisibility(View.GONE);
                        }
                        else
                        {
                            txtNoData.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @OnClick(R.id.fab_add_cap)
    void pruebaClick (View view){
        AddCapacitacionFragment fragment = new AddCapacitacionFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .addToBackStack("CapacitacionesFragment")
                .commit();
        SigecapApplication.setActionBarTitle(getActivity(),"Nueva capacitación");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }


    @Override
    public void onPress(CapacitacionesData cD) {

    }
}
