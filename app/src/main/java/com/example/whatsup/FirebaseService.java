package com.example.whatsup;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.whatsup.api.RetrofitService;
import com.example.whatsup.api.WebServiceAPI;
import com.example.whatsup.data.AppDB;
import com.example.whatsup.data.ContactDao;
import com.example.whatsup.data.MessageDao;
import com.example.whatsup.entities.Contact;
import com.example.whatsup.entities.Message;
import com.example.whatsup.entities.Sender;
import com.example.whatsup.entities.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseService extends FirebaseMessagingService {
    public WebServiceAPI api;
    private ContactDao contactDao;
    private MessageDao messageDao;
    static public String token;

    public FirebaseService() {
        AppDB db = AppDB.getDatabase(this);
        messageDao = db.messageDao();
        contactDao = db.contactDao();
        api = RetrofitService.getAPI(State.server);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        Message m = new Message(99, "now", new Sender(remoteMessage.getNotification().getTitle()), remoteMessage.getNotification().getBody());
        messageDao.insert(m);
        contactDao.insert(new Contact(data.get("id"), new User(remoteMessage.getNotification().getTitle(), "friend", "1"), m));
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123456")
                .setSmallIcon(R.drawable.ic_msg)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        managerCompat.notify(1, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WhatsApp";
            String description = "WhatsApp";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("123456", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);
        token = refreshedToken;
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("firebaseToken", refreshedToken).apply();
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("firebaseToken", "empty");
    }


}