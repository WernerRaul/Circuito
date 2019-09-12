package com.example.raul.circuito.Clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ConexionSQLiteHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "DATOS";
    private static final int DATABASE_VERSION = 1;

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
    }
}
