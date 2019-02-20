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

    @Query("SELECT c.id,c.nombre_seminario nombre,c.temas,c.hora_fin hora_fin,c.hora_inicio hora_inicio,c.fecha,            \n" +
            "           (SELECT count(a.id) from asistentes a inner join capacitaciones_cargos cc on a.id_cargo = cc.id_cargo) asistentes             \n" +
            "            from capacitaciones c \n" +
            "            INNER JOIN capacitaciones_cargos cc \n" +
            "            on c.id = cc.id_capacitacion \n" +
            "            inner join tipos_data td\n" +
            "            on cc.id_cargo = td.id            \n" +
            "            group by c.id\n" +
            "            order by date(c.fecha)")
    Observable<List<CapacitacionesData>> getCapacitaciones();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertCapacitacion(Capacitaciones capacitaciones);
}
