package fi.jamk.arthur.basicui2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.login); // add strings to control

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] {"Pasi", "Juha", "Kari", "Jouni", "Esa", "Hannu"});
        actv.setAdapter(aa);

    }


    public void clickOnLogin(View view){

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.login);
        String actv_text = actv.getText().toString();

        EditText et = (EditText) findViewById(R.id.editText);
        String et_text = et.getText().toString();

        Toast.makeText(MainActivity.this, "Login: " + actv_text + "\n" + "Password: " + et_text, Toast.LENGTH_SHORT).show();


    }

}
