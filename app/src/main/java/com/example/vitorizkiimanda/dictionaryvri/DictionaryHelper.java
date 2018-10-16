package com.example.vitorizkiimanda.dictionaryvri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DictionaryHelper {

    private static String DATABASE_TABLE_ID = DatabaseContract.TABLE_NAME_ID;
    private static String DATABASE_TABLE_ENG = DatabaseContract.TABLE_NAME_ENG;

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public DictionaryHelper(Context context){
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }


    public List<DictionaryModel> getAllData(boolean english){
        String TABLE = english ? DATABASE_TABLE_ENG : DATABASE_TABLE_ID;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();

        String q = "SELECT * FROM "+ TABLE +" ORDER BY " + DatabaseContract.DictionaryColumns.ORIGINAL_WORD+" ASC";

        Cursor cursor = database.rawQuery(q, null);
//        Log.d("dictionaryModelList", String.valueOf(dictionaryModelList.size()));
        if(cursor.moveToFirst()){
            do{
                DictionaryModel dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.DictionaryColumns._ID)));
                dictionaryModel.setOriginal_word(cursor.getString(cursor.getColumnIndex(DatabaseContract.DictionaryColumns.ORIGINAL_WORD)));
                dictionaryModel.setContent_word(cursor.getString(cursor.getColumnIndex(DatabaseContract.DictionaryColumns.CONTENT_WORD)));
                dictionaryModelList.add(dictionaryModel);
            }while(cursor.moveToNext());
        }

        Log.d("dictionaryModel-58", String.valueOf(dictionaryModelList.size()));
        cursor.close();
        return dictionaryModelList;
    }

    public void insertTransaction(List<DictionaryModel> dictionaryModelListList, boolean english){
        String TABLE = english ? DATABASE_TABLE_ENG : DATABASE_TABLE_ID;


        String q = "INSERT INTO " + TABLE + " (" +
                DatabaseContract.DictionaryColumns.ORIGINAL_WORD + ", " +
                DatabaseContract.DictionaryColumns.CONTENT_WORD + ") VALUES (?, ?)";

        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(q);

        for (DictionaryModel data : dictionaryModelListList) {
            stmt.bindString(1, data.getOriginal_word());
            stmt.bindString(2, data.getContent_word());
            stmt.execute();
            stmt.clearBindings();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public List<DictionaryModel> getByName(String query, boolean english){
        String TABLE = english ? DATABASE_TABLE_ENG : DATABASE_TABLE_ID;
        List<DictionaryModel> dictionaryModelList = new ArrayList<DictionaryModel>();

//        Log.d("input di helper :", query);

        String q = "SELECT * FROM "+ TABLE +" WHERE " + DatabaseContract.DictionaryColumns.ORIGINAL_WORD
                +" LIKE '%" + query.trim() + "%'";
//        Log.d("nilai q :", q);
        Cursor cursor = database.rawQuery(q, null);

        cursor.moveToFirst();

        if(cursor.moveToFirst()){
            do{
                DictionaryModel dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.DictionaryColumns._ID)));
                dictionaryModel.setOriginal_word(cursor.getString(cursor.getColumnIndex(DatabaseContract.DictionaryColumns.ORIGINAL_WORD)));
                dictionaryModel.setContent_word(cursor.getString(cursor.getColumnIndex(DatabaseContract.DictionaryColumns.CONTENT_WORD)));
                dictionaryModelList.add(dictionaryModel);
            }while(cursor.moveToNext());
        }
        Log.d("dictionaryModelList :", String.valueOf(dictionaryModelList.size()));
        cursor.close();
        return dictionaryModelList;
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

}
