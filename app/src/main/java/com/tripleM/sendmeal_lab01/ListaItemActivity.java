package com.tripleM.sendmeal_lab01;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tripleM.sendmeal_lab01.adapters.PlatoRecyclerAdapter;
import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.retrofit.PlatoRepositoryRest;
import com.tripleM.sendmeal_lab01.room.AccionesDAO;
import com.tripleM.sendmeal_lab01.room.AppRepository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ListaItemActivity extends AppCompatActivity implements AppRepository.OnResultCallback  {

    private Toolbar toolbar;
    private List<Plato> listaPlatos =new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer i;
    private AppRepository platoRoom;
    private PlatoRepositoryRest platoRest;

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

        recyclerView = findViewById(R.id.recyclerPlato);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lista de Platos");
        setSupportActionBar(toolbar);

        mAdapter = new PlatoRecyclerAdapter(listaPlatos,this,i);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        //Persistencia con Room
        platoRoom = new AppRepository(this.getApplication(),this);

        //Persistencia con Retrofit
        platoRest=new PlatoRepositoryRest();

        //Buscar platos con Room
        platoRoom.buscarTodos();

        //Buscar platos con Retrofit
        platoRest.listarPlato(mHandler);
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
        listaPlatos.addAll(result);
        mAdapter.notifyDataSetChanged();
          System.out.println(result);
//        Toast.makeText(ListaItemActivity.this, "Exito BUSCAR TODOS!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultId(Object result) {
//        System.out.println(result);
//      Toast.makeText(ListaItemActivity.this, "Exito BUSCAR POR ID!", Toast.LENGTH_SHORT).show();
    }

    public void actualizarPlato(final Plato plato, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.layout_actualizar_plato, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(ListaItemActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView dialogo = view.findViewById(R.id.dialogoTitulo);
        final EditText titulo = view.findViewById(R.id.tituloDialogo);
        final EditText descripcion = view.findViewById(R.id.descripcionDialogo);
        final EditText precio = view.findViewById(R.id.precioDialogo);
        final EditText calorias = view.findViewById(R.id.caloriasDialogo);

        dialogo.setText("Editar Plato");
        titulo.setText(plato.getTitulo());
        descripcion.setText(plato.getDescripcion());
        precio.setText(plato.getPrecio().toString());
        calorias.setText(plato.getCalorias().toString());

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        Plato p = listaPlatos.get(position);

                        p.setTitulo(titulo.getText().toString());
                        p.setDescripcion(descripcion.getText().toString());
                        p.setPrecio(Double.parseDouble(precio.getText().toString()));
                        p.setCalorias(Integer.parseInt(calorias.getText().toString()));

                        platoRoom.actualizar(p);

                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }

    public void borrarPlato(Plato plato, int position) {
        listaPlatos.remove(position);
        platoRoom.borrar(plato);
        mAdapter.notifyDataSetChanged();
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<ListaItemActivity> mActivity;

        public MyHandler(ListaItemActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ListaItemActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case CREAR_PLATO:
                        Plato plato = data.getParcelable("plato");
                        System.out.println("PLATO: "+plato);
                        Toast.makeText(activity,"Plato creado",Toast.LENGTH_LONG).show();
                        activity.finish();
                        break;
                    case LISTAR_PLATOS:
                        List<Plato> platos = data.getParcelable("platos");
                        System.out.println("PLATOS: "+platos);
                        Toast.makeText(activity,"Platos listados",Toast.LENGTH_LONG).show();
                        activity.finish();
                        break;
                    case ERROR:
                            String error = data.getParcelable("error");
                        System.out.println("ERROR: "+error);
                        Toast.makeText(activity,"Error de API REST",Toast.LENGTH_LONG).show();
                        //activity.finish();
                        break;
                }
            }
        }
    }
}
