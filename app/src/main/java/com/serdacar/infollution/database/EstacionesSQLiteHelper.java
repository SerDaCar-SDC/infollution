package com.serdacar.infollution.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serdacar.infollution.model.Estacion;

import java.util.ArrayList;

public class EstacionesSQLiteHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Estaciones";
    static final int VERSION_DB = 2;

    static final String CREATE_TABLE_ESTACIONES =
            "CREATE TABLE " + EstacionContract.EstacionEntry.TABLE_NAME
                    + "( " + EstacionContract.EstacionEntry.COLUMN_ID  + " INTEGER NOT NULL,"
                    + EstacionContract.EstacionEntry.COLUMN_NOMBRE + " TEXT UNIQUE,"
                    + EstacionContract.EstacionEntry.COLUMN_DIRECCION + " TEXT NOT NULL,"
                    + EstacionContract.EstacionEntry.COLUMN_LATITUD + " REAL NOT NULL,"
                    + EstacionContract.EstacionEntry.COLUMN_LONGITUD + " REAL NOT NULL);";

    public EstacionesSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_DB);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ESTACIONES);
        cargaInicial(db);
    }

    private void cargaInicial(SQLiteDatabase db) {
        ArrayList<Estacion> listaEstaciones = new ArrayList<Estacion>();

        listaEstaciones.add(new Estacion(4, "Pza. de España", "Plaza de España", 40.4238823, -3.7122567));
        listaEstaciones.add(new Estacion(8, "Escuelas Aguirre", "Entre C/ Alcalá y C/ O’ Donell", 40.4215533, -3.6823158));
        listaEstaciones.add(new Estacion(11, "Avda. Ramón y Cajal", "Avda. Ramón y Cajal  esq. C/ Príncipe de Vergara", 40.4514734, -3.6773491));
        listaEstaciones.add(new Estacion(16, "Arturo Soria", "C/ Arturo Soria  esq. C/  Vizconde de los Asilos", 40.4400457, -3.6392422));
        listaEstaciones.add(new Estacion(17, "Villaverde", "C/. Juan Peñalver", 40.347147, -3.7133167));
        listaEstaciones.add(new Estacion(18, "Farolillo", "Calle Farolillo - C/Ervigio", 40.3947825, -3.7318356));
        listaEstaciones.add(new Estacion(24, "Casa de Campo", "Casa de Campo  (Terminal del Teleférico)", 40.4193577, -3.7473445));
        listaEstaciones.add(new Estacion(27, "Barajas Pueblo", "C/. Júpiter, 21 (Barajas)", 40.4769179, -3.5800258));
        listaEstaciones.add(new Estacion(35, "Pza. del Carmen", "Plaza del Carmen esq. Tres Cruces", 40.4192091, -3.7031662));
        listaEstaciones.add(new Estacion(36, "Moratalaz", "Avd. Moratalaz  esq. Camino de los Vinateros", 40.4079517, -3.6453104));
        listaEstaciones.add(new Estacion(38, "Cuatro Caminos", "Avda. Pablo Iglesias esq. C/ Marqués de Lema", 40.4455439, -3.7071303));
        listaEstaciones.add(new Estacion(39, "Barrio del Pilar", "Avd. Betanzos esq. C/  Monforte de Lemos", 40.4782322, -3.7115364));
        listaEstaciones.add(new Estacion(40, "Vallecas", "C/ Arroyo del Olivar  esq. C/  Río Grande", 40.3881478, -3.6515286));
        listaEstaciones.add(new Estacion(47, "Mendez Alvaro", "C/ Juan de Mariana / Pza. Amanecer Méndez Alvaro", 40.3980991, -3.6868138));
        listaEstaciones.add(new Estacion(48, "Castellana", "C/ José Gutiérrez Abascal", 40.4398904, -3.6903729));
        listaEstaciones.add(new Estacion(49, "Parque del Retiro", "Paseo Venezuela- Casa de Vacas", 40.4144444, -3.6824999));
        listaEstaciones.add(new Estacion(50, "Plaza Castilla", "Plaza Castilla (Canal)", 40.4655841, -3.6887449));
        listaEstaciones.add(new Estacion(54, "Ensanche de Vallecas", "Avda La Gavia / Avda. Las Suertes", 40.3730118, -3.6121394));
        listaEstaciones.add(new Estacion(55, "Urb. Embajada", "C/ Riaño (Barajas) ", 40.4623628, -3.5805649));
        listaEstaciones.add(new Estacion(56, "Pza. Elíptica", " Pza. Elíptica - Avda. Oporto", 40.3850336, -3.7187679));
        listaEstaciones.add(new Estacion(57, "Sanchinarro", "C/ Princesa de Éboli esq C/ María Tudor", 40.4942012, -3.6605173));
        listaEstaciones.add(new Estacion(58, "El Pardo", "Avda. La Guardia", 40.5180701, -3.7746101));
        listaEstaciones.add(new Estacion(59, "Juan Carlos I", "Parque Juan Carlos I (frente oficinas mantenimiento)", 40.4607255, -3.6163407));
        listaEstaciones.add(new Estacion(60, "Tres Olivos", "Plaza Tres Olivos", 40.5005477, -3.6897308));

        db.beginTransaction();
        ContentValues infoValues;

        for (int i = 0; i < listaEstaciones.size(); i++) {
            infoValues = new ContentValues();

            infoValues.put(EstacionContract.EstacionEntry.COLUMN_ID, listaEstaciones.get(i).getCodigoCorto());
            infoValues.put(EstacionContract.EstacionEntry.COLUMN_NOMBRE, listaEstaciones.get(i).getNombre());
            infoValues.put(EstacionContract.EstacionEntry.COLUMN_DIRECCION, listaEstaciones.get(i).getDireccion());
            infoValues.put(EstacionContract.EstacionEntry.COLUMN_LATITUD, listaEstaciones.get(i).getLatitud());
            infoValues.put(EstacionContract.EstacionEntry.COLUMN_LONGITUD, listaEstaciones.get(i).getLongitud());

            db.insert(EstacionContract.EstacionEntry.TABLE_NAME, null, infoValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +
                EstacionContract.EstacionEntry.TABLE_NAME);
        onCreate(db);
    }
}
