package com.develop.perlasoft.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos")
public class Tipos {

    @PrimaryKey()
    public int id;

    public String descripcion;
}
