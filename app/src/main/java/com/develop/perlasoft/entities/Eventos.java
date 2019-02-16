package com.develop.perlasoft.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "eventos",
        foreignKeys =
                {
                    @ForeignKey(entity = Capacitaciones.class,parentColumns = "id",childColumns = "id_capacitacion")
                },
        indices =
                {
                    @Index(value = "id_capacitacion")
                }
        )

public class Eventos {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int id_capacitacion;
    public int personas_programadas;
    public int personas_asistentes;
    public int cumplimiento;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] firma_instructor;
    public boolean sheq;
}
