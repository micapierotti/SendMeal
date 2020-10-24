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

public class PlatoRecyclerAdapter extends RecyclerView.Adapter<PlatoRecyclerAdapter.PlatoViewHolder>{

    private List<Plato> mDataset;
    private AppCompatActivity activity;
    private Integer actAnterior;

    public PlatoRecyclerAdapter(List<Plato> myDataset,AppCompatActivity act,Integer i) {
        mDataset = myDataset;
        activity = act;
        actAnterior=i;
    }

    public class PlatoViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView titulo;
        TextView precio;
        ImageView imgPlato;
        Button btnSeleccionar;

        public PlatoViewHolder(@NonNull View v) {
            super(v);
            card = v.findViewById(R.id.cardView);
            titulo = v.findViewById(R.id.tituloPlato);
            precio = v.findViewById(R.id.precioPlato);
            imgPlato = v.findViewById(R.id.imagenPlato);
            btnSeleccionar = v.findViewById(R.id.seleccionar);
            btnSeleccionar.setVisibility(View.VISIBLE);
            if(actAnterior==1)btnSeleccionar.setVisibility(View.GONE);
            else if(actAnterior==2)btnSeleccionar.setVisibility(View.VISIBLE);

            btnSeleccionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer pos = (Integer)v.getTag();
                    Intent iresult = new Intent();
                    iresult.putExtra("nombrePlato", titulo.getText());
                    iresult.putExtra("precioPlato", precio.getText());
                    activity.setResult(Activity.RESULT_OK,iresult);
                    activity.finish();
                }
            });
        }
    }

    @NonNull
    @Override
    public PlatoRecyclerAdapter.PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder  vh = new PlatoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoRecyclerAdapter.PlatoViewHolder platoHolder, int position) {

        platoHolder.titulo.setTag(position);
        platoHolder.precio.setTag(position);
        platoHolder.imgPlato.setTag(position);
        platoHolder.btnSeleccionar.setTag(position);

        Plato plato = mDataset.get(position);

        /*
        switch (plato.getGenero()){
            case SUPERHEROES:
                serieHolder.imgGenero.setImageResource(R.drawable.superheroes);
                break;
            case COMEDIA:
                serieHolder.imgGenero.setImageResource(R.drawable.comedia);
                break;
        }

        if(serie.getFavorita()){
            serieHolder.imgFav.setImageResource(android.R.drawable.star_big_on);
        } else {
            serieHolder.imgFav.setImageResource(android.R.drawable.star_big_off);
        }
        */
            platoHolder.titulo.setText(plato.getTitulo());
            platoHolder.precio.setText(" $"+plato.getPrecio().toString());
            platoHolder.imgPlato.setImageResource(R.drawable.milanesasconfritas);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
