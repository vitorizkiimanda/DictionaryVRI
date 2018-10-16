package com.example.vitorizkiimanda.dictionaryvri;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.DATABASE_NAME;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.DATABASE_VERSION;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.DictionaryColumns.CONTENT_WORD;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.DictionaryColumns.ORIGINAL_WORD;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.TABLE_NAME_ENG;
import static com.example.vitorizkiimanda.dictionaryvri.DatabaseContract.TABLE_NAME_ID;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE_INDO =
            "CREATE TABLE " + TABLE_NAME_ID + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    ORIGINAL_WORD + TEXT_TYPE + COMMA_SEP +
                    CONTENT_WORD + TEXT_TYPE + " )";
    private static final String SQL_CREATE_TABLE_ENG =
            "CREATE TABLE " + TABLE_NAME_ENG + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    ORIGINAL_WORD + TEXT_TYPE + COMMA_SEP +
                    CONTENT_WORD + TEXT_TYPE + " )";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INDO);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ENG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ID);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENG);
        onCreate(sqLiteDatabase);
    }
}