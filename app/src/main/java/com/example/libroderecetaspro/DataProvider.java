package com.example.libroderecetaspro;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private static DatabaseHelper db;

    // Constructor privado para evitar instancias
    private DataProvider() {}

    // Inicializar una sola vez
    public static void init(Context context) {
        if (db == null) {
            db = new DatabaseHelper(context.getApplicationContext());
        }
    }

    // ============================================
    //                 RECETAS
    // ============================================
    public static List<Receta> getRecetas() {
        return db.obtenerRecetas();
    }

    public static long addReceta(Receta r) {
        return db.insertarReceta(r);
    }

    public static int updateReceta(int id, String nombre, String tipo, String descripcion) {
        return db.actualizarReceta(id, nombre, tipo, descripcion);
    }

    public static int deleteReceta(int id) {
        return db.eliminarReceta(id);
    }

    public static boolean recetaExiste(String nombre) {
        return db.existeReceta(nombre);
    }

    // Filtrar por tipo
    public static List<Receta> filtrarPorTipo(String tipo, List<Receta> lista) {
        List<Receta> filtradas = new ArrayList<>();

        for (Receta r : lista) {
            if (r.getTipo() != null && r.getTipo().equalsIgnoreCase(tipo)) {
                filtradas.add(r);
            }
        }

        return filtradas;
    }

    // Vacío por ahora
    public static void cargarRecetasAPI() { }


    // ============================================
    //                CATEGORÍAS
    // ============================================
    public static List<Categoria> getCategorias() {
        return db.obtenerCategorias();
    }

    public static long addCategoria(Categoria c) {
        return db.insertarCategoria(c);
    }

    public static int updateCategoria(int id, String nombre) {
        return db.actualizarCategoria(id, nombre);
    }

    public static int deleteCategoria(int id) {
        return db.eliminarCategoria(id);
    }
}
