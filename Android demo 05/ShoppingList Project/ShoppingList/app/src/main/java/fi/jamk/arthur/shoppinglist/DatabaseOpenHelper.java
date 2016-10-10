package fi.jamk.arthur.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arthur on 10.10.2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private final String DATABASE_TABLE = "products";
    private final String PRODUCT = "product";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context) {
        // Context, database name, optional cursor factory, database version
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a new table
        db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+PRODUCT+" TEXT, "+COUNT+" REAL, "+PRICE+" REAL);");
        // create sample data
        ContentValues values = new ContentValues();
        values.put(PRODUCT, "PS4");
        values.put(COUNT, 2.0);
        values.put(PRICE, 500.0);
        // insert data to database, name of table, "Nullcolumnhack", values
        db.insert(DATABASE_TABLE, null, values);
        // a more data...
        values.put(PRODUCT, "Lighter");
        values.put(COUNT, 10.0);
        values.put(PRICE, 1.0);
        db.insert(DATABASE_TABLE, null, values);
        // a more data...
        values.put(PRODUCT, "Cola");
        values.put(COUNT, 12.0);
        values.put(PRICE, 2.0);
        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }
}