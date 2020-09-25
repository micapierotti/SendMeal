package com.tripleM.sendmeal_lab01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tripleM.sendmeal_lab01.model.Usuario;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_home);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menú");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuRegistrarme:
                Intent i1 = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivityForResult(i1,1);
                break;

            case R.id.menuCrearItem:
                Intent i2 = new Intent(HomeActivity.this, CrearItemActivity.class);
                startActivityForResult(i2,2);
                break;

            case R.id.menuListaItem:
                Intent i3 = new Intent(HomeActivity.this, ListaItemActivity.class);
                startActivityForResult(i3,3);
                break;

        }
        return true;
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==1){
                if(resultCode==RESULT_OK){
                    String msj = data.getExtras().getString("user");
                    Toast toast1 = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }

        if(requestCode==2){
            if(resultCode==RESULT_OK){
                String msj = data.getExtras().getString("plato");
                Toast toast1 = Toast.makeText(getApplicationContext(), msj, Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
    }
}
