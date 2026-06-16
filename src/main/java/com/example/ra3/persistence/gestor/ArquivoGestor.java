package com.example.ra3.persistence.gestor;

import com.example.ra3.domains.gestor.Gestor;
import com.example.ra3.exceptions.gestor.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoGestor {
    private static final String CAMINHO_ARQUIVO = "gestores.dat";

    public static void salvarLista(List<Gestor> gestores) throws PersistenceException {
        try {
            File arq = new File(CAMINHO_ARQUIVO);
            if (!arq.exists()) arq.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arq))) {
                oos.writeObject(new ArrayList<>(gestores));
            }
        } catch (IOException e) {
            throw new PersistenceException("Erro ao salvar gestores", e);
        }
    }

    public static ArrayList<Gestor> lerLista() throws PersistenceException {
        ArrayList<Gestor> lista = new ArrayList<>();
        File arq = new File(CAMINHO_ARQUIVO);
        if (!arq.exists()) return lista;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Gestor>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao ler gestores", e);
        }
    }

    public static Gestor buscarPorEmail(String email) throws PersistenceException {
        for (Gestor g : lerLista()) {
            if (g.getEmail().equalsIgnoreCase(email)) return g;
        }
        return null;
    }

    public static void adicionarGestor(Gestor gestor) throws PersistenceException {
        ArrayList<Gestor> lista = lerLista();
        lista.add(gestor);
        salvarLista(lista);
    }
}
