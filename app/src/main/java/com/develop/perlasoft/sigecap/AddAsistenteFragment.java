package com.develop.perlasoft.sigecap;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import asia.kanopi.fingerscan.Status;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.AsistentesData;
import com.develop.perlasoft.scan.ScanActivity;
import com.develop.perlasoft.viewmodels.AsistentesViewModel;
import com.develop.perlasoft.viewmodels.TiposDataViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AddAsistenteFragment extends Fragment implements View.OnClickListener {

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private AsistentesViewModel assViewModel;
    private Unbinder unbinder;
    private byte[] img;
    private static final int SCAN_FINGER = 0;
    @BindView(R.id.txtNombres)
    EditText txtNombres;
    @BindView(R.id.txtCc)
    EditText txtCc;
    @BindView(R.id.spAreas)
    Spinner spAreas;
    @BindView(R.id.spCargos)
    Spinner spCargos;
    @BindView(R.id.btnhuella)
    BootstrapButton btnHuella;
    @BindView(R.id.btnGuadar)
    BootstrapButton btnGuardar;
    @BindView(R.id.imgHuella)
    ImageView imgHuella;
    @BindView(R.id.txtMessage)
    TextView txtMessage;
    private AsistentesData asisToEdit;
    private static String ASISTOEDIT = "asis_to_edit";
    private boolean isEditing = false;




    static AddAsistenteFragment newInstance(AsistentesData asistentesData) {

        AddAsistenteFragment fragment= new AddAsistenteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ASISTOEDIT,asistentesData);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_asistente_fragment, container, false);
        asisToEdit = (AsistentesData) (getArguments() != null ? getArguments().getSerializable(ASISTOEDIT) : null);
        unbinder = ButterKnife.bind(this,view);

        if (asisToEdit != null){
            isEditing = true;
        }
        else {
            isEditing = false;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TiposDataViewModel mViewModel = ViewModelProviders.of(this).get(TiposDataViewModel.class);
        assViewModel = ViewModelProviders.of(this).get(AsistentesViewModel.class);
        // TODO: Use the ViewModel

        if (!isEditing){
            mDisposable.add(mViewModel.getTDbyId(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                       @Override
                                       public void onNext(List<TiposData> tiposData) {

                                           ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner,tiposData);
                                           spAreas.setAdapter(arrayAdapter);
                                           arrayAdapter.notifyDataSetChanged();
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           e.printStackTrace();
                                       }

                                       @Override
                                       public void onComplete() {

                                       }
                                   }
                    ));

            mDisposable.add(mViewModel.getTDbyId(2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                       @Override
                                       public void onNext(List<TiposData> tiposData) {

                                           ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner,tiposData);
                                           spCargos.setAdapter(arrayAdapter);
                                           arrayAdapter.notifyDataSetChanged();
                                       }

                                       @Override
                                       public void onError(Throwable e) {

                                       }

                                       @Override
                                       public void onComplete() {

                                       }
                                   }
                    ));
        }

        btnHuella.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);

        //SI ES UNA EDICIÓN, CARGAR LOS DATOS
        if (isEditing){

            // CARGAR INFORMACIÓN
            txtNombres.setText(asisToEdit.nombres);
            txtCc.setText(String.valueOf(asisToEdit.cedula));

            mDisposable.add(mViewModel.getTDbyId(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                       @Override
                                       public void onNext(List<TiposData> tiposData) {


                                           ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>
                                                   (Objects.requireNonNull(getActivity()),R.layout.spinner,tiposData);

                                           spAreas.setAdapter(arrayAdapter);

                                           int index = 0;
                                           for (TiposData td: tiposData) {
                                               if (td.id==asisToEdit.id_area){
                                                   index = tiposData.indexOf(td);
                                                   break;
                                               }
                                           }

                                           spAreas.setSelection(index);
                                           arrayAdapter.notifyDataSetChanged();
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           e.printStackTrace();
                                       }

                                       @Override
                                       public void onComplete() {

                                       }
                                   }
                    ));

            mDisposable.add(mViewModel.getTDbyId(2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                       @Override
                                       public void onNext(List<TiposData> tiposData) {


                                           ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>
                                                   (Objects.requireNonNull(getActivity()),R.layout.spinner,tiposData);

                                           spCargos.setAdapter(arrayAdapter);

                                           int index = 0;
                                           for (TiposData td: tiposData) {
                                               if (td.id==asisToEdit.id_cargo){
                                                   index = tiposData.indexOf(td);
                                                   break;
                                               }
                                           }

                                           spCargos.setSelection(index);
                                           arrayAdapter.notifyDataSetChanged();
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           e.printStackTrace();
                                       }

                                       @Override
                                       public void onComplete() {

                                       }
                                   }
                    ));
        }
    }


    private void SaveAsistente()
    {
        btnGuardar.setEnabled(false);
        String nombres = txtNombres.getText().toString().trim();
        int cc = Integer.parseInt(txtCc.getText().toString().trim().isEmpty()? "0":txtCc.getText().toString().trim());
        int id_cargo = ((TiposData)spCargos.getSelectedItem()).id;
        int id_area = ((TiposData)spAreas.getSelectedItem()).id;

        if (!nombres.isEmpty() && cc > 0 && id_cargo> 0 && id_area > 0)
        {
            Asistentes asistentes = new Asistentes();

            asistentes.nombres = nombres;
            asistentes.cedula = cc;
            asistentes.id_area = id_area;
            asistentes.id_cargo = id_cargo;

            if (isEditing)
            {
                asistentes.id = asisToEdit.id;

                if (img != null)
                {
                    asistentes.huella_data = img;
                }

                mDisposable.add(assViewModel.updateAsistentes(asistentes)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            btnGuardar.setEnabled(true);
                            Snackbar.make(getView(), "Asistente modificado correctamente...", Snackbar.LENGTH_LONG)
                                    .setAction("Aceptar", null).show();

                            getActivity().onBackPressed();
                        }, throwable -> {
                            Snackbar.make(getView(), "Error al modificar asistente, inténtelo de nuevo...", Snackbar.LENGTH_LONG)
                                    .setAction("Aceptar", null).show();
                        }));
            }
            else{

                asistentes.huella_data = img;

                mDisposable.add(assViewModel.saveAsistente(asistentes)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            btnGuardar.setEnabled(true);
                            Snackbar.make(getView(), "Asistente creado correctamente...", Snackbar.LENGTH_LONG)
                                    .setAction("Aceptar", null).show();

                            getActivity().onBackPressed();
                        }, throwable -> {
                            Snackbar.make(getView(), "Error al crear asistente, inténtelo de nuevo...", Snackbar.LENGTH_LONG)
                                    .setAction("Aceptar", null).show();
                        }));
            }
        }
        else{
            Snackbar.make(getView(), "Por favor no dejar campos en blanco", Snackbar.LENGTH_LONG)
                    .setAction("Aceptar", null).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        int status;
        String errorMessage;

        switch (requestCode)
        {
            case (SCAN_FINGER):
            {
                if (resultCode == RESULT_OK)
                {
                    status = data.getIntExtra("status", Status.ERROR);
                    if (status == Status.SUCCESS)
                    {
                        txtMessage.setText("Huella capturada correctamente...!");
                        txtMessage.setTextColor(ContextCompat.getColor(getActivity(),R.color.green));
                        img = data.getByteArrayExtra("img");
                        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                        imgHuella.setImageBitmap(bmp);
                    }
                    else
                    {
                        errorMessage = data.getStringExtra("errorMessage");
                        txtMessage.setText("Error: "+errorMessage+"...!");
                        txtMessage.setTextColor(ContextCompat.getColor(getActivity(),R.color.red));
                    }
                }

                break;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        mDisposable.clear();
        SigecapApplication.setActionBarTitle(getActivity(),"Asistentes");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnhuella:
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivityForResult(intent, SCAN_FINGER);
                break;
            case R.id.btnGuadar:
                SaveAsistente();
                break;
        }
    }
}
