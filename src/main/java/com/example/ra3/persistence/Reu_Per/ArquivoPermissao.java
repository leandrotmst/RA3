package com.example.ra3.persistence.Reu_Per;

import com.example.ra3.domains.Reu_Per.Permissao;
import com.example.ra3.exceptions.gestor.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPermissao {
    private static final String CAMINHO_ARQUIVO = "permissoes.dat";

    public static void salvarLista(List<Permissao> permissoes) throws PersistenceException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(new ArrayList<>(permissoes));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao salvar permissões", e);
        }
    }

    public static ArrayList<Permissao> lerLista() throws PersistenceException {
        File arq = new File(CAMINHO_ARQUIVO);

        if (!arq.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Permissao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao ler permissões", e);
        }
    }

    public static void adicionar(Permissao permissao) throws PersistenceException {
        ArrayList<Permissao> permissoes = lerLista();
        permissoes.add(permissao);
        salvarLista(permissoes);
    }

    public static void atualizar(Permissao antiga, Permissao nova) throws PersistenceException {
        ArrayList<Permissao> permissoes = lerLista();

        for (int i = 0; i < permissoes.size(); i++) {
            Permissao p = permissoes.get(i);

            if (p.getNome().equalsIgnoreCase(antiga.getNome())
                    && p.getModulo().equalsIgnoreCase(antiga.getModulo())) {

                permissoes.set(i, nova);
                salvarLista(permissoes);
                return;
            }
        }

        throw new PersistenceException("Permissão não encontrada para atualização.");
    }

    public static void excluir(Permissao permissao) throws PersistenceException {
        ArrayList<Permissao> permissoes = lerLista();

        boolean removeu = permissoes.removeIf(p ->
                p.getNome().equalsIgnoreCase(permissao.getNome())
                        && p.getModulo().equalsIgnoreCase(permissao.getModulo())
        );

        if (!removeu) {
            throw new PersistenceException("Permissão não encontrada para exclusão.");
        }

        salvarLista(permissoes);
    }
}