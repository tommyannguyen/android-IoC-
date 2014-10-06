package com.datalayer;

import javax.inject.Inject;
import javax.inject.Provider;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppReaderDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TrainingSpss.db";

    @Inject
    public AppReaderDbHelper(Provider<Context> contextProvider) {
        super(contextProvider.get(), DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataContract.SQL_CREATE_APPSETTING);
        db.execSQL(DataContract.SQL_CREATE_CATEGORY);
        db.execSQL(DataContract.SQL_CREATE_DOCUMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DataContract.SQL_DELETE_APPSETTING);
        db.execSQL(DataContract.SQL_DELETE_CATEGORY);
        db.execSQL(DataContract.SQL_DELETE_DOCUMENT);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
