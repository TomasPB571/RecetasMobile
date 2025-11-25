package com.example.libroderecetaspro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.ViewHolder> {

    private List<Receta> lista;
    private List<Receta> listaOriginal;
    private Context context;

    public RecetaAdapter(List<Receta> lista, Context context) {
        this.lista = lista;
        this.context = context;

        // Copia para poder filtrar sin perder los datos
        this.listaOriginal = new ArrayList<>(lista);
    }

    public void actualizarLista(List<Receta> nuevaLista) {
        this.lista = nuevaLista;
        this.listaOriginal = new ArrayList<>(nuevaLista);
        notifyDataSetChanged();
    }

    // 🔥 MÉTODO NECESARIO PARA EL BUSCADOR
    public void filtrar(String texto) {
        texto = texto.toLowerCase();

        lista.clear();

        if (texto.isEmpty()) {
            lista.addAll(listaOriginal);
        } else {
            for (Receta r : listaOriginal) {
                if (r.getNombre().toLowerCase().contains(texto) ||
                        r.getTipo().toLowerCase().contains(texto) ||
                        r.getDescripcion().toLowerCase().contains(texto)) {

                    lista.add(r);
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receta receta = lista.get(position);

        holder.txtNombre.setText(receta.getNombre());
        holder.txtTipo.setText(receta.getTipo());
        holder.txtDescripcion.setText(receta.getDescripcion());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleRecetaActivity.class);
            intent.putExtra("id", receta.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtTipo, txtDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
        }
    }
}
