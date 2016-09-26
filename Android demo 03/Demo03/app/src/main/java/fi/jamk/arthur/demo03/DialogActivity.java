package fi.jamk.arthur.demo03;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_number2);
    }


    public void closeActivity(View view){

        ExitDialogFragment eDialog = new ExitDialogFragment();
        eDialog.show(getFragmentManager(), "exit");

    }


}
