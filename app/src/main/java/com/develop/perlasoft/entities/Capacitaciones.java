package com.develop.perlasoft.entities;

import com.develop.perlasoft.database.convertes.DateConverter;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "capacitaciones", foreignKeys =
        {
                @ForeignKey(entity = TiposData.class,
                        parentColumns = "id",
                        childColumns = "id_lugar")
        },
        indices = {@Index(value = "id_lugar")}
    )
public class Capacitaciones {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre_seminario;
    public String temas;
    @TypeConverters(DateConverter.class)
    public Date hora_inicio;
    @TypeConverters(DateConverter.class)
    public Date hora_fin;
    public int id_lugar;
//    @TypeConverters(DateConverter.class)
    public String fecha;
}
