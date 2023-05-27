package ca.on.conestogac.patelk.myvaccinationapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class UpdateActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    EditText name2, email2, phone2, type2, date2;
    Button update_button, delete_button;

    String id1, name1, email1, phone1, type1, date1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme toggle
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("dark_theme", false)) {
            setTheme(R.style.darkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name2 = findViewById(R.id.name2);
        email2 = findViewById(R.id.email2);
        phone2 = findViewById(R.id.phone2);
        type2 = findViewById(R.id.type2);
        date2 = findViewById(R.id.date2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name1);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.updateData(id1, name1, email1, phone1, type1, date1);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id1") && getIntent().hasExtra("name1") && getIntent().hasExtra("email1")
                && getIntent().hasExtra("phone1") && getIntent().hasExtra("type1") && getIntent().hasExtra("date1")){
            //Getting Data
            id1 = getIntent().getStringExtra("id1");
            name1 = getIntent().getStringExtra("name1");
            email1 = getIntent().getStringExtra("email1");
            phone1 = getIntent().getStringExtra("phone1");
            type1 = getIntent().getStringExtra("type1");
            date1 = getIntent().getStringExtra("date1");

            //Setting Data
            name2.setText(name1);
            email2.setText(email1);
            phone2.setText(phone1);
            type2.setText(type1);
            date2.setText(date1);
        }else{
            Snackbar.make(findViewById(R.id.mainLayout),"No Data",Snackbar.LENGTH_SHORT).show();
        }
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

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name1 + " ? ");
        builder.setMessage("Are you sure you want to delete "+ name1 + " ? " );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id1);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}