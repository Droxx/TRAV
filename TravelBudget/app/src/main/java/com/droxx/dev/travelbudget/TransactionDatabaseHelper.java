package com.droxx.dev.travelbudget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Michael on 25/08/2015.
 */

public final class TransactionDatabaseHelper extends SQLiteOpenHelper {

    public abstract class TransactionEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_DATE_ID = "date";
        public static final String COLUMN_NAME_COUNTRY_ID = "contry";
        public static final String COLUMN_NAME_CATEGORY_ID = "category";
        public static final String COLUMN_NAME_NOTE_ID = "note";
        public static final String COLUMN_NAME_LOCAL_ID = "local";
        public static final String COLUMN_NAME_VALUE_ID = "value";

        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String AUTOINCREMENT_PROPERTY = " AUTOINCREMENT";
        private static final String REAL_TYPE = " REAL";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TransactionEntry.TABLE_NAME + " (" +
                        TransactionEntry._ID + " INTEGER PRIMARY KEY," +
                        TransactionEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + AUTOINCREMENT_PROPERTY + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_DATE_ID + TEXT_TYPE + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_COUNTRY_ID + TEXT_TYPE + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_CATEGORY_ID + TEXT_TYPE + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_NOTE_ID + TEXT_TYPE + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_LOCAL_ID + INTEGER_TYPE + COMMA_SEP +
                        TransactionEntry.COLUMN_NAME_VALUE_ID + REAL_TYPE + COMMA_SEP +
                        " )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TransactionEntry.TABLE_NAME;


    }

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Transactions.db";

    public TransactionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TransactionEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(TransactionEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}





