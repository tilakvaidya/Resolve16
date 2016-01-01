package com.nvapps.resolve;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateActivity extends AppCompatActivity implements
        FrequencyPickerDialogFragment.FrequencyDialogListener {

    private static final String TAG = "CreateActivity";

    @Bind(R.id.edit_title)
    EditText titleVal;
    @Bind(R.id.edit_category)
    EditText categoryVal;

    SQLiteDatabase db;

    String currentDate;
    int frequency = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new ResolutionsDatabase.ResolutionsDBHelper(this).getWritableDatabase();

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

    @OnClick(R.id.frequency_btn)
    public void setFrequency(View v){
        DialogFragment dialog = new FrequencyPickerDialogFragment();
        dialog.show(getSupportFragmentManager(), "FrequencyPicker");
    }

    @OnClick(R.id.add_resolution_btn)
    public void addResolution(View v) {

        String title = titleVal.getText().toString();
        String category = categoryVal.getText().toString();

        if (title.equals("") || category.equals(""))
            return;

        ContentValues values = new ContentValues();
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_TITLE, title);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_CATEGORY, category);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_FREQUENCY, frequency);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_RESOLVED, 0);
        values.put(ResolutionsDatabase.ResolutionsEntry.COLUMN_CHEATS, 0);

        long resolutionRowId = db.insert(ResolutionsDatabase.TABLE_NAME, null, values);

        if (resolutionRowId != -1)
            Log.d(TAG, "addResolution: Record inserted");

        titleVal.setText("");
        categoryVal.setText("");

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    public void onFinishedFrequencySelection(int selection) {
        frequency = selection;
    }
}
