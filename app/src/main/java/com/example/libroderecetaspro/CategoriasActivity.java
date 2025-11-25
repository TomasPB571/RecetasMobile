package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;

public class CategoriasActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private CategoriaAdapter adapter;
    private DatabaseHelper db;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        recycler = findViewById(R.id.recyclerCategorias);
        btnAgregar = findViewById(R.id.btnAgregarCategoria);

        db = new DatabaseHelper(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Categoria> lista = db.obtenerCategorias();
        adapter = new CategoriaAdapter(lista, this);
        recycler.setAdapter(adapter);

        btnAgregar.setOnClickListener(v -> startActivity(new Intent(this, AgregarCategoriaActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.actualizarLista(db.obtenerCategorias());
    }
}
