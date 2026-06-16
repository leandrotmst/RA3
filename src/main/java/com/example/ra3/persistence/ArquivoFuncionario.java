package com.example.ra3.persistence;

import com.example.ra3.domains.Funcionario;
import com.example.ra3.exceptions.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArquivoFuncionario {
    private static final String CAMINHO_ARQUIVO = "funcionarios.dat";

    public static void salvarLista(List<Funcionario> funcionarios) throws PersistenceException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(new ArrayList<>(funcionarios));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao salvar funcionários", e);
        }
    }

    public static ArrayList<Funcionario> lerLista() throws PersistenceException {
        File arq = new File(CAMINHO_ARQUIVO);
        if (!arq.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Funcionario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao ler funcionários", e);
        }
    }

    public static List<Funcionario> buscarPorGestor(String emailGestor) throws PersistenceException {
        return lerLista().stream()
                .filter(f -> f.getGestorEmail().equalsIgnoreCase(emailGestor))
                .collect(Collectors.toList());
    }

    public static void atualizarListaGeral(List<Funcionario> listaFiltrada, String emailGestor) throws PersistenceException {
        ArrayList<Funcionario> todas = lerLista();
        todas.removeIf(f -> f.getGestorEmail().equalsIgnoreCase(emailGestor));
        todas.addAll(listaFiltrada);
        salvarLista(todas);
    }
}
