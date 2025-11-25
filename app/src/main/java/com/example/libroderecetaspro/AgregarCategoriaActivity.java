package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarCategoriaActivity extends AppCompatActivity {

    private EditText edtNombre;
    private Button btnGuardar;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        db = new DatabaseHelper(this);

        edtNombre = findViewById(R.id.edtNombreCategoria);
        btnGuardar = findViewById(R.id.btnGuardarCategoria);

        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Escribe un nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            long id = db.insertarCategoria(new Categoria(nombre));
            if (id > 0) {
                Toast.makeText(this, "Categoría guardada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
