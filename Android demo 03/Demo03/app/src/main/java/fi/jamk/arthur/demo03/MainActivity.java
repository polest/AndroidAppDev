package fi.jamk.arthur.demo03;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int notification_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_video:


                // make notification

                // create a implicit intent
                Intent actionIntent = new Intent(Intent.ACTION_VIEW);
                actionIntent.setData(Uri.parse("http://www.youtube.com/"));

                // create a pending intent
                PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, 0);

                // create notification
                Notification notification = new Notification.Builder(this)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentTitle("Youtube")
                        .setContentText("Click here to open youtube!")
                        .setSmallIcon(R.drawable.music)
                        .setAutoCancel(true)
                        .setContentIntent(actionPendingIntent)
                        .setVisibility(Notification.VISIBILITY_PUBLIC).build();

                // connect notification manager
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                // make a notification
                notificationManager.notify(notification_ID, notification);



                Toast.makeText(getBaseContext(), "Youtube notification has been created", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_quit:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void openNewActivity(View view) {

        // open new activity

        Toast.makeText(MainActivity.this, "New Activity has been opened", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);


    }


    public void openNotificationActivity(View view){

        Toast.makeText(MainActivity.this, "Custom notification activity has been opened", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

}
