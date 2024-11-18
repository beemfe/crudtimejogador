/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.fateczl.crudtimejogador.model.Jogador;
import br.edu.fateczl.crudtimejogador.persistence.JogadorDao;

public class JogadorController {
    private JogadorDao jogadorDao;

    public JogadorController(JogadorDao jogadorDao) {
        this.jogadorDao = jogadorDao;
    }

    public void inserir(Jogador jogador) throws SQLException {
        jogadorDao.open();
        jogadorDao.insert(jogador);
        jogadorDao.close();
    }

    public void atualizar(Jogador jogador) throws SQLException {
        jogadorDao.open();
        jogadorDao.update(jogador);
        jogadorDao.close();
    }

    public void excluir(Jogador jogador) throws SQLException {
        jogadorDao.open();
        jogadorDao.delete(jogador);
        jogadorDao.close();
    }

    public Jogador buscar(Jogador jogador) throws SQLException {
        jogadorDao.open();
        Jogador foundJogador = jogadorDao.findOne(jogador);
        jogadorDao.close();
        return foundJogador;
    }

    public List<Jogador> listar() throws SQLException {
        jogadorDao.open();
        List<Jogador> jogadores = jogadorDao.findAll();
        jogadorDao.close();
        return jogadores;
    }

    public String localDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate stringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}