package com.nvapps.resolve;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";

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

    public void setAlarm(View v) {

        Log.d(TAG, "setAlarm: Setting Alarm");

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, ResolutionReceiver.class);
        intent.putExtra("title", "Resolution: Get Fit");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 200, intent, 0);

        // Set an alarm after 10 sec
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent);

        Log.d(TAG, "setAlarm: Alarm Set");
    }
}
