package com.tripleM.sendmeal_lab01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tripleM.sendmeal_lab01.adapters.PedidoRecyclerAdapter;
import com.tripleM.sendmeal_lab01.broadcast.MyNotificationPublisher;
import com.tripleM.sendmeal_lab01.model.Pedido;
import com.tripleM.sendmeal_lab01.model.Plato;
import com.tripleM.sendmeal_lab01.retrofit.PedidoRepositoryRest;
import com.tripleM.sendmeal_lab01.room.AccionesDAO;
import com.tripleM.sendmeal_lab01.room.AppRepository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity implements AppRepository.OnResultCallback {

    private Toolbar toolbar;
    private EditText etEmail;
    private RadioGroup rgEntrega;
    private RadioButton rbEnvio, rbTakeAway;
    private Button btAgregarPlato, btConfirmar,ubicacionPedido;
    private ArrayList<Pair<String,String>> listaPlatos;
    private RecyclerView platosPedidos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private Double subtototal;
    private TextView subtotalPrecio, textoSubtotal,ubicacion,listaPlatosPedido;
    private AppRepository pedidoRoom;
    private PedidoRepositoryRest pedidoRest;
    private Context contexto = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tu Pedido");
        setSupportActionBar(toolbar);

        etEmail=findViewById(R.id.emailUsuario);
        ubicacionPedido=findViewById(R.id.ubicacionPedido);
        rgEntrega=findViewById(R.id.entrega);
        rbEnvio=findViewById(R.id.envio);
        rbTakeAway=findViewById(R.id.takeAway);
        btAgregarPlato=findViewById(R.id.btAgregarPlato);
        btConfirmar=findViewById(R.id.btConfirmar);
        subtotalPrecio=findViewById(R.id.etSubtotalPrecio);
        textoSubtotal=findViewById(R.id.etSubtotal);
        ubicacion=findViewById(R.id.direccion);
        listaPlatos = new ArrayList<>();
        listaPlatosPedido = findViewById(R.id.tvListaPlatos);

        if(listaPlatos.isEmpty()) {
            subtotalPrecio.setVisibility(View.GONE);
            textoSubtotal.setVisibility(View.GONE);
        }

        platosPedidos=findViewById(R.id.recyclerPedido);
        platosPedidos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        platosPedidos.setLayoutManager(layoutManager);

        mAdapter = new PedidoRecyclerAdapter(listaPlatos,this);
        platosPedidos.setAdapter(mAdapter);
        subtototal=0.0;

        //Persistencia con Room
        pedidoRoom = new AppRepository(getApplication(),this);

        //Persistencia con Retrofit
        pedidoRest = new PedidoRepositoryRest();

        //Buscar todos los pedidos con Room (solo para verificar)
        //pedidoRoom.buscarTodosPedidos();

        //Buscar todos los pedidos con Retrofit (solo para verificar)
        pedidoRest.listarPedido(mHandler);
        RadioGroup.OnCheckedChangeListener radioListenerRG1 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        rbEnvio.setEnabled(false);
                        break;
                    case R.id.rb2:
                        rbTakeAway.setEnabled(false);
                        break;
                }
            }
        };

        btAgregarPlato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PedidoActivity.this, ListaItemActivity.class);
                i.putExtra("calling-activity", 5);
                startActivityForResult(i,5);
            }
        });


        ubicacionPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PedidoActivity.this, MapsActivity.class);
                i.putExtra("calling-activity", 999);
                startActivityForResult(i,999);
            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String mensaje = "";
                String mensaje_final;

                if(ubicacion.getText().toString().compareTo("Ubicación no especificada.")==0) mensaje += "No seleccionó la ubicación. \n";
                if (etEmail.getText().toString().length() == 0) mensaje += "El e-mail está vacío. \n";
                else if (!condicionEmail(etEmail.getText().toString())) mensaje += "El e-mail debe contener un @ seguido de al menos 3 letras. \n";
                if (rgEntrega.getCheckedRadioButtonId() == -1) mensaje += "Seleccione el tipo de envío. \n";
                if (listaPlatos.isEmpty()) mensaje += "No agregó ningún plato al pedido. \n";

                if (mensaje.length() == 0) {

                    Pedido pedido=new Pedido(null,subtototal,listaPlatos,rbEnvio.isSelected(),etEmail.getText().toString(),ubicacion.getText().toString());

                    //Guardar pedido con Room
                    pedidoRoom.insertarPedido(pedido,PedidoActivity.this);

                    //Guardar pedido con Retrofit
                    pedidoRest.crearPedido(pedido,mHandler);

                    //Intent notificationIntent = new Intent(contexto, MyNotificationPublisher.class);
                    //contexto.sendBroadcast(notificationIntent);

                    finish();

                } else {
                    mensaje_final = mensaje.substring(0, mensaje.length() - 1);

                    Toast toast1 = Toast.makeText(getApplicationContext(), mensaje_final, Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });

    }
        public boolean condicionEmail(String email){
            email=email.toLowerCase();
            if(!email.contains("@")) return false;
            else {
                for (int i = 0; i < email.length(); i++){
                    if(email.charAt(i)=='@'&&(email.length()-i)>=3){
                        if(email.charAt(i+1)>='a'&&email.charAt(i+1)<='z'&&
                                email.charAt(i+2)>='a'&&email.charAt(i+2)<='z'&&
                                email.charAt(i+3)>='a'&&email.charAt(i+3)<='z') return true;
                    }
                }
            }
            return false;
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                Double st = 0.0;
        if(requestCode==5){
            if(resultCode==RESULT_OK){
                String nombrePlato = data.getExtras().getString("nombrePlato");
                String precioPlato = data.getExtras().getString("precioPlato");

                st=Double.parseDouble(precioPlato.substring(2));
                subtototal=subtototal+st;
                subtotalPrecio.setText("$"+subtototal.toString());

                listaPlatos.add(new Pair<>(nombrePlato, precioPlato));

                if(!listaPlatos.isEmpty()) {
                    subtotalPrecio.setVisibility(View.VISIBLE);
                    textoSubtotal.setVisibility(View.VISIBLE);
                }

                mAdapter = new PedidoRecyclerAdapter(listaPlatos,this);
                platosPedidos.setAdapter(mAdapter);

            }
        }
        if(requestCode==999){
            if(resultCode==RESULT_OK){
                double latitud = data.getExtras().getDouble("latitud");
                double longitud = data.getExtras().getDouble("longitud");

                ubicacion.setText("Lat: "+latitud+"     "+"Long:"+longitud);
            }
        }
    }

    @Override
    public void onResult(List result) {
        System.out.println("PEDIDOS " +result);
    }

    @Override
    public void onResultId(Object result) {

    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<PedidoActivity> mActivity;

        public MyHandler(PedidoActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PedidoActivity activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
                AccionesDAO evento = AccionesDAO.valueOf(data.getString("accion"));
                switch (evento){
                    case CREAR_PEDIDO:
                        Plato plato = data.getParcelable("pedido");
                        System.out.println("PEDIDO: "+plato);
                        Toast.makeText(activity,"Pedido creado",Toast.LENGTH_LONG).show();
                        activity.finish();
                        break;
                    case LISTAR_PEDIDOS:
                        List<Plato> platos = data.getParcelable("pedidos");
                        System.out.println("PEDIDOS: "+platos);
                        Toast.makeText(activity,"Pedidos listados",Toast.LENGTH_LONG).show();
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