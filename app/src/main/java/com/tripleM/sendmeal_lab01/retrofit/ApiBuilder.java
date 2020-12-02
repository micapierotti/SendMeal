package com.tripleM.sendmeal_lab01.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private PlatoService platoService;
    private PedidoService pedidoService;
    private static ApiBuilder _INSTANCIA;

    private ApiBuilder(){ }

    public PlatoService getPlatoRest() {
        if(platoService==null) this.iniciarRetrofit();
        return platoService;
    }


    public PedidoService getPedidoRest() {
        if(pedidoService==null) this.iniciarRetrofit();
        return pedidoService;
    }

    public static ApiBuilder getInstance(){
        if(_INSTANCIA == null) {
            _INSTANCIA = new ApiBuilder();
        }
        return _INSTANCIA;
    }

    private void iniciarRetrofit(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        platoService =retrofit.create(PlatoService.class);
        pedidoService =retrofit.create(PedidoService.class);
    }

}
