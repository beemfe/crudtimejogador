/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Jogador;
import br.edu.fateczl.crudtimejogador.model.Time;

public class JogadorDao implements ICRUDDao<Jogador> {
    private SQLiteDatabase db;
    private GenericDao gDao;

    public JogadorDao(Context context) {
        gDao = new GenericDao(context);
    }

    public void open() throws SQLException {
        db = gDao.getWritableDatabase();
    }

    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", jogador.getNome());
        values.put("dataNasc", jogador.getDataNasc().toString());
        values.put("altura", jogador.getAltura());
        values.put("peso", jogador.getPeso());
        values.put("idTime", jogador.getTime().getCodigo());

        db.insert("jogador", null, values);
    }

    @Override
    public void update(Jogador jogador) throws SQLException {
        ContentValues values = new ContentValues();
        values.put("nome", jogador.getNome());
        values.put("dataNasc", jogador.getDataNasc().toString());
        values.put("altura", jogador.getAltura());
        values.put("peso", jogador.getPeso());
        values.put("idTime", jogador.getTime().getCodigo());

        db.update("jogador", values, "id = ?", new String[]{String.valueOf(jogador.getId())});
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        db.delete("jogador", "id = ?", new String[]{String.valueOf(jogador.getId())});
    }

    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        Cursor cursor = db.rawQuery("SELECT j.*, t.nome as time_nome, t.cidade as time_cidade " +
                "FROM jogador j INNER JOIN time t ON j.idTime = t.codigo " +
                "WHERE j.id = ?", new String[]{String.valueOf(jogador.getId())});

        if (cursor != null)
            cursor.moveToFirst();

        Jogador foundJogador = cursorToJogador(cursor);
        cursor.close();
        return foundJogador;
    }

    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT j.*, t.nome as time_nome, t.cidade as time_cidade " +
                "FROM jogador j INNER JOIN time t ON j.idTime = t.codigo", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Jogador jogador = cursorToJogador(cursor);
            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    @SuppressLint("Range")
    private Jogador cursorToJogador(Cursor cursor) {
        Jogador jogador = new Jogador();
        jogador.setId(cursor.getInt(cursor.getColumnIndex("id")));
        jogador.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataNasc"))));
        jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura")));
        jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso")));

        Time time = new Time();
        time.setCodigo(cursor.getInt(cursor.getColumnIndex("idTime")));
        time.setNome(cursor.getString(cursor.getColumnIndex("time_nome")));
        time.setCidade(cursor.getString(cursor.getColumnIndex("time_cidade")));
        jogador.setTime(time);

        return jogador;
    }
}
