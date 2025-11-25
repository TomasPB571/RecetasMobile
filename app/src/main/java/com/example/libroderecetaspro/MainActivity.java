package com.example.libroderecetaspro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecetaAdapter adapter;
    private Spinner spinnerFiltro;
    private SearchView searchView;
    private Button btnIrAgregar;
    private Button btnIrCategorias; // Nuevo botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias de vistas
        searchView = findViewById(R.id.searchReceta);
        spinnerFiltro = findViewById(R.id.spinnerFiltro);
        recyclerView = findViewById(R.id.recyclerRecetas);
        btnIrAgregar = findViewById(R.id.btnIrAgregar);
        btnIrCategorias = findViewById(R.id.btnIrCategorias); // Referencia al nuevo botón

        // Inicializar DataProvider
        DataProvider.init(this);

        // Cargar recetas desde API (vacío pero evita errores)
        DataProvider.cargarRecetasAPI();

        // Lista inicial
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Receta> recetas = DataProvider.getRecetas();

        adapter = new RecetaAdapter(recetas, this);
        recyclerView.setAdapter(adapter);

        // Opciones del spinner
        String[] opciones = {"Todos", "Desayuno", "Almuerzo", "Cena", "Postre"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                opciones
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(spinnerAdapter);

        // Evento del spinner
        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String tipo = parent.getItemAtPosition(position).toString();
                List<Receta> todas = DataProvider.getRecetas();

                if (tipo.equals("Todos")) {
                    adapter.actualizarLista(todas);
                } else {
                    List<Receta> filtradas = DataProvider.filtrarPorTipo(tipo, todas);
                    adapter.actualizarLista(filtradas);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Buscador
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String texto) {
                adapter.filtrar(texto);
                return true;
            }
        });

        // Botón para abrir pantalla de agregar receta
        btnIrAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarRecetaActivity.class);
            startActivity(intent);
        });

        // Botón para abrir pantalla de categorías
        btnIrCategorias.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategoriasActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar lista al volver
        adapter.actualizarLista(DataProvider.getRecetas());
    }
}
