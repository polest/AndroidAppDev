package fi.jamk.arthur.maplauncher;

import android.content.Intent;
import android.net.Uri;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view){

        // get lat and lng values

        EditText lat_field = (EditText) findViewById(R.id.lat);
        String lat_value = lat_field.getText().toString();

        EditText lng_field = (EditText) findViewById(R.id.lng);
        String lng_value = lng_field.getText().toString();

        double lat = Double.parseDouble(lat_value);
        double lng = Double.parseDouble(lng_value);

        // Show map

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+lat+","+lng));
        startActivity(intent);

    }

}
