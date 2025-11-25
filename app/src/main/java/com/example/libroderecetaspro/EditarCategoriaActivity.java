package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarCategoriaActivity extends AppCompatActivity {

    private EditText edtNombre;
    private Button btnActualizar;
    private DatabaseHelper db;
    private int categoriaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        db = new DatabaseHelper(this);

        edtNombre = findViewById(R.id.edtNombreCategoriaEditar);
        btnActualizar = findViewById(R.id.btnActualizarCategoria);

        categoriaId = getIntent().getIntExtra("id", -1);
        if (categoriaId == -1) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // cargar nombre actual
        Categoria c = null;
        for (Categoria cat : db.obtenerCategorias()) {
            if (cat.getId() == categoriaId) { c = cat; break; }
        }
        if (c != null) edtNombre.setText(c.getNombre());

        btnActualizar.setOnClickListener(v -> {
            String nuevo = edtNombre.getText().toString().trim();
            if (nuevo.isEmpty()) {
                Toast.makeText(this, "Escribe un nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            int rows = db.actualizarCategoria(categoriaId, nuevo);
            if (rows > 0) {
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
