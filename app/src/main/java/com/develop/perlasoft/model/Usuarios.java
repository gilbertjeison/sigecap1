package com.develop.perlasoft.model;

public class Usuarios extends BaseResponse {

    int id;
    String nombres;
    String apellidos;
    String usuario;
    String password;
    int id_rol;

    public Usuarios(int id, String nombres, String apellidos, String usuario, String password, int id_rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.id_rol = id_rol;
    }

    public Usuarios() {
    }

    public Usuarios(String nombres, String apellidos, String usuario, String password, int id_rol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.id_rol = id_rol;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
