package com.example.libroderecetaspro;

public class Receta {

    private int id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String ingredientes;
    private String preparacion;

    // Constructor para leer desde BD
    public Receta(int id, String nombre, String tipo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ingredientes = "";
        this.preparacion = "";
    }

    // Constructor para insertar desde AgregarRecetaActivity
    public Receta(String nombre, String tipo, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ingredientes = "";
        this.preparacion = "";
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public String getIngredientes() { return ingredientes; }
    public String getPreparacion() { return preparacion; }

    public void setId(int id) { this.id = id; }
    public void setTipo(String tipo) { this.tipo = tipo; } // ✅ nuevo setter
}
