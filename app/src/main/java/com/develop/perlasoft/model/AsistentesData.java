package com.develop.perlasoft.model;

import java.io.Serializable;

public class AsistentesData implements Serializable {
    public int id;
    public int cedula;
    public String nombres;
    public int id_cargo;
    public String cargo;
    public int id_area;
    public String area;

    public AsistentesData() {
    }

    public AsistentesData(int id, int cc, String nombres, int id_cargo, String cargo, int id_area, String area) {
        this.id = id;
        this.cedula = cc;
        this.nombres = nombres;
        this.id_cargo = id_cargo;
        this.cargo = cargo;
        this.id_area = id_area;
        this.area = area;
    }
}
