package fi.jamk.arthur.shoppinglist;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements AddHighscoreDialogFragment.DialogListener {
    private final String DATABASE_TABLE = "products";
    private final int DELETE_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find list view
        listView = (ListView)  findViewById(R.id.listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();

        // get data with own made queryData method
        queryData();

    }

    public String getTotalPrice(){

        // calculate total points in highscores
        float total_price = 0f;
        if (cursor.moveToFirst()) {
            do {
                float count = cursor.getInt(2);
                float price = cursor.getFloat(3); // columnIndex
                total_price += (count*price);
            } while(cursor.moveToNext());

        }

        String total_price_two = String.format("%.2f", total_price);

        return total_price_two;

    }

    // query data from database
    public void queryData() {
        cursor = db.rawQuery("SELECT _id, product, count, price FROM products ORDER BY _id ASC", null);
        // get data with query
        //String[] resultColumns = new String[]{"_id","product","count","price"};
        //cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,"price DESC",null);

        // add data to adapter
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, cursor,
                new String[] {"product", "count", "price"},      // from
                new int[] {R.id.product, R.id.count, R.id.price}    // to
                ,0);  // flags

        // show data in listView
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handles item selections
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                AddHighscoreDialogFragment eDialog = new AddHighscoreDialogFragment();
                eDialog.show(getFragmentManager(), "Add a new Product");
        }
        return false;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String product, int count, float price) {
        ContentValues values=new ContentValues(3);
        values.put("product", product);
        values.put("count", count);
        values.put("price", price);
        db.insert("products", null, values);
        // get data again
        queryData();
        Toast.makeText(getBaseContext(), "Total price: " + getTotalPrice(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                String[] args = {String.valueOf(info.id)};
                db.delete("products", "_id=?", args);
                queryData();
                Toast.makeText(getBaseContext(), "Total price: " + getTotalPrice(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // close cursor and db connection
        cursor.close();
        db.close();
    }
}
