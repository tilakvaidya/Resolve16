package com.nvapps.resolve;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class ResolutionsDatabase {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ResolutionsEntry.TABLE_NAME + "(" +
                    ResolutionsEntry._ID + " INTEGER PRIMARY KEY, " +
                    ResolutionsEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ResolutionsEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ResolutionsEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    ResolutionsEntry.COLUMN_NAME_CHEAT_COUNTER + TEXT_TYPE +
                    ")";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ResolutionsEntry.TABLE_NAME;

    public ResolutionsDatabase() {
    }

    public static abstract class ResolutionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_CHEAT_COUNTER = "cc";
    }

    public static class ResolutionsDBHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "DataReader.db";

        public ResolutionsDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}
