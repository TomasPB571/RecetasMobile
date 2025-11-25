package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarRecetaActivity extends AppCompatActivity {

    private EditText campoNombre, campoTipo, campoDescripcion;
    private DatabaseHelper db;
    private int recetaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_receta);

        db = new DatabaseHelper(this);

        campoNombre = findViewById(R.id.campoNombreEditar);
        campoTipo = findViewById(R.id.campoTipoEditar);
        campoDescripcion = findViewById(R.id.campoDescripcionEditar);
        Button btnActualizar = findViewById(R.id.btnActualizar);

        // Recuperar datos enviados
        recetaId = getIntent().getIntExtra("id", -1);
        String nombre = getIntent().getStringExtra("nombre");
        String tipo = getIntent().getStringExtra("tipo");
        String descripcion = getIntent().getStringExtra("descripcion");

        if (nombre != null) campoNombre.setText(nombre);
        if (tipo != null) campoTipo.setText(tipo);
        if (descripcion != null) campoDescripcion.setText(descripcion);

        // Si no hay id válido, avisar y deshabilitar guardado
        if (recetaId <= 0) {
            btnActualizar.setEnabled(false);
            Toast.makeText(this, "ID inválido. No se puede actualizar.", Toast.LENGTH_LONG).show();
            return;
        }

        btnActualizar.setOnClickListener(v -> {
            String nuevoNombre = campoNombre.getText().toString().trim();
            String nuevoTipo = campoTipo.getText().toString().trim();
            String nuevaDescripcion = campoDescripcion.getText().toString().trim();

            if (nuevoNombre.isEmpty() || nuevoTipo.isEmpty() || nuevaDescripcion.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int rows = db.actualizarReceta(recetaId, nuevoNombre, nuevoTipo, nuevaDescripcion);
            if (rows > 0) {
                Toast.makeText(this, "Receta actualizada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
