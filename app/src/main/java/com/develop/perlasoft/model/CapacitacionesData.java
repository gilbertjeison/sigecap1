package com.develop.perlasoft.model;

import com.develop.perlasoft.database.convertes.DateConverter;

import java.util.Date;

import androidx.room.Ignore;
import androidx.room.TypeConverters;

public class CapacitacionesData {
    public int id;
    public String nombre;
    public String temas;
    @TypeConverters(DateConverter.class)
    public Date hora_inicio;
    @TypeConverters(DateConverter.class)
    public Date hora_fin;
    @Ignore
    public int duracion;
    public int asistentes;
    public String fecha;
}
