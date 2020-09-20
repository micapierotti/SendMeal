package com.tripleM.sendmeal_lab01.dao;

import android.util.Log;
import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.ArrayList;
import java.util.List;

public class PlatoDAO {
    private static final List<Plato> platos = new ArrayList<>();

    private void iniciar(){
        if(platos.isEmpty()){
            platos.add(new Plato("Asado", "A la parrilla", 1000.0, 500));
            platos.add(new Plato("Spaguetti", "Con queso crema", 300.0, 450));
            platos.add(new Plato("Carlitos", "Con extra queso", 200.0, 300));
            platos.add(new Plato("Milanesa", "Napolitana", 200.0, 320));
            platos.add(new Plato("Pescado", "A la parrilla", 800.0, 200));
            platos.add(new Plato("Ensalada", "Con tomates, lechuga y zanahoria", 100.0, 50));
            platos.add(new Plato("Guiso", "De lentejas", 140.0, 500));
            platos.add(new Plato("Hamburguesa", "Con bacon y cheddar", 300.0, 500));
        }
    }

    public List<Plato> list(){
        iniciar();
        return this.platos;
    }

    public void add(String titulo,String descripcion,Double precio,Integer calorias){
        platos.add(new Plato(titulo, descripcion, precio, calorias));
    }

}
