package com.serdacar.infollution.database;

import android.provider.BaseColumns;

public class EstacionContract {

    public static abstract class EstacionEntry implements BaseColumns {

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NOMBRE = "NOMBRE";
        public static final String COLUMN_DIRECCION = "DIRECCION";
        public static final String COLUMN_LATITUD = "LATITUD";
        public static final String COLUMN_LONGITUD = "LONGITUD";
        public static final String TABLE_NAME = "INFO_ESTACIONES";
    }
}
