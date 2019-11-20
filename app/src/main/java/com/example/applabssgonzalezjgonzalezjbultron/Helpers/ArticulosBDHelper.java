package com.example.applabssgonzalezjgonzalezjbultron.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ArticulosBDHelper extends SQLiteOpenHelper {

    String queryCreate = "CREATE TABLE articulos(id INTEGER PRIMARY KEY, nombre TEXT, cantidad TEXT, estado TEXT, precio TEXT, dire TEXT, usuario TEXT)";//imagen BLOB,

    public ArticulosBDHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
