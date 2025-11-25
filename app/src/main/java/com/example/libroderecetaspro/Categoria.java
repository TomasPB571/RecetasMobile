package com.example.libroderecetaspro;

public class Categoria {

    private int id;
    private String nombre;

    // Constructor para nuevas categorías (sin ID)
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Constructor completo (cuando viene de la BD)
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
