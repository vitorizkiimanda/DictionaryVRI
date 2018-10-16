package com.example.vitorizkiimanda.dictionaryvri;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract(){}

    public static String DATABASE_NAME = "dbdictionary";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_ID = "indonesia_words";
    public static final String TABLE_NAME_ENG = "english_words";

    static final class DictionaryColumns implements BaseColumns {

        // word
        static String ORIGINAL_WORD = "original_word";
        // content
        static String CONTENT_WORD = "content_word";

    }
}