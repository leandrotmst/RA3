package com.example.ra3.persistence;

import com.example.ra3.domains.Gestor;
import com.example.ra3.exceptions.FuncionarioException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoGestor {
    private static final String CAMINHO_ARQUIVO = "gestores.dat";

    public static void salvarLista(List<Gestor> gestores) throws FuncionarioException {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) arq.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq))) {
                oos.writeObject(new ArrayList<>(gestores));
            }
        } catch (IOException e) {
            throw new FuncionarioException("Erro ao salvar gestores", e);
        }
    }

    public static ArrayList<Gestor> lerLista() throws FuncionarioException {
        ArrayList<Gestor> lista = new ArrayList<>();
        File arq = new File(CAMINHO_ARQUIVO);
        if (!arq.exists()) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Gestor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FuncionarioException("Erro ao ler gestores", e);
        }
    }
}
