package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AgregarRecetaActivity extends AppCompatActivity {

    private EditText campoNombre, campoDescripcion;
    private Spinner spinnerCategorias;
    private DatabaseHelper db;
    private List<Categoria> categorias; // lista real desde SQLite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_receta);

        db = new DatabaseHelper(this);

        campoNombre = findViewById(R.id.campoNombre);
        campoDescripcion = findViewById(R.id.campoDescripcion);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // ✅ Cargar categorías desde SQLite (NO del DataProvider)
        categorias = db.obtenerCategorias();
        if (categorias == null) {
            categorias = new ArrayList<>();
        }

        // Crear lista de nombres para el spinner
        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria c : categorias) {
            nombresCategorias.add(c.getNombre());
        }

        // Adaptador para Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                nombresCategorias
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorias.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> {
            String nombre = campoNombre.getText().toString().trim();
            String descripcion = campoDescripcion.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (categorias.isEmpty()) {
                Toast.makeText(this, "No hay categorías disponibles", Toast.LENGTH_SHORT).show();
                return;
            }

            int pos = spinnerCategorias.getSelectedItemPosition();

            Categoria categoriaSeleccionada = categorias.get(pos);

            // Crear receta con el nombre de la categoría seleccionada
            Receta receta = new Receta(nombre, categoriaSeleccionada.getNombre(), descripcion);

            long resultado = db.insertarReceta(receta);

            if (resultado > 0) {
                Toast.makeText(this, "Receta guardada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
