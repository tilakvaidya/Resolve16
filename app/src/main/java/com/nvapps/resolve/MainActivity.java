package com.nvapps.resolve;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateActivity.class));
            }
        });

        readDatabase();
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
