package com.tripleM.sendmeal_lab01.database;

import android.os.AsyncTask;

import com.tripleM.sendmeal_lab01.model.Plato;

import java.util.List;

public class BuscarPlatoById extends AsyncTask<String, Void, Plato> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatoById(PlatoDao platoDao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected Plato doInBackground(String... string) {
        Plato plato = dao.buscar(string[0]);
        return plato;
    }

    @Override
    protected void onPostExecute(Plato plato) {
        super.onPostExecute(plato);
        callback.onResultId(plato);
    }
}
