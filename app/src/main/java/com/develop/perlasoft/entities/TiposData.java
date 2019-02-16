package com.develop.perlasoft.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipos_data",
        foreignKeys = @ForeignKey(entity = Tipos.class,
                        parentColumns = "id",
                        childColumns = "id_tipo"),
        indices = @Index(value = "id_tipo"))
public class TiposData {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    public int id;

    public int id_tipo;
    public String descripcion;

    @NonNull
    @Override
    public String toString() {
        return descripcion;
    }
}
