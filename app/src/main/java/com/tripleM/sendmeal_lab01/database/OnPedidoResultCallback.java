package com.tripleM.sendmeal_lab01.database;

import com.tripleM.sendmeal_lab01.model.Pedido;

import java.util.List;

interface OnPedidoResultCallback {
    void onResultPedido(List<Pedido> pedido);
    void onResultIdPedido(Pedido pedido);
}