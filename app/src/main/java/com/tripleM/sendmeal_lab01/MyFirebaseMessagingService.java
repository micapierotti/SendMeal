package com.tripleM.sendmeal_lab01;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Pueden validar si el mensaje trae datos
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Payload del mensaje: " + remoteMessage.getData());
            // Realizar alguna acción en consecuencia
        }
        // Pueden validar si el mensaje trae una notificación
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Cuerpo de la notificación del mensaje: " + remoteMessage.getNotification().getBody());
            // También pueden usar:
            // remoteMessage.getNotification().getTitle()
        }
        // Cualquier otra acción que quieran realizar al recibir un mensaje de firebase, la pueden realizar aca
        // EJ:
        sendNotification("Cuerpo de la notificacion");
    }

    // Función para crear una notificación (completar)
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // PendingIntent pendingIntent = ....

        //NotificationCompat.Builder notificationBuilder =
        //        new NotificationCompat.Builder(this, channelId)
        //                          ....
        //               .setContentIntent(pendingIntent);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "66")
                .setContentTitle("Pedido Confirmado").setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(messageBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel("66","Pedido confirmado",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(canal);
        }

        notificationManager.notify(66, notificationBuilder.build());
    }

}