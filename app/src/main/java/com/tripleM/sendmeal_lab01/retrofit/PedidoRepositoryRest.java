package com.tripleM.sendmeal_lab01.retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tripleM.sendmeal_lab01.model.Pedido;
import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.model.Usuario;
import com.tripleM.sendmeal_lab01.room.AccionesDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepositoryRest {
    PedidoDAORest pedidoRest;

    public PedidoRepositoryRest(){
        pedidoRest = ApiBuilder.getInstance().getPedidoRest();
    }

    public void crearPedido(Pedido pedido, final Handler h){

        Call<Pedido> callPedidos = pedidoRest.createPedido(pedido);
        callPedidos.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                Log.d("API REST","EJECUTO");
                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putParcelable("pedido",response.body());
                datos.putString("accion", AccionesDAO.CREAR_PEDIDO.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
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

    public void listarPedido(final Handler h){
        Call<List<Pedido>> callPedidos = pedidoRest.getPedidoList();
        callPedidos.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                Log.d("API REST","EJECUTO");

                final Message msg = h.obtainMessage();
                final Bundle datos = new Bundle();
                datos.putParcelableArrayList("pedidos",(ArrayList<Pedido>) response.body());
                datos.putString("accion", AccionesDAO.LISTAR_PEDIDOS.toString());
                msg.setData(datos);
                h.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
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

}
