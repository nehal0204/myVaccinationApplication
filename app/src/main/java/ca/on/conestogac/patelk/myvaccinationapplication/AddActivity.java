package ca.on.conestogac.patelk.myvaccinationapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    private Button submit_button;

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText type;
    private EditText date;

    DatePickerDialog.OnDateSetListener setListener;

    private ImageView imageViewHeart;
    private boolean saveState;
    private boolean creatingActivity = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        creatingActivity = true;

        //Theme toggle
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPref.getBoolean("dark_theme", false)) {
            setTheme(R.style.darkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        type = findViewById(R.id.type);
        date = findViewById(R.id.date);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date1 = day+"/"+month+"/"+year;
                        date.setText(date1);

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        submit_button = findViewById(R.id.submit_button);

        imageViewHeart = findViewById(R.id.imageViewHeart);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewHeart.setImageResource(R.drawable.thank_you_icon);
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.register(name.getText().toString().trim(),
                        email.getText().toString().trim(),
                        Integer.valueOf(phone.getText().toString().trim()),
                        type.getText().toString().trim(),
                        date.getText().toString().trim());

            }
        });



    }
    @Override
    protected void onPause() {
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString("Name", name.getText().toString());
        ed.putString("Email", email.getText().toString());
        ed.putString("Number", phone.getText().toString());
        ed.putString("Type", type.getText().toString());
        ed.putString("Date", date.getText().toString());
        ed.commit();
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        saveState = sharedPref.getBoolean("sync", false);
        if (saveState || !creatingActivity) {
            name.setText(sharedPref.getString("Name", ""));
            email.setText(sharedPref.getString("Email", ""));
            phone.setText(sharedPref.getString("Number", ""));
            type.setText(sharedPref.getString("Type", ""));
            date.setText(sharedPref.getString("Date", ""));

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

}