package com.lucasbazan.fondodepantalla.Apartado_informativo;

public class Informacion {

    String nombre;
    String imagen;

    public Informacion() {
    }

    public Informacion(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
