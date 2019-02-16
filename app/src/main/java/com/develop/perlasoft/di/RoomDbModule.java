package com.develop.perlasoft.di;

import com.develop.perlasoft.application.AppExecutors;
import com.develop.perlasoft.application.SigecapApplication;
import com.develop.perlasoft.dao.AsistentesDAO;
import com.develop.perlasoft.database.SigecapDB;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.Module;
import dagger.Provides;

@Module(includes = AppModule.class)
public class RoomDbModule {
    private SigecapDB sigecapDB;

    public RoomDbModule(SigecapApplication sigecapApplication, AppExecutors appExecutors)
    {
        sigecapDB =Room.databaseBuilder(sigecapApplication, SigecapDB.class, SigecapDB.NAME_DB)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    SigecapDB provideRoomDatabase()
    {
        return sigecapDB;
    }

    @Singleton
    @Provides
    AsistentesDAO providesAsistentesDao(SigecapDB sigecapDB)
    {
        return sigecapDB.getAsistentesDao();
    }
}

