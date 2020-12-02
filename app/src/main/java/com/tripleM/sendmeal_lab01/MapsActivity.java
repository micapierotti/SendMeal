package com.tripleM.sendmeal_lab01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng ubicacionPedido;
    private Button confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        confirmar=findViewById(R.id.confirmar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Instancia de mapa
        mMap = googleMap;

        //Pedir permiso para saber ubicacion actual
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }

        mMap.setMyLocationEnabled(true);

        //Seteo el tipo de mapa que quiero
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //Habilito que se pueda ver el trafico
        mMap.setTrafficEnabled(true);

        //Habilito que se pueda hacer zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //Instancia de marcadores
        final Map<Integer, MarkerOptions> marcadores = new HashMap<>();

        //Marcador en Crai
        //mostrarCrai();

        //Marcador en Sydney
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        //Posicion inicial de la camara en Sydney
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //Configuraciones de la rotaciones y gestos
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);

        //Marcador de clic largo
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions m = new MarkerOptions()
                        .position(latLng)
                        .title("Destino del pedido")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                mMap.clear();
                mMap.addMarker(m);
                ubicacionPedido=latLng;
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iresult = new Intent();
                iresult.putExtra("latitud", ubicacionPedido.latitude);
                iresult.putExtra("latitud", ubicacionPedido.longitude);
                setResult(RESULT_OK,iresult);
                finish();
            }
        });

/*
        //Listener de un marcador
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setVisible(false);
                return false;
            }
        });

        //GENERAR POLIGONO

        //Creo la lista de puntos a usar para el poligono
        List<LatLng> lista = this.generarPuntos(1);
        LatLng ultimoPunto = null;

        //Uno los puntos, pinto de rojo el borde y de azul el interior
        PolygonOptions linea = new PolygonOptions();
        for(LatLng punto: lista ){
            linea.add(punto).fillColor(Color.RED).strokeColor(Color.BLUE);
            ultimoPunto = punto;
        }
        googleMap.addPolygon(linea);

        //Animacion de la posición inicial a un punto en especifico con un zoom
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ultimoPunto)
                .zoom(15)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
  */  }
/*
    private void mostrarCrai(){
        LatLng crai= new LatLng(-31.620816,-60.747582);
        mMap.addMarker(new MarkerOptions().position(crai)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.club))
                .title("CRAI")
                .snippet("Club de rugby ateneo inmaculada"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(crai)
                .zoom(17)
                .bearing(90)
                .tilt(30)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),3000,null);
    }

    private List<LatLng> generarPuntos(Integer n){

        //Agego dos puntos iniciales
        List<LatLng> lista = new ArrayList<>();
        double norte = -31.614339;
        double oeste = -60.702693;
        double sur = -31.644426;
        double este = -60.687270;
        LatLng punto1 = new LatLng(norte,oeste);
        LatLng punto2 = new LatLng(sur,este);
        lista.add(punto1);
        lista.add(punto2);

        //Agrego n puntos mas cercanos a punto 1 de forma aleatoria
        Random r = new Random();
        for(int i =0; i<n;i++){
            double  l1 = r.nextDouble()/1000.0;
            double  l2 = r.nextDouble()/1000.0;
            lista.add(new LatLng(norte-l1,oeste-l2));
        }

        return lista;
    }
*/
    //Habilitar ir a la posicion actual
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==9999 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }
    }
}