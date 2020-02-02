package com.example.servicioaudio;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyService extends Service {


    private NotificationManager notificationManager;
    private static final int ID_NOTIFICACION=1234;
    MediaPlayer mPlayer;


    public MyService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();
        Toast.makeText(this, "servicio creado", Toast.LENGTH_SHORT).show();
        mPlayer = MediaPlayer.create(this, R.raw.audio1);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1223", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "servicio iniciado "+startId, Toast.LENGTH_SHORT).show();
        mPlayer.start();


        //patron de vibracion

        long vibrate[] ={0,100,100};

     /*   NotificationCompat.Builder builder= new NotificationCompat.Builder(
                getBaseContext()).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Musica")
                .setContentText("Servicio de Musica")
                .setVibrate(vibrate)
                .setWhen(System.currentTimeMillis());*/
        //lanzaria la notificacion



        // Create an explicit intent for an Activity in your app
        Intent intent1 = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);




        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "1223")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Musica")
                .setContentText("Servicio Musica")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //notificationManager.notify(ID_NOTIFICACION, builder1.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(ID_NOTIFICACION, builder1.build());


        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "servicio destruido", Toast.LENGTH_SHORT).show();
        mPlayer.stop();
        notificationManager.cancel(ID_NOTIFICACION);
    }
}
