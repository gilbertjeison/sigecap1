package com.develop.perlasoft.database;

import com.develop.perlasoft.entities.Tipos;
import com.develop.perlasoft.entities.TiposData;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private static final String[] TIPOS = new String[]
    {
        "AREAS","CARGOS","SITIOS","INSTRUCTORES"
    };

    private static final String[] AREAS = new String[]
    {
        "Producción","Mantenimiento profesional","Recursos humanos"
    };

    private static final String[] CARGOS = new String[]
    {
        "Operario","Mecánico","Analista de calidad"
    };

    private static final String[] SITIOS = new String[]
    {
        "Sala puro","Sala fruco","Sala sedal"
    };

    private static final String[] INSTRUCTORES = new String[]
    {
        "Alfredo Lopera","Stewar Valencia","Natalia Ramirez"
    };


    public static List<Tipos> generateTipos()
    {
        List<Tipos> tiposList = new ArrayList<>(4);
        for (int i = 0; i < TIPOS.length; i++) {
            Tipos tipos = new Tipos();
            tipos.id = i+1;
            tipos.descripcion = TIPOS[i];

            tiposList.add(tipos);
        }

        return tiposList;
    }

    public static List<TiposData> generateTiposData()
    {
        List<TiposData> tiposList = new ArrayList<>();

        int regs = 1;

        for (int i = 0; i < AREAS.length; i++) {

            TiposData tiposData = new TiposData();
            tiposData.id = regs;
            tiposData.id_tipo = 1;
            tiposData.descripcion = AREAS[i];

            tiposList.add(tiposData);
            regs++;
        }

        for (int i = 0; i < CARGOS.length; i++) {

            TiposData tiposData = new TiposData();
            tiposData.id = regs;
            tiposData.id_tipo = 2;
            tiposData.descripcion = CARGOS[i];

            tiposList.add(tiposData);
            regs++;
        }

        for (int i = 0; i < SITIOS.length; i++) {

            TiposData tiposData = new TiposData();
            tiposData.id = regs;
            tiposData.id_tipo = 3;
            tiposData.descripcion = SITIOS[i];

            tiposList.add(tiposData);
            regs++;
        }

        for (int i = 0; i < INSTRUCTORES.length; i++) {

            TiposData tiposData = new TiposData();
            tiposData.id = regs;
            tiposData.id_tipo = 4;
            tiposData.descripcion = INSTRUCTORES[i];

            tiposList.add(tiposData);
            regs++;
        }

        return tiposList;
    }
}
