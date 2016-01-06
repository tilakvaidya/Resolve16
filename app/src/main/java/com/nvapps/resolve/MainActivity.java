package com.nvapps.resolve;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREFERENCES = "preferences";
    private static final String PREFERENCE_FIRST_RUN = "firstrun";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.resolution_list)
    RecyclerView resolutionList;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        boolean firstRun = getSharedPreferences(PREFERENCES, MODE_PRIVATE).getBoolean("firstRun", true);

        if (firstRun) {
            Log.d(TAG, "onCreate: First Run");
            setAlarm();
            getSharedPreferences(PREFERENCES, MODE_PRIVATE)
                    .edit()
                    .putBoolean(PREFERENCE_FIRST_RUN, false)
                    .commit();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateActivity.class));
            }
        });

        populateList();

    }

    private void populateList() {

        // TODO: Read from database
        ArrayList<Resolution> resolutions = new ArrayList<>();
        resolutions.add(new Resolution("Get Fit", "#Fitness"));
        resolutions.add(new Resolution("Get Smart", "#Smartness"));

        RecyclerView.LayoutManager resolutionLayout = new LinearLayoutManager(this);
        resolutionList.setLayoutManager(resolutionLayout);

        ResolutionAdapter resolutionAdapter = new ResolutionAdapter(this, resolutions);
        resolutionList.setAdapter(resolutionAdapter);
    }

    public void setAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ResolutionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 200, intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, pendingIntent);
        Log.d(TAG, "setAlarm: Alarm Set");
    }

    private void readDatabase() {

        db = new ResolutionsDatabase.ResolutionsDBHelper(this).getReadableDatabase();

        String[] projection = {
                ResolutionsDatabase.ResolutionsEntry.COLUMN_TITLE,
                ResolutionsDatabase.ResolutionsEntry.COLUMN_CATEGORY
        };

        Cursor c = db.query(
                ResolutionsDatabase.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );


        if (c.moveToFirst()) {
            String title = c.getString(c.getColumnIndex(ResolutionsDatabase.ResolutionsEntry.COLUMN_TITLE));
            String category = c.getString(c.getColumnIndex(ResolutionsDatabase.ResolutionsEntry.COLUMN_CATEGORY));

            Log.d(TAG, "addResolution: " + title + " " + category);
        }

        c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
