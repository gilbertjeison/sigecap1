package com.develop.perlasoft.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "capacitaciones_cargos",
        foreignKeys =
                {
                        @ForeignKey(entity = Capacitaciones.class, parentColumns = "id", childColumns = "id_capacitacion"),
                        @ForeignKey(entity = TiposData.class, parentColumns = "id", childColumns = "id_cargo")
                },
        indices =
                {
                        @Index(value = "id_cargo"),
                        @Index(value = "id_capacitacion")
                }
        )
public class CapacitacionesCargos {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int id_capacitacion;
    public int id_cargo;
}
