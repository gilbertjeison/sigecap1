package com.develop.perlasoft.sigecap;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.perlasoft.adapters.AsistentesListAdapter;
import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.model.AsistentesData;
import com.develop.perlasoft.viewmodels.AsistentesViewModel;
import com.develop.perlasoft.viewmodels.TiposDataViewModel;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsistentesFragment extends Fragment implements View.OnClickListener, AsistentesListAdapter.AsistentesAdapterOnLongPressHandler {

    Unbinder unbinder;
    private static AsistentesViewModel mAsViewModel;
    public static final CompositeDisposable mDisposable = new CompositeDisposable();
    private static final int READ_REQUEST_CODE = 42;
    private static final int DELETE_REQUEST_CODE = 41;

    private String TAG = AsistentesFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;
    @BindView(R.id.fab_import)
    FloatingActionButton fab_import;
    @BindView(R.id.empty_view)
    TextView txtNoData;
    @BindView(R.id.fabMenuAsistentes)
    FloatingActionMenu fabMenu;

    private List<AsistentesData> listAs;
    AsistentesListAdapter listAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.asistentes_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        fab_add.setOnClickListener(this);
        fab_import.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();

        mDisposable.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAsViewModel = ViewModelProviders.of(this).get(AsistentesViewModel.class);

        //LLENAR LA LISTA DE ASISTENTES
        listAs = new ArrayList<>();
        listAdapter = new AsistentesListAdapter(getActivity(),listAs,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(listAdapter);

        mDisposable.add(mAsViewModel.getAsistentes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<AsistentesData>>(){
                    @Override
                    public void onNext(List<AsistentesData> asistentesData) {
                        listAs.clear();
                        listAs.addAll(asistentesData);
                        listAdapter.notifyDataSetChanged();

                        if (listAs.size()>0)
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

    private void openExcelFile()
    {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.ms-excel");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    private void importData(Uri uri) throws IOException {
        //TODO:IMPLEMENTAR DIALOGO CON PROGRESO DE IMPORTACIÓN
        List<Asistentes> listToAdd = new ArrayList<>();
        if (uri != null)
        {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            // Create a POI File System object
            POIFSFileSystem mFile = new POIFSFileSystem(inputStream);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(mFile);

            // Get the first sheet from workbook
            HSSFSheet myShhet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = myShhet.rowIterator();
            rowIter.next();
            int rowNo = 1;
            while (rowIter.hasNext()){
                Asistentes asistentes = new Asistentes();
                HSSFRow row = (HSSFRow)rowIter.next();
                if (rowNo != 0){
                    Iterator<Cell> celIter = row.iterator();
                    int colNo = 0;
                    while (celIter.hasNext()){
                        HSSFCell cell = (HSSFCell)celIter.next();
                        switch (colNo)
                        {
                            case 0:
                                asistentes.cedula = Integer.parseInt(cell.toString().replace(".0",""));
                                break;
                            case 1:
                                asistentes.nombres = cell.toString();
                                break;
                            case 2:
                                //TODO:CODIGO CORRESPONDIENTE A CADA CARGO
                                asistentes.id_cargo = 4;
                                break;
                            case 3:
                                //TODO:CODIGO CORRESPONDIENTE A CADA AREA
                                asistentes.id_area = 1;
                                break;
                        }

                        colNo++;
                    }
                }
                rowNo++;
                listToAdd.add(asistentes);
            }
        }

        //AGREGAR A LA BASE DE DATOS
        if (listToAdd.size()>0)
        {
            mDisposable.add(mAsViewModel.saveAllAsistente(listToAdd)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(()->{
                        //CERRAR MENÚ BUTTON
                        Snackbar.make(getView(), "Asistentes importados correctamente...", Snackbar.LENGTH_LONG)
                                .setAction("Aceptar", null).show();

                        onActivityCreated(null);
                    }, throwable -> {
                        Snackbar.make(getView(), "Error al importar asistentes, inténtelo de nuevo..."+throwable.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Aceptar", null).show();
                    }));
        }

        fabMenu.close(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
           Uri uri;
           if (data != null){
               uri = data.getData();
               try {
                   importData(uri);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       if (requestCode == DELETE_REQUEST_CODE){
           if (Activity.RESULT_OK == resultCode)
           {
               Toast.makeText(getActivity(), "Eliminando!", Toast.LENGTH_LONG).show();
           }
           else if (Activity.RESULT_CANCELED == resultCode)
           {
               Toast.makeText(getActivity(), "Operación cancelada!", Toast.LENGTH_LONG).show();
           }
       }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab_add:
                AddAsistenteFragment fragment = new AddAsistenteFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.mainFrame, fragment)
                        .addToBackStack("AsistentesFragment")
                        .commit();
                SigecapApplication.setActionBarTitle(getActivity(),"Nuevo asistente");
                break;
            case R.id.fab_import:
                openExcelFile();
                break;
        }
    }

    @Override
    public void onLongPress(AsistentesData asistentesData) {
        MenuDialog menuDialog = new MenuDialog();
        menuDialog.asistentesData = asistentesData;
        menuDialog.show(getFragmentManager(),TAG);
    }

    public static class MenuDialog extends DialogFragment
    {
        public  AsistentesData asistentesData;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Opciones para "+asistentesData.nombres)
                .setItems(R.array.menu_asistentes, (dialog, which) -> {
                    // The 'which' argument contains the index position
                    // of the selected item
                    switch (which)
                    {
                        case 0:
                            AddAsistenteFragment fragment = AddAsistenteFragment.newInstance(asistentesData);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.mainFrame, fragment)
                                    .addToBackStack("AsistentesFragment")
                                    .commit();
                            SigecapApplication.setActionBarTitle(getActivity(),"Editar asistente");
                            break;
                        case 1:
                            ConfirmDialog confirmDialog = new ConfirmDialog();
                            confirmDialog.asistentesData = asistentesData;
                            confirmDialog.setTargetFragment(this, DELETE_REQUEST_CODE);
                            confirmDialog.show(getFragmentManager(),"");
                            break;
                    }

                });
            return builder.create();
        }
    }

    public static class ConfirmDialog extends DialogFragment
    {
        public  AsistentesData asistentesData;
        Asistentes asistentes = new Asistentes();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Eliminando asistente... ")
                    .setMessage("Desea realmente eliminar a "+asistentesData.nombres)
                    .setPositiveButton("Si", (dialogInterface, i) -> {

                        asistentes.id = asistentesData.id;
                        asistentes.id_area = asistentesData.id_area;
                        asistentes.id_cargo = asistentesData.id_cargo;
                        asistentes.nombres = asistentesData.nombres;
                        asistentes.cedula = asistentesData.cedula;

                        mDisposable.add(mAsViewModel.deleteAsistentes(asistentes)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(()->{
//                                    Snackbar.make, "Asistente eliminado correctamente...", Snackbar.LENGTH_LONG)
//                                            .setAction("Aceptar", null).show();


                                }, throwable -> {
//                                    Snackbar.make(getView(), "Error al eliminado asistente, inténtelo de nuevo...", Snackbar.LENGTH_LONG)
//                                            .setAction("Aceptar", null).show();
                                }));
                    })
            .setNegativeButton("No", (dialogInterface, i) -> {


            });
            return builder.create();
        }
    }
}
