package com.tripleM.sendmeal_lab01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tripleM.sendmeal_lab01.adapters.PlatoRecyclerAdapter;
import com.tripleM.sendmeal_lab01.dao.PlatoDAODatos;
import com.tripleM.sendmeal_lab01.database.AppRepository;
import com.tripleM.sendmeal_lab01.retrofit.PlatoService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaItemActivity extends AppCompatActivity implements AppRepository.OnResultCallback  {

    private Toolbar toolbar;
    private PlatoDAODatos platoDAO;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer i;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_item);

        int callingActivity = getIntent().getIntExtra("calling-activity", 0);

        switch (callingActivity) {
            case 3:
                i=1;
                break;
            case 5:
                i=2;
                break;
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Platos");
        setSupportActionBar(toolbar);

        platoDAO = new PlatoDAODatos();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPlato);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PlatoRecyclerAdapter(platoDAO.list(),this,i);
        recyclerView.setAdapter(mAdapter);


        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("{urlApiRest}/")
                // En la siguiente linea, le especificamos a Retrofit que tiene que usar Gson para deserializar nuestros objetos
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlatoService platoService = retrofit.create(PlatoService.class);

        repository = new AppRepository(this.getApplication(), this);
        repository.buscarTodos();
        repository.buscar("1");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menulistaplatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuPedido:
                Intent i4 = new Intent(ListaItemActivity.this, PedidoActivity.class);
                startActivity(i4);
                break;
            case R.id.atras:
                finish();
                break;
            case R.id.BackButton:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onResult(List result) {
        // Vamos a obtener una Lista de items como resultado cuando finalize
        Toast.makeText(ListaItemActivity.this, "Exito BUSCAR TODOS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultId(Object result) {
        // Vamos a obtener un item como resultado cuando finalize
        Toast.makeText(ListaItemActivity.this, "Exito BUSCAR POR ID!", Toast.LENGTH_SHORT).show();

    }
}
