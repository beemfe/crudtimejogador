/*
 *@author: Felipe Bernardes Cisilo
 */

package br.edu.fateczl.crudtimejogador.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.crudtimejogador.R;
import br.edu.fateczl.crudtimejogador.controller.TimeController;
import br.edu.fateczl.crudtimejogador.model.Time;
import br.edu.fateczl.crudtimejogador.persistence.TimeDao;

public class TimeFragment extends Fragment {
    private View view;
    private EditText etCodigoTime, etNomeTime, etCidadeTime;
    private Button btnInserirTime, btnAtualizarTime, btnExcluirTime, btnBuscarTime, btnListarTime;
    private TextView tvListaTimes;
    private TimeController timeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);

        inicializarComponentes();
        configurarController();
        configurarListeners();

        return view;
    }

    private void inicializarComponentes() {
        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnAtualizarTime = view.findViewById(R.id.btnAtualizarTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);
        tvListaTimes = view.findViewById(R.id.tvListaTimes);
    }

    private void configurarController() {
        timeController = new TimeController(new TimeDao(getContext()));
    }

    private void configurarListeners() {
        btnInserirTime.setOnClickListener(v -> inserirTime());
        btnAtualizarTime.setOnClickListener(v -> atualizarTime());
        btnExcluirTime.setOnClickListener(v -> excluirTime());
        btnBuscarTime.setOnClickListener(v -> buscarTime());
        btnListarTime.setOnClickListener(v -> listarTimes());
    }

    private void inserirTime() {
        try {
            Time time = montarTime();
            timeController.inserir(time);
            Toast.makeText(getContext(), "Time inserido com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao inserir time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarTime() {
        try {
            Time time = montarTime();
            timeController.atualizar(time);
            Toast.makeText(getContext(), "Time atualizado com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao atualizar time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirTime() {
        try {
            Time time = new Time();
            time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
            timeController.excluir(time);
            Toast.makeText(getContext(), "Time excluído com sucesso", Toast.LENGTH_SHORT).show();
            limparCampos();
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao excluir time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarTime() {
        try {
            int codigo = Integer.parseInt(etCodigoTime.getText().toString());
            Time time = new Time();
            time.setCodigo(codigo);
            time = timeController.buscar(time);
            if (time != null) {
                preencherCampos(time);
            } else {
                Toast.makeText(getContext(), "Time não encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException | IllegalArgumentException e) {
            Toast.makeText(getContext(), "Erro ao buscar time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listarTimes() {
        try {
            List<Time> times = timeController.listar();
            StringBuilder sb = new StringBuilder();
            for (Time time : times) {
                sb.append(time.toString()).append("\n");
            }
            tvListaTimes.setText(sb.toString());
        } catch (SQLException e) {
            Toast.makeText(getContext(), "Erro ao listar times: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Time montarTime() {
        Time time = new Time();
        time.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        time.setNome(etNomeTime.getText().toString());
        time.setCidade(etCidadeTime.getText().toString());
        return time;
    }

    private void preencherCampos(Time time) {
        etCodigoTime.setText(String.valueOf(time.getCodigo()));
        etNomeTime.setText(time.getNome());
        etCidadeTime.setText(time.getCidade());
    }

    private void limparCampos() {
        etCodigoTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }
}