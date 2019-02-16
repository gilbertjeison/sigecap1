package com.develop.perlasoft.dao;

import com.develop.perlasoft.entities.Tipos;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface TiposDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tipos> tipos);
}
