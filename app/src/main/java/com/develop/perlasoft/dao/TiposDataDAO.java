package com.develop.perlasoft.dao;

import com.develop.perlasoft.entities.TiposData;
import com.develop.perlasoft.model.TiposDataCk;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;

@Dao
public interface TiposDataDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTipoData(List<TiposData> tiposData);

    @Query("select * from tipos_data where id_tipo = :id_tipo")
    Observable<List<TiposData>> getTiposDataById(int id_tipo);

    @Query("select * from tipos_data where id_tipo = :id_tipo")
    Observable<List<TiposDataCk>> getTiposDataCkById(int id_tipo);

    @Query("select * from tipos_data")
    Observable<List<TiposData>> getTiposData();
}
