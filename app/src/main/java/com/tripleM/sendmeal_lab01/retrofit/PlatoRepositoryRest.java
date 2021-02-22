package com.tripleM.sendmeal_lab01.retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.room.AccionesDAO;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepositoryRest{

    private PlatoDAORest platoRest;

    public PlatoRepositoryRest(){
        platoRest = ApiBuilder.getInstance().getPlatoRest();
    }

    public void crearPlato(Plato plato, final Handler h){
        Call<Plato> callPlatos = platoRest.createPlato(plato);
        callPlatos.enqueue(new Callback<Plato>() {
            @Override
            public void onResponse(Call<Plato> call, Response<Plato> response) {
                Log.d("API REST","EJECUTO");
                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putParcelable("plato",response.body());
                datos.putString("accion", AccionesDAO.CREAR_PLATO.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<Plato> call, Throwable t) {
                Log.d("API REST","ERROR "+t.getMessage());
                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putString("accion", AccionesDAO.ERROR.toString());
                datos.putString("error", t.getMessage());
                msg.setData(datos);
                h.sendMessage(msg);
            }
        });
    }

    public void listarPlato(final Handler h){
        Call<List<Plato>> callPlatos = platoRest.getPlatoList();

        callPlatos.enqueue(new Callback<List<Plato>>() {
            @Override
            public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                Log.d("API REST","EJECUTO");

                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putParcelableArrayList("platos",(ArrayList<Plato>) response.body());
                datos.putString("accion", AccionesDAO.LISTAR_PLATOS.toString());
                System.out.println("Hasta aca");


                msg.setData(datos);
                h.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<Plato>> call, Throwable t) {
                Log.d("API REST","ERROR "+t.getMessage());
                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putString("accion", AccionesDAO.ERROR.toString());
                datos.putString("error", t.getMessage());
                msg.setData(datos);
                System.out.println("Hasta aca 2");
                System.out.println("Hasta aca 3");

                h.sendMessage(msg);
            }
        });
    }
}
