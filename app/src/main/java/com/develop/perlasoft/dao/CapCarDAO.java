package com.develop.perlasoft.dao;

import com.develop.perlasoft.entities.CapacitacionesCargos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CapCarDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllCapCar(List<CapacitacionesCargos> capcar);
}
