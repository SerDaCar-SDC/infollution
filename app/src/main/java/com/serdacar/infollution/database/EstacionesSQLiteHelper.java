package com.serdacar.infollution.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serdacar.infollution.model.Estacion;

import java.util.ArrayList;

public class EstacionesSQLiteHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "Estaciones";
    static final int VERSION_DB = 4;

    static final String CREATE_TABLE_ESTACIONES =
            "CREATE TABLE " + EstacionContract.EstacionEntry.TABLE_NAME
                    + "( " + EstacionContract.EstacionEntry.COLUMN_ID  + " INTEGER PRIMARY KEY, "
                    + EstacionContract.EstacionEntry.COLUMN_NOMBRE + " TEXT UNIQUE, "
                    + EstacionContract.EstacionEntry.COLUMN_DIRECCION + " TEXT NOT NULL, "
                    + EstacionContract.EstacionEntry.COLUMN_LATITUD + " INTEGER NOT NULL, "
                    + EstacionContract.EstacionEntry.COLUMN_LONGITUD + " INTEGER NOT NULL);";

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

        listaEstaciones.add(new Estacion(4, "Pza. de España", "Plaza de España", 404238823, -37122567));
        listaEstaciones.add(new Estacion(8, "Escuelas Aguirre", "Entre C/ Alcalá y C/ O’ Donell", 404215533, -36823158));
        listaEstaciones.add(new Estacion(11, "Avda. Ramón y Cajal", "Avda. Ramón y Cajal  esq. C/ Príncipe de Vergara", 404514734, -36773491));
        listaEstaciones.add(new Estacion(16, "Arturo Soria", "C/ Arturo Soria  esq. C/  Vizconde de los Asilos", 404400457, -36392422));
        listaEstaciones.add(new Estacion(17, "Villaverde", "C/. Juan Peñalver", 40347147, -37133167));
        listaEstaciones.add(new Estacion(18, "Farolillo", "Calle Farolillo - C/Ervigio", 403947825, -37318356));
        listaEstaciones.add(new Estacion(24, "Casa de Campo", "Casa de Campo  (Terminal del Teleférico)", 404193577, -37473445));
        listaEstaciones.add(new Estacion(27, "Barajas Pueblo", "C/. Júpiter, 21 (Barajas)", 404769179, -35800258));
        listaEstaciones.add(new Estacion(35, "Pza. del Carmen", "Plaza del Carmen esq. Tres Cruces", 404192091, -37031662));
        listaEstaciones.add(new Estacion(36, "Moratalaz", "Avd. Moratalaz  esq. Camino de los Vinateros", 404079517, -36453104));
        listaEstaciones.add(new Estacion(38, "Cuatro Caminos", "Avda. Pablo Iglesias esq. C/ Marqués de Lema", 404455439, -37071303));
        listaEstaciones.add(new Estacion(39, "Barrio del Pilar", "Avd. Betanzos esq. C/  Monforte de Lemos", 404782322, -37115364));
        listaEstaciones.add(new Estacion(40, "Vallecas", "C/ Arroyo del Olivar  esq. C/  Río Grande", 403881478, -36515286));
        listaEstaciones.add(new Estacion(47, "Mendez Alvaro", "C/ Juan de Mariana / Pza. Amanecer Méndez Alvaro", 403980991, -36868138));
        listaEstaciones.add(new Estacion(48, "Castellana", "C/ José Gutiérrez Abascal", 404398904, -36903729));
        listaEstaciones.add(new Estacion(49, "Parque del Retiro", "Paseo Venezuela- Casa de Vacas", 404144444, -36824999));
        listaEstaciones.add(new Estacion(50, "Plaza Castilla", "Plaza Castilla (Canal)", 404655841, -36887449));
        listaEstaciones.add(new Estacion(54, "Ensanche de Vallecas", "Avda La Gavia / Avda. Las Suertes", 403730118, -36121394));
        listaEstaciones.add(new Estacion(55, "Urb. Embajada", "C/ Riaño (Barajas) ", 404623628, -35805649));
        listaEstaciones.add(new Estacion(56, "Pza. Elíptica", " Pza. Elíptica - Avda. Oporto", 403850336, -37187679));
        listaEstaciones.add(new Estacion(57, "Sanchinarro", "C/ Princesa de Éboli esq C/ María Tudor", 404942012, -36605173));
        listaEstaciones.add(new Estacion(58, "El Pardo", "Avda. La Guardia", 405180701, -37746101));
        listaEstaciones.add(new Estacion(59, "Juan Carlos I", "Parque Juan Carlos I (frente oficinas mantenimiento)", 404607255, -36163407));
        listaEstaciones.add(new Estacion(60, "Tres Olivos", "Plaza Tres Olivos", 405005477, -36897308));

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
