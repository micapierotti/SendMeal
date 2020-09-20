package com.tripleM.sendmeal_lab01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tripleM.sendmeal_lab01.model.Plato;

public class CrearItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText titulo, descripcion, precio, calorias;
    private Button guardar;
    private Plato plato;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);

        titulo = findViewById(R.id.tituloPlato);
        descripcion = findViewById(R.id.descripcion);
        precio = findViewById(R.id.precio);
        calorias = findViewById(R.id.calorias);
        guardar = findViewById(R.id.guardarPlato);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CrearPlatos");
        setSupportActionBar(toolbar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = "";
                String mensaje_final = new String();
                String mensaje_exitoso = "Plato creado exitosamente.";

                if(titulo.getText().toString().length()==0) mensaje += "El titulo está vacío. \n";
                if(descripcion.getText().toString().length()==0) mensaje += "La descripción está vacía. \n";
                if(precio.getText().toString().length()==0) mensaje += "El precio está vacío. \n";
                if(calorias.getText().toString().length()==0) mensaje += "Las calorias están vacías. \n";

                if(mensaje.length()==0){
                    mensaje_final=mensaje_exitoso;

                    plato = new Plato(titulo.getText().toString(),descripcion.getText().toString(),Double.parseDouble(precio.getText().toString()),Integer.parseInt(calorias.getText().toString()));
                    System.out.println(plato);

                    Intent i = new Intent();
                    activity.setResult(Activity.RESULT_OK,i);
                    activity.finish();

                }else mensaje_final=mensaje.substring(0,mensaje.length()-1);

                Toast toast1 = Toast.makeText(getApplicationContext(), mensaje_final, Toast.LENGTH_SHORT);
                toast1.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.atras:
               Intent i = new Intent(CrearItemActivity.this, HomeActivity.class);
               startActivityForResult(i,987);
                break;

        }
        return true;
    }

}
