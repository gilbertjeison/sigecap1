package com.develop.perlasoft.database;

import android.content.Context;

import com.develop.perlasoft.application.AppExecutors;
import com.develop.perlasoft.dao.AsistentesDAO;
import com.develop.perlasoft.dao.CapCarDAO;
import com.develop.perlasoft.dao.CapacitacionesDAO;
import com.develop.perlasoft.dao.TiposDAO;
import com.develop.perlasoft.dao.TiposDataDAO;
import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.entities.CapacitacionesCargos;
import com.develop.perlasoft.entities.Eventos;
import com.develop.perlasoft.entities.Tipos;
import com.develop.perlasoft.entities.TiposData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
    entities =
        {
            Asistentes.class,
            Capacitaciones.class,
            CapacitacionesCargos.class,
            Eventos.class,
            Tipos.class,
            TiposData.class
        },
    version = 1, exportSchema = false)
public abstract class SigecapDB extends RoomDatabase {

    private static SigecapDB sInstance;
    public static final String NAME_DB = "sigecap_db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract AsistentesDAO getAsistentesDao();
    public abstract CapacitacionesDAO getCapacitacionesDao();
    public abstract CapCarDAO getCapCapDao();
    public abstract TiposDAO getTiposDao();
    public abstract TiposDataDAO getTiposDataDao();


    public static SigecapDB getInstance(final Context context, final AppExecutors appExecutors)
    {
        if (sInstance == null)
        {
            synchronized (SigecapDB.class)
            {
                if (sInstance == null)
                {
                    sInstance = buildDatabase(context,appExecutors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static SigecapDB buildDatabase(final Context context, final AppExecutors appExecutors)
    {
        return Room.databaseBuilder(context, SigecapDB.class, NAME_DB)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        appExecutors.getmDiskIO().execute(()->
                        {
                            SigecapDB sigecapDB = SigecapDB.getInstance(context,appExecutors);
                            List<Tipos> tipos = DataGenerator.generateTipos();
                            List<TiposData> tiposData = DataGenerator.generateTiposData();

                            insertData(sigecapDB,tipos,tiposData);

                        });
                    }
                })
                .build();
    }

    private static void insertData(final SigecapDB sigecapDB, final List<Tipos> tipos, final List<TiposData> tiposData)
    {
        sigecapDB.runInTransaction(()->
        {
            sigecapDB.getTiposDao().insertAll(tipos);
            sigecapDB.getTiposDataDao().insertTipoData(tiposData);
        });
    }

    private void updateDatabaseCreated(final Context context)
    {
        if (context.getDatabasePath(NAME_DB).exists())
        {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated()
    {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDabaseCreated()
    {
        return  mIsDatabaseCreated;
    }
}
