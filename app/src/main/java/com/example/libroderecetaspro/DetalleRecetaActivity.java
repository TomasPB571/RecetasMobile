package com.example.libroderecetaspro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleRecetaActivity extends AppCompatActivity {

    private TextView nombre, tipo, descripcion;
    private Button btnEditar, btnEliminar;
    private DatabaseHelper db;
    private int recetaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        db = new DatabaseHelper(this);

        nombre = findViewById(R.id.detalleNombre);
        tipo = findViewById(R.id.detalleTipo);
        descripcion = findViewById(R.id.detalleDescripcion);

        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Recuperar extras (pueden venir vacíos si algo falla)
        recetaId = getIntent().getIntExtra("id", -1);
        String n = getIntent().getStringExtra("nombre");
        String t = getIntent().getStringExtra("tipo");
        String d = getIntent().getStringExtra("descripcion");

        // Si no hay nombre/tipo/desc, mostrar aviso; pero evitar NPE
        if (n != null) nombre.setText(n);
        if (t != null) tipo.setText(t);
        if (d != null) descripcion.setText(d);

        // Si no recibimos un id válido deshabilitamos editar/eliminar
        if (recetaId <= 0) {
            btnEliminar.setEnabled(false);
            btnEditar.setEnabled(false);
            Toast.makeText(this, "Aviso: no se recibió el ID de la receta. Solo vista.", Toast.LENGTH_LONG).show();
            return;
        }

        // ELIMINAR (confirmación)
        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar receta")
                    .setMessage("¿Estás seguro de que quieres eliminar esta receta?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        int rows = db.eliminarReceta(recetaId);
                        if (rows > 0) {
                            Toast.makeText(this, "Receta eliminada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No se pudo eliminar (id: " + recetaId + ")", Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        // EDITAR - abre EditarRecetaActivity
        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarRecetaActivity.class);
            intent.putExtra("id", recetaId);
            intent.putExtra("nombre", nombre.getText().toString());
            intent.putExtra("tipo", tipo.getText().toString());
            intent.putExtra("descripcion", descripcion.getText().toString());
            startActivity(intent);
        });
    }
}
