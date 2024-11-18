/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TimesJogadores.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_TIME =
            "CREATE TABLE time (codigo INTEGER PRIMARY KEY, nome TEXT, cidade TEXT)";
    private static final String CREATE_TABLE_JOGADOR =
            "CREATE TABLE jogador (id INTEGER PRIMARY KEY, nome TEXT, dataNasc TEXT, " +
                    "altura REAL, peso REAL, idTime INTEGER, " +
                    "FOREIGN KEY(idTime) REFERENCES time(codigo))";

    public GenericDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIME);
        db.execSQL(CREATE_TABLE_JOGADOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS jogador");
        db.execSQL("DROP TABLE IF EXISTS time");
        onCreate(db);
    }
}