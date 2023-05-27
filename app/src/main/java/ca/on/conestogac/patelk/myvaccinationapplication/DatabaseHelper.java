package ca.on.conestogac.patelk.myvaccinationapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "VaccineApplication.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_vaccination";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DATE = "date";

     DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_CONTACT + " INTEGER, " +
                        COLUMN_TYPE + " TEXT, " +
                        COLUMN_DATE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void register(String name1,String email1, int contact1, String type1, String date1){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name1);
        cv.put(COLUMN_EMAIL, email1);
        cv.put(COLUMN_CONTACT, contact1);
        cv.put(COLUMN_TYPE, type1);
        cv.put(COLUMN_DATE, date1);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name1, String email1, String phone1, String type1, String date1){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put(COLUMN_NAME, name1);
         cv.put(COLUMN_EMAIL, email1);
         cv.put(COLUMN_CONTACT, phone1);
         cv.put(COLUMN_TYPE, type1);
         cv.put(COLUMN_DATE, date1);

         long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context, "Fail to Update", Toast.LENGTH_SHORT).show();
         }else
         {
             Toast.makeText(context, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
         }

    }

    void deleteOneRow(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
         if(result == -1){
             Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
         }
         else{
             Toast.makeText(context, "Successfully Deleted!!", Toast.LENGTH_SHORT).show();
         }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
