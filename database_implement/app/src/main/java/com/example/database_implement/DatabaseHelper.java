package com.example.database_implement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserPreferences.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "preferences";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_KEY = "pref_key";
    public static final String COLUMN_VALUE = "pref_value";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_KEY + " TEXT UNIQUE, " +
                    COLUMN_VALUE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to save or update a preference
    public boolean savePreference(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KEY, key);
        contentValues.put(COLUMN_VALUE, value);
        
        // Use conflict algorithm to update if key exists
        long result = db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return result != -1;
    }

    // Method to get a preference value by key
    public String getPreference(String key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_VALUE}, 
                COLUMN_KEY + "=?", new String[]{key}, null, null, null);
        
        String value = null;
        if (cursor.moveToFirst()) {
            int valueIndex = cursor.getColumnIndex(COLUMN_VALUE);
            if (valueIndex != -1) {
                value = cursor.getString(valueIndex);
            }
        }
        cursor.close();
        return value;
    }
}
