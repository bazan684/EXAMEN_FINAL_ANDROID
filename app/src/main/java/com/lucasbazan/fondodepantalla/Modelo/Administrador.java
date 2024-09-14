package com.lucasbazan.fondodepantalla.Modelo;

public class Administrador {

    String UID;
    String NOMBRE;
    String APELLIDO;
    String CORREO;
    String IMAGEN;
    int EDAD;

    public Administrador() {

    }

    public Administrador(String UID, String NOMBRE, String APELLIDO, String CORREO, String IMAGEN, int EDAD) {
        this.UID = UID;
        this.NOMBRE = NOMBRE;
        this.APELLIDO = APELLIDO;
        this.CORREO = CORREO;
        this.IMAGEN = IMAGEN;
        this.EDAD = EDAD;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAPELLIDO() {
        return APELLIDO;
    }

    public void setAPELLIDO(String APELLIDO) {
        this.APELLIDO = APELLIDO;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(String IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public int getEDAD() {
        return EDAD;
    }

    public void setEDAD(int EDAD) {
        this.EDAD = EDAD;
    }
}
