package com.tripleM.sendmeal_lab01.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Entity
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private List<Plato> listaPlatos;
    private Double total;
    private Usuario usuario;
    private LatLng ubicacion;

    public Pedido() { }

    public Pedido(Long id, List<Plato> listaPlatos, Double total, Usuario usuario) {
        this.id = id;
        this.listaPlatos = listaPlatos;
        this.total = total;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Plato> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Plato> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }
}
