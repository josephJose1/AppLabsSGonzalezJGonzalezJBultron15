package com.example.applabssgonzalezjgonzalezjbultron.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class datosHelper extends SQLiteOpenHelper {

    String queryCreate = "CREATE TABLE usuariosR(nombre TEXT PRIMARY KEY, contra TEXT, tipo TEXT)";

    public datosHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
