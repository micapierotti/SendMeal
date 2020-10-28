package com.tripleM.sendmeal_lab01.retrofit;

import android.os.Handler;
import android.util.Log;

import com.tripleM.sendmeal_lab01.model.Pedido;
import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepositoryService {
    PedidoService pedidoService;

    public PedidoRepositoryService(){
        pedidoService = ApiBuilder.getInstance().getPedidoRest();
    }

    public void crearPedido(List<Plato> listaPlatos, Double total, Usuario usuario, final Handler h){
        Pedido pedido = new Pedido(null,listaPlatos,total,usuario);
        Call<Pedido> callPedidos = pedidoService.createPedido(pedido);
        callPedidos.enqueue(
                new Callback<Pedido>() {
                    @Override
                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                        if (response.code() == 200) {
                            Log.d("DEBUG", "Returno Exitoso");
                        }
                    }

                    @Override
                    public void onFailure(Call<Pedido> call, Throwable t) {
                        Log.d("DEBUG", "Returno Fallido");
                    }
                }
        );

    }
}
