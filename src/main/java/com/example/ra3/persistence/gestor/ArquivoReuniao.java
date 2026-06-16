package com.example.ra3.persistence.gestor;

import com.example.ra3.domains.gestor.Reuniao;
import com.example.ra3.exceptions.gestor.PersistenceException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArquivoReuniao {
    private static final String CAMINHO_ARQUIVO = "reunioes.dat";

    public static void salvarLista(List<Reuniao> reunioes) throws PersistenceException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(new ArrayList<>(reunioes));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao salvar reuniões", e);
        }
    }

    public static ArrayList<Reuniao> lerLista() throws PersistenceException {
        File arq = new File(CAMINHO_ARQUIVO);

        if (!arq.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
            return (ArrayList<Reuniao>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao ler reuniões", e);
        }
    }

    public static List<Reuniao> buscarPorGestor(String emailGestor) throws PersistenceException {
        return lerLista().stream()
                .filter(r -> r.getGestorEmail().equalsIgnoreCase(emailGestor))
                .collect(Collectors.toList());
    }

    public static void adicionar(Reuniao reuniao) throws PersistenceException {
        ArrayList<Reuniao> reunioes = lerLista();
        reunioes.add(reuniao);
        salvarLista(reunioes);
    }

    public static void atualizar(Reuniao antiga, Reuniao nova) throws PersistenceException {
        ArrayList<Reuniao> reunioes = lerLista();

        for (int i = 0; i < reunioes.size(); i++) {
            Reuniao r = reunioes.get(i);

            if (r.getTitulo().equalsIgnoreCase(antiga.getTitulo())
                    && r.getData().equalsIgnoreCase(antiga.getData())
                    && r.getHorario().equalsIgnoreCase(antiga.getHorario())
                    && r.getGestorEmail().equalsIgnoreCase(antiga.getGestorEmail())) {
                reunioes.set(i, nova);
                salvarLista(reunioes);
                return;
            }
        }

        throw new PersistenceException("Reunião não encontrada para atualização");
    }

    public static void excluir(Reuniao reuniao) throws PersistenceException {
        ArrayList<Reuniao> reunioes = lerLista();

        boolean removeu = reunioes.removeIf(r ->
                r.getTitulo().equalsIgnoreCase(reuniao.getTitulo())
                        && r.getData().equalsIgnoreCase(reuniao.getData())
                        && r.getHorario().equalsIgnoreCase(reuniao.getHorario())
                        && r.getGestorEmail().equalsIgnoreCase(reuniao.getGestorEmail())
        );

        if (!removeu) {
            throw new PersistenceException("Reunião não encontrada para exclusão");
        }

        salvarLista(reunioes);
    }
}