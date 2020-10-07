package com.tripleM.sendmeal_lab01;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tripleM.sendmeal_lab01.adapters.PlatoRecyclerAdapter;
import com.tripleM.sendmeal_lab01.dao.PlatoDAO;

public class ListaItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private PlatoDAO platoDAO;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer i;

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

        platoDAO = new PlatoDAO();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerPlato);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new PlatoRecyclerAdapter(platoDAO.list(),this,i);
        recyclerView.setAdapter(mAdapter);
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

}
