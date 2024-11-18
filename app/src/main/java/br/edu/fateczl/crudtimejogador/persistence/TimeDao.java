/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Time;

public class TimeDao implements ICRUDDao<Time> {
    private SQLiteDatabase db;
    private GenericDao gDao;

    public TimeDao(Context context) {
        gDao = new GenericDao(context);
    }

    public void open() throws SQLException {
        db = gDao.getWritableDatabase();
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("codigo", time.getCodigo());
        values.put("nome", time.getNome());
        values.put("cidade", time.getCidade());

        db.insert("time", null, values);
    }

    @Override
    public void update(Time time) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", time.getNome());
        values.put("cidade", time.getCidade());

        db.update("time", values, "codigo = ?", new String[]{String.valueOf(time.getCodigo())});
    }

    @Override
    public void delete(Time time) throws SQLException {
        db.delete("time", "codigo = ?", new String[]{String.valueOf(time.getCodigo())});
    }

    @Override
    public Time findOne(Time time) throws SQLException {
        Cursor cursor = db.query("time", null, "codigo = ?",
                new String[]{String.valueOf(time.getCodigo())}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Time foundTime = cursorToTime(cursor);
        cursor.close();
        return foundTime;
    }

    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        Cursor cursor = db.query("time", null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Time time = cursorToTime(cursor);
            times.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    private Time cursorToTime(Cursor cursor) {
        Time time = new Time();
        time.setCodigo(cursor.getInt(0));
        time.setNome(cursor.getString(1));
        time.setCidade(cursor.getString(2));
        return time;
    }
}