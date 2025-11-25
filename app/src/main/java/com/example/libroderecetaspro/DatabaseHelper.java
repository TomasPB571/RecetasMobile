package com.example.libroderecetaspro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "recetas_db";
    private static final int DB_VERSION = 2;

    // Tabla Usuarios
    private static final String TABLA_USUARIOS = "usuarios";
    private static final String U_ID = "id";
    private static final String U_NOMBRE = "nombre";
    private static final String U_EMAIL = "email";

    // Tabla Recetas
    private static final String TABLA_RECETAS = "recetas";
    private static final String R_ID = "id";
    private static final String R_NOMBRE = "nombre";
    private static final String R_TIPO = "tipo";
    private static final String R_DESCRIPCION = "descripcion";
    private static final String R_IMAGEN = "imagen";

    // Tabla Categorías
    private static final String TABLA_CATEGORIAS = "categorias";
    private static final String C_ID = "id";
    private static final String C_NOMBRE = "nombre";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear tabla usuarios
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLA_USUARIOS + " (" +
                        U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        U_NOMBRE + " TEXT, " +
                        U_EMAIL + " TEXT)"
        );

        // Crear tabla recetas
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLA_RECETAS + " (" +
                        R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        R_NOMBRE + " TEXT, " +
                        R_TIPO + " TEXT, " +
                        R_DESCRIPCION + " TEXT, " +
                        R_IMAGEN + " TEXT)"
        );

        // Crear tabla categorías
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLA_CATEGORIAS + " (" +
                        C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        C_NOMBRE + " TEXT UNIQUE)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS " + TABLA_CATEGORIAS + " (" +
                            C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            C_NOMBRE + " TEXT UNIQUE)"
            );
        }
    }

    // ==========================================================
    //                      CRUD USUARIOS
    // ==========================================================
    public long insertarUsuario(String nombre, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(U_NOMBRE, nombre);
        cv.put(U_EMAIL, email);
        return db.insert(TABLA_USUARIOS, null, cv);
    }

    // ==========================================================
    //                      CRUD RECETAS
    // ==========================================================
    public long insertarReceta(Receta r) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(R_NOMBRE, r.getNombre());
        cv.put(R_TIPO, r.getTipo());
        cv.put(R_DESCRIPCION, r.getDescripcion());

        // Soporte para imagen opcional
        String imagen = null;
        try {
            java.lang.reflect.Method m = r.getClass().getMethod("getImagenUrl");
            Object val = m.invoke(r);
            if (val != null) imagen = val.toString();
        } catch (Exception ignored) {}

        cv.put(R_IMAGEN, imagen);

        return db.insert(TABLA_RECETAS, null, cv);
    }

    public int actualizarReceta(int id, String nombre, String tipo, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(R_NOMBRE, nombre);
        cv.put(R_TIPO, tipo);
        cv.put(R_DESCRIPCION, descripcion);

        return db.update(TABLA_RECETAS, cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int eliminarReceta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLA_RECETAS, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Receta> obtenerRecetas() {
        List<Receta> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLA_RECETAS, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndexOrThrow(R_ID));
                String nombre = c.getString(c.getColumnIndexOrThrow(R_NOMBRE));
                String tipo = c.getString(c.getColumnIndexOrThrow(R_TIPO));
                String descripcion = c.getString(c.getColumnIndexOrThrow(R_DESCRIPCION));

                lista.add(new Receta(id, nombre, tipo, descripcion));

            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }

    // 👉 **MÉTODO AGREGADO**
    public boolean existeReceta(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + R_ID + " FROM " + TABLA_RECETAS +
                        " WHERE " + R_NOMBRE + " = ? LIMIT 1",
                new String[]{nombre}
        );

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    // ==========================================================
    //                      CRUD CATEGORÍAS
    // ==========================================================
    public long insertarCategoria(Categoria c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NOMBRE, c.getNombre());
        return db.insert(TABLA_CATEGORIAS, null, cv);
    }

    public int actualizarCategoria(int id, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NOMBRE, nombre);
        return db.update(TABLA_CATEGORIAS, cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public int eliminarCategoria(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLA_CATEGORIAS, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Categoria> obtenerCategorias() {
        List<Categoria> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLA_CATEGORIAS, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndexOrThrow(C_ID));
                String nombre = c.getString(c.getColumnIndexOrThrow(C_NOMBRE));

                lista.add(new Categoria(id, nombre));

            } while (c.moveToNext());
        }
        c.close();
        return lista;
    }
}
