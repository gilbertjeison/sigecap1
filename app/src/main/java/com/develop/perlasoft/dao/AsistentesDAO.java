package com.develop.perlasoft.dao;

import com.develop.perlasoft.entities.Asistentes;
import com.develop.perlasoft.model.AsistentesData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public interface AsistentesDAO {

    @Query("SELECT a.id,a.nombres,a.cedula,a.id_area,a.id_cargo,t.descripcion cargo,d.descripcion area " +
            "from asistentes a " +
            "INNER JOIN tipos_data t on a.id_cargo = t.id " +
            "INNER JOIN tipos_data d on a.id_area = d.id " +
            "order by d.descripcion,a.nombres")
    Observable<List<AsistentesData>> getAsistentes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAsistente(Asistentes asistentes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAsistente(List<Asistentes> asistentes);

    @Update
    int updateAsistente(Asistentes asistente);

    @Delete
    int deleteAsistente(Asistentes asistente);

}
