package com.tripleM.sendmeal_lab01.retrofit;

import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface PlatoDAORest {
    @GET("plato/{idPlato}")
    Call<Plato> getPlato(@Path("idPlato") String idPlato);

    @GET("plato/")
    Call<List<Plato>> getPlatoList();

    @POST("plato/")
    Call<Plato> createPlato(@Body Plato plato);
}