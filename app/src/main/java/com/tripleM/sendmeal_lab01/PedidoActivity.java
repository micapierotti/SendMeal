package com.tripleM.sendmeal_lab01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tripleM.sendmeal_lab01.adapters.PedidoRecyclerAdapter;
import com.tripleM.sendmeal_lab01.broadcast.MyNotificationPublisher;

import java.util.ArrayList;

public class PedidoActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    private Toolbar toolbar;
    private EditText etEmail, etDireccion;
    private RadioGroup rgEntrega;
    private RadioButton rbEnvio, rbTakeAway;
    private Button btAgregarPlato, btConfirmar;
    private ArrayList<Pair<String,String>> listaPlatos;
    private RecyclerView platosPedidos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private GuardarPedido guardarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tu Pedido");
        setSupportActionBar(toolbar);

        etEmail=findViewById(R.id.emailUsuario);
        etDireccion=findViewById(R.id.direccionUsuario);
        rgEntrega=findViewById(R.id.entrega);
        rbEnvio=findViewById(R.id.envio);
        rbTakeAway=findViewById(R.id.takeAway);
        btAgregarPlato=findViewById(R.id.btAgregarPlato);
        btConfirmar=findViewById(R.id.btConfirmar);
        listaPlatos = new ArrayList<>();

        platosPedidos=findViewById(R.id.recyclerPedido);
        platosPedidos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        platosPedidos.setLayoutManager(layoutManager);

        mAdapter = new PedidoRecyclerAdapter(listaPlatos,this);
        platosPedidos.setAdapter(mAdapter);

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

        guardarPedido=new GuardarPedido();

        btConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String mensaje = "";
                String mensaje_final = new String();
                String mensaje_exitoso = "Pedido realizado exitosamente.";

                if(etDireccion.getText().toString().length()==0) mensaje += "La dirección está vacío. \n";
                if (etEmail.getText().toString().length() == 0) mensaje += "El e-mail está vacío. \n";
                else if (!condicionEmail(etEmail.getText().toString())) mensaje += "El e-mail debe contener un @ seguido de al menos 3 letras. \n";
                if (rgEntrega.getCheckedRadioButtonId() == -1) mensaje += "Seleccione el tipo de envío. \n";

                if (mensaje.length() == 0) {

                    guardarPedido.execute();

                    //finish();

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

        if(requestCode==5){
            if(resultCode==RESULT_OK){
                String nombrePlato = data.getExtras().getString("nombrePlato");
                String precioPlato = data.getExtras().getString("precioPlato");
                listaPlatos.add(new Pair<String, String>(nombrePlato,precioPlato));

                mAdapter = new PedidoRecyclerAdapter(listaPlatos,this);
                platosPedidos.setAdapter(mAdapter);

                //Toast toast1 = Toast.makeText(getApplicationContext(),nombrePlato+" "+precioPlato, Toast.LENGTH_SHORT);
                //toast1.show();
            }
        }
    }

    class GuardarPedido extends AsyncTask<Double,Integer,String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Double... doubles) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(PedidoActivity.this, default_notification_channel_id);
            builder.setContentTitle("SendMeal");
            builder.setContentText("¡El pedido ya está listo!");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setAutoCancel(true);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

            Intent notificationIntent = new Intent(PedidoActivity.this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, builder.build());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(PedidoActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onCancelled(String s) {

        }
    }


}