package fi.jamk.arthur.demo03;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void openCustomNotification(View view){

        int notification_ID = 1;

        String notificationTitle;
        String notificationText;
        String notificationWebsite;


        EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        notificationTitle = editTextTitle.getText().toString();

        EditText editTextText = (EditText) findViewById(R.id.editTextText);
        notificationText = editTextText.getText().toString();

        EditText editTextWebsite = (EditText) findViewById(R.id.editTextWebsite);
        notificationWebsite = editTextWebsite.getText().toString();

        if(notificationTitle.isEmpty() || notificationText.isEmpty() || notificationWebsite.isEmpty()){
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        } else {

            // make custom notification

            // create a implicit intent
            Intent actionIntent = new Intent(Intent.ACTION_VIEW);
            actionIntent.setData(Uri.parse("http://" + notificationWebsite + "/"));

            // create a pending intent
            PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);

            // create notification
            Notification notification = new Notification.Builder(this)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationText)
                    .setSmallIcon(R.drawable.video)
                    .setAutoCancel(true)
                    .setContentIntent(actionPendingIntent)
                    .setVisibility(Notification.VISIBILITY_PUBLIC).build();

            // connect notification manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            // make a notification
            notificationManager.notify(notification_ID, notification);

            Toast.makeText(NotificationActivity.this, "Custom notification created", Toast.LENGTH_SHORT).show();

        }

    }

    public void backButton(View view){
        finish();

        Toast.makeText(this, "You are back in the main activity", Toast.LENGTH_SHORT).show();
    }


}
