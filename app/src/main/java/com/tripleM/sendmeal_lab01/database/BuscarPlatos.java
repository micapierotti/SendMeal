package com.tripleM.sendmeal_lab01.database;

import android.os.AsyncTask;

import com.tripleM.sendmeal_lab01.model.Plato;

import java.util.List;

class BuscarPlatos extends AsyncTask<String, Void, List<Plato>> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatos(PlatoDao dao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected List<Plato> doInBackground(String... strings) {
        List<Plato> platos = dao.buscarTodos();
        return platos;
    }

    @Override
    protected void onPostExecute(List<Plato> platos) {
        super.onPostExecute(platos);
        callback.onResult(platos);
    }
}