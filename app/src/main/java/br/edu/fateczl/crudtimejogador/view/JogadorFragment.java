/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.edu.fateczl.crudtimejogador.R;
import br.edu.fateczl.crudtimejogador.controller.JogadorController;
import br.edu.fateczl.crudtimejogador.controller.TimeController;
import br.edu.fateczl.crudtimejogador.model.Jogador;
import br.edu.fateczl.crudtimejogador.model.Time;
import br.edu.fateczl.crudtimejogador.persistence.JogadorDao;
import br.edu.fateczl.crudtimejogador.persistence.TimeDao;

public class JogadorFragment extends Fragment {
    private View view;
    private EditText etIdJogador, etNomeJogador, etDataNascJogador, etAlturaJogador, etPesoJogador;
    private Spinner spTimeJogador;
    private Button btnInserirJogador, btnAtualizarJogador, btnExcluirJogador, btnBuscarJogador, btnListarJogador;
    private TextView tvListaJogadores;
    private JogadorController jogadorController;
    private TimeController timeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        inicializarComponentes();
        configurarControllers();
        configurarListeners();
        preencherSpinnerTimes();

        return view;
    }

    private void inicializarComponentes() {
        etIdJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataNascJogador = view.findViewById(R.id.etDataNascJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);
        spTimeJogador = view.findViewById(R.id.spTimeJogador);
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnAtualizarJogador = view.findViewById(R.id.btnAtualizarJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        btnBuscarJogador = view.findViewById(R.id.btnBuscarJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);
        tvListaJogadores = view.findViewById(R.id.tvListaJogadores);
    }

    private void configurarControllers() {
        jogadorController = new JogadorController(new JogadorDao(getContext()));
        timeController = new TimeController(new TimeDao(getContext()));
    }

    private void configurarListeners() {
        btnInserirJogador.setOnClickListener(v -> inserirJogador());
        btnAtualizarJogador.setOnClickListener(v -> atualizarJogador());
        btnExcluirJogador.setOnClickListener(v -> excluirJogador());
        btnBuscarJogador.setOnClickListener(v -> buscarJogador());
        btnListarJogador.setOnClickListener(v -> listarJogadores());
    }

    private void preencherSpinnerTimes() {
        try {
            List<Time> times = timeController.listar();
            ArrayAdapter<Time> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, times);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimeJogador.setAdapter(adapter);
        } catch (SQLException e) {
            Toast.makeText(getContext(), "Erro ao carregar times: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void inserirJogador() {
        try {
            Jogador jogador = montarJogador();
            jogadorController.inserir(jogador);
            Toast.makeText(getContext(), "Jogador inserido com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao inserir jogador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarJogador() {
        try {
            Jogador jogador = montarJogador();
            jogadorController.atualizar(jogador);
            Toast.makeText(getContext(), "Jogador atualizado com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao atualizar jogador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirJogador() {
        try {
            Jogador jogador = new Jogador();
            jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));
            jogadorController.excluir(jogador);
            Toast.makeText(getContext(), "Jogador excluído com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao excluir jogador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarJogador() {
        try {
            int id = Integer.parseInt(etIdJogador.getText().toString());
            Jogador jogador = new Jogador();
            jogador.setId(id);
            jogador = jogadorController.buscar(jogador);
            if (jogador != null) {
                preencherCampos(jogador);
            } else {
                Toast.makeText(getContext(), "Jogador não encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao buscar jogador: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listarJogadores() {
        try {
            List<Jogador> jogadores = jogadorController.listar();
            StringBuilder sb = new StringBuilder();
            for (Jogador jogador : jogadores) {
                sb.append(jogador.toString()).append("\n");
            }
            tvListaJogadores.setText(sb.toString());
        } catch (SQLException e) {
            Toast.makeText(getContext(), "Erro ao listar jogadores: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Jogador montarJogador() {
        Jogador jogador = new Jogador();
        jogador.setId(Integer.parseInt(etIdJogador.getText().toString()));
        jogador.setNome(etNomeJogador.getText().toString());
        jogador.setDataNasc(LocalDate.parse(etDataNascJogador.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        jogador.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        jogador.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
        jogador.setTime((Time) spTimeJogador.getSelectedItem());
        return jogador;
    }

    private void preencherCampos(Jogador jogador) {
        etIdJogador.setText(String.valueOf(jogador.getId()));
        etNomeJogador.setText(jogador.getNome());
        etDataNascJogador.setText(jogador.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        etAlturaJogador.setText(String.valueOf(jogador.getAltura()));
        etPesoJogador.setText(String.valueOf(jogador.getPeso()));
        for (int i = 0; i < spTimeJogador.getCount(); i++) {
            Time time = (Time) spTimeJogador.getItemAtPosition(i);
            if (time.getCodigo() == jogador.getTime().getCodigo()) {
                spTimeJogador.setSelection(i);
                break;
            }
        }
    }

    private void limparCampos() {
        etIdJogador.setText("");
        etNomeJogador.setText("");
        etDataNascJogador.setText("");
        etAlturaJogador.setText("");
        etPesoJogador.setText("");
        spTimeJogador.setSelection(0);
    }
}