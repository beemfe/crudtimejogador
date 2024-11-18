/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Time;
import br.edu.fateczl.crudtimejogador.persistence.TimeDao;

public class TimeController {
    private TimeDao timeDao;

    public TimeController(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public void inserir(Time time) throws SQLException {
        timeDao.open();
        timeDao.insert(time);
        timeDao.close();
    }

    public void atualizar(Time time) throws SQLException {
        timeDao.open();
        timeDao.update(time);
        timeDao.close();
    }

    public void excluir(Time time) throws SQLException {
        timeDao.open();
        timeDao.delete(time);
        timeDao.close();
    }

    public Time buscar(Time time) throws SQLException {
        timeDao.open();
        Time foundTime = timeDao.findOne(time);
        timeDao.close();
        return foundTime;
    }

    public List<Time> listar() throws SQLException {
        timeDao.open();
        List<Time> times = timeDao.findAll();
        timeDao.close();
        return times;
    }
}