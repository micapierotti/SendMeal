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
                startActivityForResult(i1,999);
                break;

            case R.id.menuCrearItem:
                Intent i2 = new Intent(HomeActivity.this, CrearItemActivity.class);
                startActivityForResult(i2,987);
                break;

            case R.id.menuListaItem:
                Intent i3 = new Intent(HomeActivity.this, ListaItemActivity.class);
                startActivityForResult(i3,992);
                break;

        }
        return true;
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
    }
}
