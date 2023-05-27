package ca.on.conestogac.patelk.myvaccinationapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    DatabaseHelper myDB;
    ArrayList<String> user_id, user_name, user_email, user_phone, vaccine_type, vaccine_date;
    CustomAdapter customAdapter;

    ImageView imageViewEmpty;
    TextView NoData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        startService(new Intent(getApplicationContext(), NotificationService.class));

        //Theme toggle
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("dark_theme", false)) {
            setTheme(R.style.darkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        imageViewEmpty = findViewById(R.id.imageViewEmpty);
        NoData = findViewById(R.id.NoData);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_email = new ArrayList<>();
        user_phone = new ArrayList<>();
        vaccine_type = new ArrayList<>();
        vaccine_date = new ArrayList<>();

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, user_id, user_name, user_email, user_phone, vaccine_type, vaccine_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    protected void onRestart() {
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("dark_theme", false)) {
            setTheme(R.style.darkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        finish();
        startActivity(getIntent());
        super.onRestart();
    }

    void storeData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            imageViewEmpty.setVisibility(View.VISIBLE);
            NoData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                user_id.add(cursor.getString(0));
                user_name.add(cursor.getString(1));
                user_email.add(cursor.getString(2));
                user_phone.add(cursor.getString(3));
                vaccine_type.add(cursor.getString(4));
                vaccine_date.add(cursor.getString(5));
            }
            imageViewEmpty.setVisibility(View.GONE);
            NoData.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;

        switch (item.getItemId()) {
            case R.id.menu_reset:
                confirmDialog();
                break;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            default:
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }


    @Override
    protected void onStop() {
        startService(new Intent(getApplicationContext(), NotificationService.class));
        super.onStop();
    }


}