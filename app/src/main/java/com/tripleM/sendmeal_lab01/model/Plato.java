package com.tripleM.sendmeal_lab01.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.*;

@Entity
public class Plato implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public Long idPlato;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;
    private String url="";



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    @Ignore
    public Plato(String titulo, String descripcion, Double precio, Integer calorias, String url) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.url=url;
    }

    public Plato(Long idPlato, String titulo, String descripcion, Double precio, Integer calorias, String url) {
        this.idPlato =idPlato;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
        this.url=url;
    }

    protected Plato(Parcel in) {
        if (in.readByte() == 0) {
            idPlato = null;
        } else {
            idPlato = in.readLong();
        }
        titulo = in.readString();
        descripcion = in.readString();
        if (in.readByte() == 0) {
            precio = null;
        } else {
            precio = in.readDouble();
        }
        if (in.readByte() == 0) {
            calorias = null;
        } else {
            calorias = in.readInt();
        }
        url=in.readString();

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    @Override
    public String toString() {
        return "Plato{" +
                "idPlato=" + idPlato +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", calorias=" + calorias +
                ", url='" + url + '\'' +
                '}';
    }

    public static final Creator<Plato> CREATOR = new Creator<Plato>() {
        @Override
        public Plato createFromParcel(Parcel in) {
            return new Plato(in);
        }

        @Override
        public Plato[] newArray(int size) {
            return new Plato[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idPlato == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idPlato);
        }
        dest.writeString(titulo);
        dest.writeString(descripcion);
        if (precio == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(precio);
        }
        if (calorias == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(calorias);
        }
    }
}
