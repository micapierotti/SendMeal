package com.tripleM.sendmeal_lab01.room;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.tripleM.sendmeal_lab01.PedidoActivity;
import com.tripleM.sendmeal_lab01.R;
import com.tripleM.sendmeal_lab01.broadcast.MyNotificationPublisher;
import com.tripleM.sendmeal_lab01.model.Pedido;
import com.tripleM.sendmeal_lab01.model.Plato;
import java.util.List;
import java.util.concurrent.Executor;

public class AppRepository implements OnPlatoResultCallback{
    private PlatoDAORoom platoDAORoom;
    private PedidoDAORoom pedidoDAORoom;
    private OnResultCallback callback;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    public AppRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        platoDAORoom = db.platoDao();
        pedidoDAORoom = db.pedidoDao();
        callback = context;
    }

    public void insertar(final Plato plato){
        new CrearPlato().execute(plato);
    }


    public void insertarPedido(final Pedido pedido,final Context ctx){
        new CrearPedido(ctx).execute(pedido);
    }


    public void borrar(final Plato plato){
        new BorrarPlato().execute(plato);
    }

    public void actualizar(final Plato plato){
        new ActualizarPlato().execute(plato);
    }

    public void buscar(String id) {
        new BuscarPlatoById().execute(id);
    }

    public void buscarTodos() {
        new BuscarPlatos().execute();
    }

    public void buscarTodosPedidos() {
        new BuscarPedidos().execute();
    }


    @Override
    public void onResult(List<Plato> platos) {
        Log.d("DEBUG", "Buscar Platos ejecutado correctamente");
        callback.onResult(platos);
    }

    @Override
    public void onResultId(Plato plato) {
        Log.d("DEBUG", "Buscar Platos por ID ejecutado correctamente");
        callback.onResultId(plato);
    }

    @Override
    public void onResultActualizar(boolean b) {
        callback.onResultId(true);
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
        void onResultId(T result);
    }

    private class BuscarPlatos extends AsyncTask<Void, Void, List<Plato>> {

        @Override
        protected List<Plato> doInBackground(Void... Void) {
            List<Plato> platos = platoDAORoom.buscarTodos();
            return platos;
        }

        @Override
        protected void onPostExecute(List<Plato> platos) {
            super.onPostExecute(platos);
            callback.onResult(platos);
        }
    }

    private class BuscarPedidos extends AsyncTask<Void, Void, List<Pedido>> {

        @Override
        protected List<Pedido> doInBackground(Void... Void) {
            List<Pedido> pedidos = pedidoDAORoom.buscarTodos();
            return pedidos;
        }

        @Override
        protected void onPostExecute(List<Pedido> pedidos) {
            super.onPostExecute(pedidos);
            callback.onResult(pedidos);
        }
    }

    private class BuscarPlatoById extends AsyncTask<String, Void, Plato> {
        @Override
        protected Plato doInBackground(String... string) {
            Plato plato = platoDAORoom.buscar(string[0]);
            return plato;
        }

        @Override
        protected void onPostExecute(Plato plato) {
            super.onPostExecute(plato);
            callback.onResultId(plato);
        }
    }

    private class CrearPlato extends AsyncTask<Plato,Void,Void> {
        @Override
        protected Void doInBackground(Plato... platos) {
            platoDAORoom.insertar(platos[0]);
            return null;
        }
    }

    private class ActualizarPlato extends AsyncTask<Plato,Void,Void> {
        @Override
        protected Void doInBackground(Plato... platos) {
            platoDAORoom.actualizar(platos[0]);
            return null;
        }
    }

    private class BorrarPlato extends AsyncTask<Plato,Void,Void> {
        @Override
        protected Void doInBackground(Plato... platos) {
            platoDAORoom.borrar(platos[0]);
            return null;
        }
    }

    private class CrearPedido extends AsyncTask<Pedido,Void,Void> {

        private Context ctx;

        public CrearPedido(Context ctx){
            this.ctx=ctx;
        }

        @Override
        protected Void doInBackground(Pedido... pedidos) {
            pedidoDAORoom.insertar(pedidos[0]);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, default_notification_channel_id);
            builder.setContentTitle("SendMeal");
            builder.setContentText("¡El pedido ya está listo!");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setAutoCancel(true);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);

            Intent notificationIntent = new Intent(ctx, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, builder.build());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);

        }
    }
}