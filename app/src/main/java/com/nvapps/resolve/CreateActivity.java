package com.nvapps.resolve;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    ResolutionsDatabase.ResolutionsDBHelper mDbHelper;
    SQLiteDatabase db;
    ContentValues values;
    long newRowId;
    Button startDate;
    Date d;
    String currentDate;
    Calendar calendar;
    SimpleDateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDbHelper = new ResolutionsDatabase.ResolutionsDBHelper(this);
        db = mDbHelper.getReadableDatabase();
        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = df.format(calendar.getTime());
        startDate = (Button)findViewById(R.id.button_start_date);
        startDate.setText(currentDate);

       /* values = new ContentValues();
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_ENTRY_ID, "1");
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE, "First Resolution");
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_CHEAT_COUNTER, "0");

        newRowId = db.insert(
                ResolutionsDatabase.ResolutionsEntry.TABLE_NAME,
                null,
                values);
        Button button = (Button) findViewById(R.id.show);
        final TextView textView = (TextView) findViewById(R.id.show_text);

        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

            db = mDbHelper.getReadableDatabase();
            String[] projection = {
                    ResolutionsDatabase.ResolutionsEntry._ID,
                    ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE
            };
                    Cursor c = db.query(
                            ResolutionsDatabase.ResolutionsEntry.TABLE_NAME,  // The table to query
                            projection,                               // The columns to return
                            null,                                // The columns for the WHERE clause
                            null,                            // The values for the WHERE clause
                            null,                                     // don't group the rows
                            null,                                     // don't filter by row groups
                            null                                 // The sort order
                    );
                    c.moveToFirst();
                    String title = c.getString(c.getColumnIndexOrThrow(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE));
                    textView.setText(title);


                }
            });*/


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
