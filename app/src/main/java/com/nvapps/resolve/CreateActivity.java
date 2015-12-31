package com.nvapps.resolve;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateActivity";

    @Bind(R.id.edit_title)
    EditText titleVal;
    @Bind(R.id.edit_category)
    EditText categoryVal;

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
        db = mDbHelper.getReadableDatabase();

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

    public void setAlarm(View v) {

        String title = titleVal.getText().toString();
        String category = categoryVal.getText().toString();

        if (title.equals("") || category.equals(""))
            return;

        titleVal.setText("");
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
