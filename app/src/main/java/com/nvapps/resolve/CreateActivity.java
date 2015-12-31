package com.nvapps.resolve;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {

    ResolutionsDatabase.ResolutionsDBHelper mDbHelper;
    SQLiteDatabase db;
    Button startDate;
    String currentDate;
    Calendar calendar;
    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new ResolutionsDatabase.ResolutionsDBHelper(this);
        db = mDbHelper.getReadableDatabase();
        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = df.format(calendar.getTime());
        startDate = (Button)findViewById(R.id.button_start_date);
        startDate.setText(currentDate);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
