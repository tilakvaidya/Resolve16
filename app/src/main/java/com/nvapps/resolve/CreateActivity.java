package com.nvapps.resolve;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";

    public static int frequency = 0;

    @Bind(R.id.edit_title)
    EditText titleVal;
    @Bind(R.id.edit_category)
    EditText categoryVal;
    @Bind(R.id.button_frequency)
    Button button_frequency;

    ResolutionsDatabase.ResolutionsDBHelper mDbHelper;
    SQLiteDatabase db;

    String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new ResolutionsDatabase.ResolutionsDBHelper(this);
        db = mDbHelper.getWritableDatabase();

        currentDate = DateFormat.getDateInstance().format(Calendar.getInstance().getTime());

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

    public void setFrequency(View v){
        DialogFragment dialog = new FrequencyPickerDialogFragment();
        dialog.show(getSupportFragmentManager(), "Select frequency");
    }

    public void setAlarm(View v) {

        String title = titleVal.getText().toString();
        String category = "#" + categoryVal.getText().toString();

        if (title.equals("") || category.equals(""))
            return;

        ContentValues values = new ContentValues();
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE, title);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_CATEGORY, category);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_FREQUENCY, frequency);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_CHEAT_COUNTER, 0);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_DONE, 0);

        long newRowId = db.insert(
                ResolutionsDatabase.ResolutionsEntry.TABLE_NAME,
                null,
                values);

        //read current entry from database

        mDbHelper = new ResolutionsDatabase.ResolutionsDBHelper(this);
        db = mDbHelper.getReadableDatabase();

        String[] projection = {
                ResolutionsDatabase.ResolutionsEntry._ID,
                ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE,
        };

        Cursor c = db.query(
                ResolutionsDatabase.ResolutionsEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        c.moveToPosition((int) newRowId - 1);
        String text = c.getString(c.getColumnIndexOrThrow(ResolutionsDatabase.ResolutionsEntry.COLUMN_NAME_TITLE)); //or any column name

        titleVal.setText(frequency + "");
        categoryVal.setText("");

        Log.d(TAG, "setAlarm: Setting Alarm");

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, ResolutionReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("category", category);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 200, intent, 0);

        // Set an alarm after 10 sec
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent);

        Log.d(TAG, "setAlarm: Alarm Set");
    }
}
