package com.example.ra3.persistence.administrador;

import com.example.ra3.domains.gestor.Gestor;
import com.example.ra3.exceptions.gestor.PersistenceException;

import java.util.ArrayList;

public class ArquivoGestor {

    public static void salvarLista(ArrayList<Gestor> gestores) {
        try {
            com.example.ra3.persistence.gestor.ArquivoGestor.salvarLista(gestores);
            System.out.println("Lista de gestores salva com " + gestores.size() + " itens.");
        } catch (PersistenceException e) {
            System.err.println("Erro ao salvar gestores: " + e.getMessage());
        }
    }

    public static ArrayList<Gestor> lerLista() {
        try {
            return com.example.ra3.persistence.gestor.ArquivoGestor.lerLista();
        } catch (PersistenceException e) {
            System.err.println("Erro ao ler gestores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean adicionarGestor(Gestor novo) {
        ArrayList<Gestor> gestores = lerLista();
        if (existeEmail(novo.getEmail(), gestores, null)) {
            return false;
        }

        gestores.add(novo);
        salvarLista(gestores);
        System.out.println("Gestor adicionado: " + novo.getNome());
        return true;
    }

    public static Gestor buscarPorEmail(String email) {
        String emailNormalizado = normalizar(email);
        for (Gestor gestor : lerLista()) {
            if (normalizar(gestor.getEmail()).equals(emailNormalizado)) {
                return gestor;
            }
        }
        return null;
    }

    public static boolean atualizarGestor(Gestor antigo, Gestor atualizado) {
        ArrayList<Gestor> gestores = lerLista();
        if (existeEmail(atualizado.getEmail(), gestores, antigo)) {
            return false;
        }

        for (int i = 0; i < gestores.size(); i++) {
            if (normalizar(gestores.get(i).getEmail()).equals(normalizar(antigo.getEmail()))) {
                gestores.set(i, atualizado);
                salvarLista(gestores);
                return true;
            }
        }
        return false;
    }

    public static void excluirGestor(Gestor gestorParaExcluir) {
        ArrayList<Gestor> restantes = new ArrayList<>();
        String emailParaExcluir = normalizar(gestorParaExcluir.getEmail());

        for (Gestor gestor : lerLista()) {
            if (!normalizar(gestor.getEmail()).equals(emailParaExcluir)) {
                restantes.add(gestor);
            }
        }

        salvarLista(restantes);
        System.out.println("Gestor excluido: " + gestorParaExcluir.getNome());
    }

    private static boolean existeEmail(String email, ArrayList<Gestor> gestores, Gestor ignorar) {
        String emailNormalizado = normalizar(email);
        String emailIgnorado = ignorar == null ? null : normalizar(ignorar.getEmail());

        for (Gestor gestor : gestores) {
            String emailGestor = normalizar(gestor.getEmail());
            if (emailIgnorado != null && emailGestor.equals(emailIgnorado)) {
                continue;
            }
            if (emailGestor.equals(emailNormalizado)) {
                return true;
            }
        }
        return false;
    }

    private static String normalizar(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
    }
}
