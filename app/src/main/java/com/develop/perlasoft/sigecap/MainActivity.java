package com.develop.perlasoft.sigecap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.Usuarios;
import com.develop.perlasoft.network.ApiClient;
import com.develop.perlasoft.network.ApiService;
import com.develop.perlasoft.utils.SPHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    SPHandler spHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spHandler = new SPHandler(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Obteniendo información de servidor...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView txtNombresHeader = headerView.findViewById(R.id.txtNombres);
        txtNombresHeader.setText(spHandler.getUserDetails().getNombres()+ " " + spHandler.getUserDetails().getApellidos());

        //RETROFIT SERVICE
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);

        //OBTENIENDO TODOS LOS USUARIOS
        compositeDisposable.add(
                //SUBSCRIBIR OBSERVER A OBSERVABLE
                getUsuariosObservable().
                        observeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .map(new Function<Usuarios, Usuarios>() {
                            @Override
                            public Usuarios apply(Usuarios u) throws Exception {
                                u.setNombres(u.getNombres().toUpperCase());

                                return u;
                            }
                        })
                        .subscribeWith(getUserObserver()));

        compositeDisposable.add(
                apiService.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Usuarios>>()
        {
            @Override
            public void onSuccess(List<Usuarios> usuarios) {
                for (Usuarios us : usuarios)
                {
                    Log.wtf(TAG,"Id: "+us.getId());
                    Log.wtf(TAG,"Nombres: "+us.getNombres());
                    Log.wtf(TAG,"Apellidos: "+us.getApellidos());
                    Log.wtf(TAG,"Usuario: "+us.getUsuario());
                    Log.wtf(TAG,"Pass: "+us.getPassword());
                    Log.wtf(TAG,"Rol: "+us.getId_rol());
                }
            }

            @Override
            public void onError(Throwable e) {
                e.fillInStackTrace();
                Log.e(TAG, "onError primo: " + e.getMessage());
            }
        }));

        spHandler.checkLogin();
    }

    private List<Usuarios> prepareUsers()
    {
        List<Usuarios> list = new ArrayList<>();
//        list.add(new Usuarios(1,"James",));
//        list.add(new Usuarios(2,"Ronald"));
//        list.add(new Usuarios(3,"Gonza"));

        return list;
    }

    List<Usuarios> usuarios;
    private Observable<Usuarios> getUsuariosObservable()
    {
        usuarios = prepareUsers();

        return Observable.create(new ObservableOnSubscribe<Usuarios>() {
            @Override
            public void subscribe(ObservableEmitter<Usuarios> emitter) throws Exception {
                for (Usuarios us : usuarios)
                {
                    if (!emitter.isDisposed())
                    {
                        emitter.onNext(us);
                    }
                }

                if (!emitter.isDisposed())
                {
                    emitter.onComplete();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }

    private DisposableObserver<Usuarios> getUserObserver()
    {
        return new DisposableObserver<Usuarios>() {

            @Override
            public void onNext(Usuarios u) {
                Log.wtf(TAG,"Name: "+u.getNombres());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"onError: "+ e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.wtf(TAG,"Todos los items han sido emitidos");
            }
        };
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DisplayView(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void DisplayView(int item)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        Class fragmentClass;

        switch (item)
        {
            case R.id.nav_capacitaciones:
                fragmentClass = CapacitacionesFragment.class;
                title = "Capacitaciones";
                break;
            case R.id.nav_inst:
                fragmentClass = TiposDataFragment.class;
                title = "Instructores";
                break;
            case R.id.nav_asist:
                fragmentClass = AsistentesFragment.class;
                title = "Asistentes";
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                title = "Ajustes";
                break;
            case R.id.nav_cerrar_sesion:
                fragmentClass = null;
                spHandler.logoutUser();
                break;
            default:
                fragmentClass = AsistentesFragment.class;
                break;
        }

        try
        {
            if (fragmentClass != null)
            {
                fragment = (Fragment)fragmentClass.newInstance();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (fragment != null)
        {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.mainFrame,fragment).commit();
        }

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(title);
        }
    }

}
