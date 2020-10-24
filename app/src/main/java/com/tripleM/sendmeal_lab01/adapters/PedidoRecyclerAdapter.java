package com.tripleM.sendmeal_lab01.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tripleM.sendmeal_lab01.R;
import com.tripleM.sendmeal_lab01.model.Plato;

import java.util.List;

public class PedidoRecyclerAdapter extends RecyclerView.Adapter<PedidoRecyclerAdapter.PedidoViewHolder>{

    private List<Pair<String,String>> mDataset;
    private AppCompatActivity activity;

    public PedidoRecyclerAdapter(List<Pair<String,String>> myDataset, AppCompatActivity act) {
        mDataset = myDataset;
        activity = act;
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView precio;

        public PedidoViewHolder(@NonNull View v) {
            super(v);
            nombre = v.findViewById(R.id.nombrePlato);
            precio = v.findViewById(R.id.precioPlato);
        }
    }

    @NonNull
    @Override
    public PedidoRecyclerAdapter.PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_pedido, parent, false);
        PedidoViewHolder  vh = new PedidoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoRecyclerAdapter.PedidoViewHolder platoHolder, int position) {

        platoHolder.nombre.setTag(position);
        platoHolder.precio.setTag(position);
        Pair<String,String> plato = mDataset.get(position);

        platoHolder.nombre.setText(plato.first);
        platoHolder.precio.setText(plato.second);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
