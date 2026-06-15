package com.example.ra3.persistence;

import com.example.ra3.domains.Funcionario;
import com.example.ra3.exceptions.FuncionarioException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoFuncionario {
    private static final String CAMINHO_ARQUIVO = "funcionarios.dat";

    public static void salvarLista(List<Funcionario> funcionarios) throws FuncionarioException {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) {
                arq.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq));
            oos.writeObject(funcionarios);
            oos.close();
            System.out.println("Lista de funcionários salva com sucesso.");
        } catch (IOException e) {
            throw new FuncionarioException("Erro ao salvar funcionários", e);
        }
    }

    public static ArrayList<Funcionario> lerLista() throws FuncionarioException {
        ArrayList<Funcionario> lista = new ArrayList<>();
        File arq = new File(CAMINHO_ARQUIVO);
        if (!arq.exists()) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Funcionario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FuncionarioException("Erro ao ler funcionários", e);
        }
    }
}
