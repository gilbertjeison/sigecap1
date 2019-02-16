package com.develop.perlasoft.sigecap;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.develop.perlasoft.adapters.CargosMiniAdapter;
import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.entities.CapacitacionesCargos;
import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.TiposDataCk;
import com.develop.perlasoft.viewmodels.CapacitacionesViewModel;
import com.develop.perlasoft.viewmodels.TiposDataViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mabbas007.tagsedittext.TagsEditText;


public class AddCapacitacionFragment extends Fragment implements View.OnClickListener,  CargosMiniAdapter.CmiAdapterOnPressHandler  {

    private Unbinder unbinder;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private CapacitacionesViewModel capViewModel;

    private static final String CERO = "0";
    @BindView(R.id.recCargos)
    RecyclerView recyclerViewCargos;
    @BindView(R.id.spLugar)
    Spinner spLugar;
    @BindView(R.id.txtTemas)
    TagsEditText txtTemas;
    @BindView(R.id.txtHrInicio)
    BootstrapEditText txtHrInicio;
    @BindView(R.id.txtHrFin)
    BootstrapEditText txtHrFin;
    @BindView(R.id.btnHrIni)
    ImageButton btnHrIni;
    @BindView(R.id.btnHrFin)
    ImageButton btnHrFin;
    @BindView(R.id.txtDuracion)
    BootstrapEditText txtDuracion;
    @BindView(R.id.btnGuadarCap)
    BootstrapButton btnGuardar;
    @BindView(R.id.txtNombreSeminario)
    BootstrapEditText txtSeminario;

    private List<TiposDataCk> listData;
    private CargosMiniAdapter listAdapter;
    private Calendar datetimeInicio = Calendar.getInstance();
    private Calendar datetimeFin = Calendar.getInstance();

    //Calendario para obtener fecha & hora
    private final Calendar c = Calendar.getInstance();
    //Hora
    private final int hora = c.get(Calendar.HOUR);
    private final int minuto = c.get(Calendar.MINUTE);
//    @BindView(R.id.addcap_container)
//    ViewPager containerV;
//    @BindView(R.id.tabs)
//    TabLayout tbLayout;
//    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_capacitacion, container, false);
        unbinder = ButterKnife.bind(this,view);

//        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
//        containerV.setAdapter(sectionsPagerAdapter);
//        tbLayout.setupWithViewPager(containerV);
//
//        int[] icons = {R.drawable.ic_add_cap,R.drawable.ic_capacitaciones};
//
//        for (int i = 0; i < tbLayout.getTabCount(); i++) {
//            tbLayout.getTabAt(i).setIcon(icons[i]);
//        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TiposDataViewModel mViewModel = ViewModelProviders.of(this).get(TiposDataViewModel.class);
        capViewModel = ViewModelProviders.of(this).get(CapacitacionesViewModel.class);

        listData = new ArrayList<>();
        listAdapter = new CargosMiniAdapter(getActivity(),listData,this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerViewCargos.setLayoutManager(layoutManager);
        recyclerViewCargos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCargos.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        recyclerViewCargos.setAdapter(listAdapter);

        mDisposable.add(mViewModel.getTDbyId(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                   @Override
                                   public void onNext(List<TiposData> tiposData) {
                                       ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner,tiposData);
                                       spLugar.setAdapter(arrayAdapter);
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

        mDisposable.add(mViewModel.getTDCKbyId(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TiposDataCk>>(){
                    @Override
                    public void onNext(List<TiposDataCk> data) {
                        listData.clear();
                        listData.addAll(data);
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

        btnHrIni.setOnClickListener(this);
        btnHrFin.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        SigecapApplication.setActionBarTitle(getActivity(),"Capacitaciones");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnHrIni:
                ObtenerHora(1);
                break;
            case R.id.btnHrFin:
                ObtenerHora(2);
                break;
            case R.id.btnGuadarCap:
                SaveCapacitacion();
                break;
        }
    }

    @Override
    public void onPress(TiposDataCk data) {

    }

    private void ObtenerHora(int pos){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (timePicker, hr, mn) -> {
            int hrf=0,minf = 0;

            if (pos == 1)
            {
                datetimeInicio.set(Calendar.HOUR_OF_DAY, hr);
                datetimeInicio.set(Calendar.MINUTE, mn);

                hrf = datetimeInicio.get(Calendar.HOUR);
                minf = datetimeInicio.get(Calendar.MINUTE);
            }

            else if (pos == 2){
                datetimeFin.set(Calendar.HOUR_OF_DAY, hr);
                datetimeFin.set(Calendar.MINUTE, mn);

                hrf = datetimeFin.get(Calendar.HOUR);
                minf = datetimeFin.get(Calendar.MINUTE);
            }

            String hora_format = (hrf < 9) ? String.valueOf(CERO + hrf): String.valueOf(hrf);
            String minuto_format = (minf < 9) ? String.valueOf(CERO + minf): String.valueOf(minf);

            String AM_PM;
            if(hr < 12) {
                AM_PM = "a.m.";
            } else {
                AM_PM = "p.m.";
            }
            if (pos == 1)
            {
                txtHrInicio.setText(hora_format + ":" + minuto_format + " " + AM_PM);
            }
            else if (pos == 2){
                txtHrFin.setText(hora_format + ":" + minuto_format + " " + AM_PM);
            }

            long difference = datetimeFin.getTimeInMillis() - datetimeInicio.getTimeInMillis();

            int minutos = (int) (difference/ (1000*60));

            //FIJAR DURACIÓN
            txtDuracion.setText(minutos + " minutos");
        }, hora, minuto, false);

        timePickerDialog.show();
    }

    private void SaveCapacitacion(){
        btnGuardar.setEnabled(false);

        String seminario = txtSeminario.getText().toString().trim();
        String temas = StringUtils.join(txtTemas.getTags().toArray(),"-");
        String horaIni = txtHrInicio.getText().toString().trim();
        String horaFin = txtHrFin.getText().toString().trim();
        int id_lugar = ((TiposData)spLugar.getSelectedItem()).id;
        List< TiposDataCk > carList = listAdapter.getCargosListChecked();

        if (!seminario.isEmpty()
                && temas!= null
                    && !horaIni.isEmpty()
                        && !horaFin.isEmpty()
                            && carList.size() > 0
                                && id_lugar > 0)
        {
           Capacitaciones cap = new Capacitaciones();
           cap.nombre_seminario = seminario;
           cap.temas = temas;
           cap.hora_inicio = datetimeInicio.getTime();
           cap.hora_fin = datetimeFin.getTime();
           cap.id_lugar = id_lugar;

           SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
           cap.fecha = sdf.format(datetimeInicio.getTime());


            mDisposable.add(capViewModel.saveCapacitacion(cap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong->{
                        //GUARDAR LOS CARGOS CON EL ID DE LA CAPACITACIÓN
                        List<CapacitacionesCargos> capcar = new ArrayList<>();
                        for(TiposDataCk tdc: carList){

                            CapacitacionesCargos cc = new CapacitacionesCargos();
                            cc.id_capacitacion = Integer.parseInt(aLong.toString());
                            cc.id_cargo = tdc.id;

                            capcar.add(cc);
                        }

                        mDisposable.add(capViewModel.insertAllCapCar(capcar)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(()->{

                                    Snackbar.make(getView(), "Capacitación creada correctamente...", Snackbar.LENGTH_LONG)
                                            .setAction("Aceptar", null).show();

                                    getActivity().onBackPressed();

                                }, throwable -> {
                                    Snackbar.make(getView(), "Error al crear capacitación, inténtelo de nuevo (capcar)...", Snackbar.LENGTH_LONG)
                                            .setAction("Aceptar", null).show();
                                }));

                    }, throwable -> {
                        Snackbar.make(getView(), "Error al crear capacitación, inténtelo de nuevo (cap)...", Snackbar.LENGTH_LONG)
                                .setAction("Aceptar", null).show();
                    }));
        }
        else{
            Snackbar.make(getView(), "Por favor no dejar campos en blanco", Snackbar.LENGTH_LONG)
                    .setAction("Aceptar", null).show();
        }

        btnGuardar.setEnabled(true);

    }

    public static class Informacion1Fragment extends Fragment implements View.OnClickListener, CargosMiniAdapter.CmiAdapterOnPressHandler {

        private CompositeDisposable mDisposable = new CompositeDisposable();

        private static final String CERO = "0";
        private Unbinder unbinderI1;

        @BindView(R.id.recCargos)
        RecyclerView recyclerViewCargos;
        @BindView(R.id.spLugar)
        Spinner spLugar;
        @BindView(R.id.txtTemas)
        TagsEditText txtTemas;
        @BindView(R.id.txtHrInicio)
        BootstrapEditText txtHrInicio;
        @BindView(R.id.txtHrFin)
        BootstrapEditText txtHrFin;
        @BindView(R.id.btnHrIni)
        ImageButton btnHrIni;
        @BindView(R.id.btnHrFin)
        ImageButton btnHrFin;
        @BindView(R.id.txtDuracion)
        BootstrapEditText txtDuracion;

        private List<TiposDataCk> listData;
        CargosMiniAdapter listAdapter;
        Calendar datetimeInicio = Calendar.getInstance();
        Calendar datetimeFin = Calendar.getInstance();

        //Calendario para obtener fecha & hora
        public final Calendar c = Calendar.getInstance();
        //Hora
        final int hora = c.get(Calendar.HOUR);
        final int minuto = c.get(Calendar.MINUTE);


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_add_capacitacion, container, false);
            unbinderI1 = ButterKnife.bind(this, rootView);
            return rootView;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            TiposDataViewModel mViewModel = ViewModelProviders.of(this).get(TiposDataViewModel.class);

            listData = new ArrayList<>();
            listAdapter = new CargosMiniAdapter(getActivity(),listData,this);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
            recyclerViewCargos.setLayoutManager(layoutManager);
            recyclerViewCargos.setItemAnimator(new DefaultItemAnimator());
            recyclerViewCargos.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
            recyclerViewCargos.setAdapter(listAdapter);

            mDisposable.add(mViewModel.getTDbyId(3)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposData>>(){
                                       @Override
                                       public void onNext(List<TiposData> tiposData) {
                                           ArrayAdapter<TiposData> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.spinner,tiposData);
                                           spLugar.setAdapter(arrayAdapter);
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

            mDisposable.add(mViewModel.getTDCKbyId(2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<List<TiposDataCk>>(){
                        @Override
                        public void onNext(List<TiposDataCk> data) {
                            listData.clear();
                            listData.addAll(data);
                            listAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    }));

            btnHrIni.setOnClickListener(this);
            btnHrFin.setOnClickListener(this);
        }

        private void ObtenerHora(int pos){
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (timePicker, hr, mn) -> {
                int hrf=0,minf = 0;

                if (pos == 1)
                {
                    datetimeInicio.set(Calendar.HOUR_OF_DAY, hr);
                    datetimeInicio.set(Calendar.MINUTE, mn);

                    hrf = datetimeInicio.get(Calendar.HOUR);
                    minf = datetimeInicio.get(Calendar.MINUTE);
                }

                else if (pos == 2){
                    datetimeFin.set(Calendar.HOUR_OF_DAY, hr);
                    datetimeFin.set(Calendar.MINUTE, mn);

                    hrf = datetimeFin.get(Calendar.HOUR);
                    minf = datetimeFin.get(Calendar.MINUTE);
                }

                String hora_format = (hrf < 9) ? String.valueOf(CERO + hrf): String.valueOf(hrf);
                String minuto_format = (minf < 9) ? String.valueOf(CERO + minf): String.valueOf(minf);

                String AM_PM;
                if(hr < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                if (pos == 1)
                {
                    txtHrInicio.setText(hora_format + ":" + minuto_format + " " + AM_PM);
                }
                else if (pos == 2){
                    txtHrFin.setText(hora_format + ":" + minuto_format + " " + AM_PM);
                }

                long difference = datetimeFin.getTimeInMillis() - datetimeInicio.getTimeInMillis();

                int minutos = (int) (difference/ (1000*60));

                //FIJAR DURACIÓN
                txtDuracion.setText(minutos + " minutos");
            }, hora, minuto, false);

            timePickerDialog.show();
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btnHrIni:
                    ObtenerHora(1);
                    break;
                case R.id.btnHrFin:
                    ObtenerHora(2);
                    break;
            }
        }

        @Override
        public void onStop() {
            super.onStop();

            mDisposable.clear();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            unbinderI1.unbind();
        }

        @Override
        public void onPress(TiposDataCk data) {

        }
    }

    public static class Informacion2Fragment extends Fragment implements View.OnClickListener {



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_add_capacitacion2, container, false);

            return rootView;
        }

        @Override
        public void onClick(View v)
        {
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment f;
            switch (position)
            {
                case 0:
                    f = new Informacion1Fragment();
                    break;
                case 1:
                    f = new Informacion2Fragment();
                    break;
                default:
                    f = new Informacion1Fragment();
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "INFORMACIÓN BÁSICA";
                case 1:
                    return "RESUMEN Y FINAL";
            }
            return null;
        }
    }
}
