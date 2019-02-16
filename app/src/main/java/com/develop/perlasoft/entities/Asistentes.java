package com.develop.perlasoft.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "asistentes",
        foreignKeys = {
            @ForeignKey(entity = TiposData.class,
                        parentColumns = "id",
                        childColumns = "id_cargo"),
             @ForeignKey(entity = TiposData.class,
                        parentColumns = "id",
                        childColumns = "id_area")
            },
        indices = {@Index(value = "id_cargo"), @Index(value = "id_area")}
        )
public class Asistentes {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int cedula;

    public String nombres;

    public int id_cargo;

    public int id_area;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] huella_data;



}
