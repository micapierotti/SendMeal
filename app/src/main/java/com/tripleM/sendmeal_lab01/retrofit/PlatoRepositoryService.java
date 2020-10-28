package com.tripleM.sendmeal_lab01.retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepositoryService {

    PlatoService platoService;

    public PlatoRepositoryService(){
        platoService = ApiBuilder.getInstance().getPlatoRest();
    }

    public void crearPlato(String titulo, String descripcion, Double precio, Integer calorias, final Handler h){
        Plato plato = new Plato(null,titulo,descripcion,precio,calorias);
        Call<Plato> callPlatos = platoService.createPlato(plato);
        callPlatos.enqueue(
                new Callback<Plato>() {
                    @Override
                    public void onResponse(Call<Plato> call, Response<Plato> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<Plato> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );

    }

    public void listarPlato(final Handler h){
        Call<List<Plato>> callPlatos = platoService.getPlatoList();
        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );
    }

}
