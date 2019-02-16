package com.develop.perlasoft.dao;

import com.develop.perlasoft.entities.Capacitaciones;
import com.develop.perlasoft.model.CapacitacionesData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface CapacitacionesDAO {

    @Query("SELECT c.id,c.nombre_seminario,c.temas,c.hora_fin - c.hora_inicio duracion,count(cc.id) " +
            "from capacitaciones c " +
            "INNER JOIN capacitaciones_cargos cc on c.id = cc.id_capacitacion " +
            "INNER JOIN tipos_data d on d.id = cc.id_cargo " +
            "order by date(c.fecha)")
    Observable<List<CapacitacionesData>> getCapacitaciones();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertCapacitacion(Capacitaciones capacitaciones);
}
