package com.tripleM.sendmeal_lab01.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tripleM.sendmeal_lab01.Firebase;
import com.tripleM.sendmeal_lab01.ListaItemActivity;
import com.tripleM.sendmeal_lab01.R;
import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.List;

public class PlatoRecyclerAdapter extends RecyclerView.Adapter<PlatoRecyclerAdapter.PlatoViewHolder>{

    private List<Plato> mDataset;
    private AppCompatActivity activity;
    private Integer actAnterior;
    private FirebaseStorage storage;

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
        Button btnSeleccionarActualizar,btnBorrar;

        public PlatoViewHolder(@NonNull View v) {
            super(v);
            card = v.findViewById(R.id.cardView);
            titulo = v.findViewById(R.id.tituloPlato);
            precio = v.findViewById(R.id.precioPlato);
            imgPlato = v.findViewById(R.id.imagenPlato);
            btnSeleccionarActualizar = v.findViewById(R.id.selec_actual);
            btnBorrar = v.findViewById(R.id.borrar_plato);

            if(actAnterior==1){
                btnSeleccionarActualizar.setText("Actualizar");
                btnBorrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer pos = (Integer)v.getTag();
                        Plato p= mDataset.get(pos);
                        ((ListaItemActivity) activity).borrarPlato(p, pos);
                    }
                });

                btnSeleccionarActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer pos = (Integer)v.getTag();
                        Plato p= mDataset.get(pos);
                        ((ListaItemActivity) activity).actualizarPlato(p, pos);
                    }
                });
            }
            else if(actAnterior==2){
                btnSeleccionarActualizar.setText("Seleccionar");
                btnBorrar.setVisibility(View.GONE);

                btnSeleccionarActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iresult = new Intent();
                        iresult.putExtra("nombrePlato", titulo.getText());
                        iresult.putExtra("precioPlato", precio.getText());
                        activity.setResult(Activity.RESULT_OK,iresult);
                        activity.finish();
                    }
                });
            }
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
        platoHolder.btnSeleccionarActualizar.setTag(position);
        platoHolder.btnBorrar.setTag(position);

        Plato plato = mDataset.get(position);

            platoHolder.titulo.setText(plato.getTitulo());
            platoHolder.precio.setText(" $"+plato.getPrecio().toString());

            StorageReference gsReference = storage.getReferenceFromUrl("/images/"+plato.getTitulo()+".jpg");

            final long THREE_MEGABYTE = 3 * 1024 * 1024;
             gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Exito
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                //getWindowManager().getDefaultDisplay().getMetrics(dm);

                platoHolder.imgPlato.setImageBitmap(bm);
                platoHolder.imgPlato.setMinimumHeight(dm.heightPixels);
                platoHolder.imgPlato.setMinimumWidth(dm.widthPixels);
                platoHolder.imgPlato.setImageBitmap(bm);
            }
             }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                platoHolder.imgPlato.setImageResource(R.drawable.milanesasconfritas);
            }
             });
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
