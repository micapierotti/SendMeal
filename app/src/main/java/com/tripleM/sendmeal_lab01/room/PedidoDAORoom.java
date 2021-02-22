package com.tripleM.sendmeal_lab01.room;

import androidx.room.*;
import com.tripleM.sendmeal_lab01.model.*;

import java.util.List;

@Dao
public interface PedidoDAORoom {
    @Insert
    void insertar(Pedido pedido);

    @Delete
    void borrar(Pedido pedido);

    @Update
    void actualizar(Pedido pedido);
/*
    @Query("SELECT * FROM pedido WHERE id = :id LIMIT 1")
    Pedido buscar(String id);
*/
    @Query("SELECT * FROM pedido")
    List<Pedido> buscarTodos();

}
