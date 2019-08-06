package com.grupo2.iomt.Services;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
    //Ctrl + O
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("TOKENFIREBASE", s);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
    }
    private void showNotification(String title, String body){
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.amst.firebasenotify.test";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                            NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("EDMT Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("info");
        notificationManager.notify(
                new Random().nextInt(), notificationBuilder.build());
    }
}
