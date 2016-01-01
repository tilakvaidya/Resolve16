package com.nvapps.resolve;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public final class ResolutionsDatabase {

    public static final String TABLE_NAME = "resolution";
    private static final String TAG = "ResolutionDatabase";
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ResolutionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ResolutionsEntry.COLUMN_TITLE + " TEXT, " +
                    ResolutionsEntry.COLUMN_CATEGORY + " TEXT, " +
                    ResolutionsEntry.COLUMN_FREQUENCY + " INTEGER, " +
                    ResolutionsEntry.COLUMN_RESOLVED + " INTEGER, " +
                    ResolutionsEntry.COLUMN_CHEATS + " INTEGER);";

    private static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ResolutionsDatabase() {
    }

    public static final class ResolutionsEntry implements BaseColumns {

        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_FREQUENCY = "frequency";
        public static final String COLUMN_RESOLVED = "resolved";
        public static final String COLUMN_CHEATS = "cheats";
    }

    public static class ResolutionsDBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "resolution.db";

        public ResolutionsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_QUERY);
            Log.d(TAG, "onCreate: Database created");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_QUERY);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
