package com.tripleM.sendmeal_lab01.room;

import androidx.room.*;

import com.tripleM.sendmeal_lab01.model.Plato;

import java.util.List;

@Dao
public interface PlatoDAORoom {
    @Insert
    void insertar(Plato plato);

    @Delete
    void borrar(Plato plato);

    @Update
    void actualizar(Plato plato);

    @Query("SELECT * FROM plato WHERE idPlato = :id LIMIT 1")
    Plato buscar(String id);

    @Query("SELECT * FROM plato")
    List<Plato> buscarTodos();
}