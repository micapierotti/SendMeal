package com.tripleM.sendmeal_lab01.retrofit;

import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface PlatoService {
    @GET("plato/{id}")
    Call<Plato> getPlato(@Path("id") String id);

    @GET("plato/list")
    Call<List<Plato>> getPlatoList();

    @POST("plato")
    Call<Plato> createPlato(@Body Plato plato);
  /*
    Si deciden usar SendMeal-Fake-API deberán usar un body
    del tipo @Body String body. Lo cual les facilitara cumplir el formato esperado

    JSONObject bodyExample = new JSONObject();
    paramObject.put("email", "sample@gmail.com");
    paramObject.put("pass", "4384984938943");
    createPlato(bodyExample.toString())
  */

}