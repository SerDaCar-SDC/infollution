package com.serdacar.infollution.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.serdacar.infollution.model.Estacion;

import java.util.ArrayList;

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

    public Estacion leerEstacion(int idEstacion) {
        Estacion estacion = null;
        SQLiteDatabase database = openReadable();

        String query = "SELECT "
                + EstacionContract.EstacionEntry.COLUMN_ID
                + ", " + EstacionContract.EstacionEntry.COLUMN_NOMBRE
                + ", " + EstacionContract.EstacionEntry.COLUMN_DIRECCION
                + ", " + EstacionContract.EstacionEntry.COLUMN_LATITUD
                + ", " + EstacionContract.EstacionEntry.COLUMN_LONGITUD
                + " FROM " + EstacionContract.EstacionEntry.TABLE_NAME
                + " WHERE "
                + EstacionContract.EstacionEntry.COLUMN_ID + " = ?";

        String [] whereArgs = {String.valueOf(idEstacion)};

        Cursor cursor = database.rawQuery(query, whereArgs);

        int id;
        String nombre;
        String direccion;
        double latitud;
        double longitud;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_ID));
            nombre = cursor.getString(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_NOMBRE));
            direccion = cursor.getString(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_DIRECCION));
            latitud = cursor.getDouble(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_LATITUD));
            longitud = cursor.getDouble(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_LONGITUD));


            estacion = new Estacion(id, nombre, direccion, latitud, longitud);

        }

        cursor.close();
        close(database);

        return estacion;
    } 

    public int estacionPornombre(String nombre) {
        int idEstacion = 0;
        SQLiteDatabase database = openReadable();

        String query = "SELECT "
                + EstacionContract.EstacionEntry.COLUMN_ID
                + " FROM " + EstacionContract.EstacionEntry.TABLE_NAME
                + " WHERE "
                + EstacionContract.EstacionEntry.COLUMN_NOMBRE + " = ?";

        String [] whereArgs = {String.valueOf(nombre)};

        Cursor cursor = database.rawQuery(query, whereArgs);

        if (cursor.moveToFirst()) {
            idEstacion = cursor.getInt(cursor.getColumnIndex(
                    EstacionContract.EstacionEntry.COLUMN_ID));
        }

        cursor.close();
        close(database);

        return idEstacion;
    }

    public ArrayList<Estacion> leerEstacionLista() {
        ArrayList<Estacion> listaEstaciones = new ArrayList<Estacion>();
        SQLiteDatabase database = openReadable();

        String query = "SELECT "
                + EstacionContract.EstacionEntry.COLUMN_ID
                + ", " + EstacionContract.EstacionEntry.COLUMN_NOMBRE
                + ", " + EstacionContract.EstacionEntry.COLUMN_DIRECCION
                + ", " + EstacionContract.EstacionEntry.COLUMN_LATITUD
                + ", " + EstacionContract.EstacionEntry.COLUMN_LONGITUD
                + " FROM " + EstacionContract.EstacionEntry.TABLE_NAME;


        Cursor cursor = database.rawQuery(query, null);

        Estacion station = null;

        int id;
        String nombre;
        String direccion;
        double latitud;
        double longitud;

        if (cursor.moveToFirst()) {
            do {

                id = cursor.getInt(cursor.getColumnIndex(EstacionContract.EstacionEntry.COLUMN_ID));
                nombre = cursor.getString(cursor.getColumnIndex(EstacionContract.EstacionEntry.COLUMN_NOMBRE));
                direccion = cursor.getString(cursor.getColumnIndex(EstacionContract.EstacionEntry.COLUMN_DIRECCION));
                latitud = cursor.getDouble(cursor.getColumnIndex(EstacionContract.EstacionEntry.COLUMN_LATITUD));
                longitud = cursor.getDouble(cursor.getColumnIndex(EstacionContract.EstacionEntry.COLUMN_LONGITUD));

                station = new Estacion(id, nombre, direccion, latitud, longitud);

                listaEstaciones.add(station);

            } while (cursor.moveToNext());

        }

        cursor.close();
        close(database);

        return listaEstaciones;
    }

}
