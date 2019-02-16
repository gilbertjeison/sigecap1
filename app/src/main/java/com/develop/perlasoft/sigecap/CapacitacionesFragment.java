package com.develop.perlasoft.sigecap;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.viewmodels.CapacitacionesViewModel;
import com.github.clans.fab.FloatingActionButton;

public class CapacitacionesFragment extends Fragment{

    private CapacitacionesViewModel mViewModel;
    Unbinder unbinder;
    @BindView(R.id.fab_add_cap)
    FloatingActionButton fabAddCap;

    public static CapacitacionesFragment newInstance() {
        return new CapacitacionesFragment();
    }

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
        // TODO: Use the ViewModel
        this.fabAddCap.getButtonSize();
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
}
