package com.example.libroderecetaspro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    private List<Categoria> lista;
    private Context context;

    public CategoriaAdapter(List<Categoria> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    public void actualizarLista(List<Categoria> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Categoria c = lista.get(position);
        holder.txtNombre.setText(c.getNombre());

        // CLICK → editar
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, EditarCategoriaActivity.class);
            i.putExtra("id", c.getId());
            context.startActivity(i);
        });

        // LONG CLICK → eliminar
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar categoría")
                    .setMessage("¿Seguro que deseas eliminar \"" + c.getNombre() + "\"?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.eliminarCategoria(c.getId());
                        Toast.makeText(context, "Eliminada", Toast.LENGTH_SHORT).show();

                        // Recargar lista
                        lista = db.obtenerCategorias();
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreCategoria);
        }
    }
}
