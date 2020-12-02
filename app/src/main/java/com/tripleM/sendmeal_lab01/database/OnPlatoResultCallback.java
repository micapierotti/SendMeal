package com.tripleM.sendmeal_lab01.database;

import com.tripleM.sendmeal_lab01.model.Plato;

import java.util.List;

interface OnPlatoResultCallback {
    void onResult(List<Plato> plato);
    void onResultId(Plato plato);
}