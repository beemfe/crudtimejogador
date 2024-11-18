/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.controller;

import java.sql.SQLException;
import java.util.List;

public interface IController<T> {
    void inserir(T t) throws SQLException;
    void atualizar(T t) throws SQLException;
    void excluir(T t) throws SQLException;
    T buscar(T t) throws SQLException;
    List<T> listar() throws SQLException;
}