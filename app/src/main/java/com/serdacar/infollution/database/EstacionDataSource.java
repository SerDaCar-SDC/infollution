package com.serdacar.infollution.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class EstacionDataSource {
    private Context contexto;
    private EstacionesSQLiteHelper estacionHelper;

    public EstacionDataSource (Context contexto) {
        this.contexto = contexto;
        estacionHelper = new EstacionesSQLiteHelper(contexto);
    }

    public SQLiteDatabase openReadable() {
        return estacionHelper.getReadableDatabase();
    }
    public SQLiteDatabase openWriteable() {
        return estacionHelper.getWritableDatabase();
    }
    public void close(SQLiteDatabase database) {
        database.close();
    }
}
