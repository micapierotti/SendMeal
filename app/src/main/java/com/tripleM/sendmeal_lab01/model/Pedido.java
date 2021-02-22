package com.tripleM.sendmeal_lab01.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Pedido implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public Long idPedido;
    private Double total;
    private List<Pair<String,String>> listaPlatos;
    private boolean envio;
    private String emailUsuario;
    private String ubicacion;

    public Pedido(Long idPedido, Double total, List<Pair<String, String>> listaPlatos, boolean envio, String emailUsuario, String ubicacion) {
        this.idPedido = idPedido;
        this.total = total;
        this.listaPlatos = listaPlatos;
        this.envio = envio;
        this.emailUsuario = emailUsuario;
        this.ubicacion = ubicacion;
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Pair<String, String>> getListaPlatos() {
        return listaPlatos;
    }

    public void setListaPlatos(List<Pair<String, String>> listaPlatos) {
        this.listaPlatos = listaPlatos;
    }

    public boolean isEnvio() {
        return envio;
    }

    public void setEnvio(boolean envio) {
        this.envio = envio;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", total=" + total +
                ", listaPlatos=" + listaPlatos +
                ", nombreUsuario='" + emailUsuario + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }


    protected Pedido(Parcel in) {
        if (in.readByte() == 0) {
            idPedido = null;
        } else {
            idPedido = in.readLong();
        }
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readDouble();
        }
        envio = in.readByte() != 0;
        emailUsuario = in.readString();
        ubicacion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idPedido == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idPedido);
        }
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(total);
        }
        dest.writeByte((byte) (envio ? 1 : 0));
        dest.writeString(emailUsuario);
        dest.writeString(ubicacion);
    }
}
