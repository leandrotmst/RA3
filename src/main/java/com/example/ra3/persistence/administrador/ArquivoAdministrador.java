package com.example.ra3.persistence.administrador;

import com.example.ra3.domains.administrador.Administrador;

import java.io.*;
import java.util.ArrayList;

public class ArquivoAdministrador {

    private static final String CAMINHO_ARQUIVO = "administradores.dat";

    public static void salvarLista(ArrayList<Administrador> administradores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(administradores);
            System.out.println("Administradores salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar administradores: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Administrador> lerLista() {
        ArrayList<Administrador> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
                lista = (ArrayList<Administrador>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler administradores: " + e.getMessage());
            }
        }
        return lista;
    }

    public static boolean adicionarAdministrador(Administrador novo) {
        ArrayList<Administrador> administradores = lerLista();

        for (Administrador administrador : administradores) {
            if (administrador.getEmail().equalsIgnoreCase(novo.getEmail())) {
                return false;
            }
        }

        administradores.add(novo);
        salvarLista(administradores);
        return true;
    }

    public static Administrador buscarPorEmail(String email) {
        for (Administrador administrador : lerLista()) {
            if (administrador.getEmail().equalsIgnoreCase(email)) {
                return administrador;
            }
        }
        return null;
    }

    public static boolean atualizarAdministrador(Administrador atualizado) {
        ArrayList<Administrador> administradores = lerLista();
        for (int i = 0; i < administradores.size(); i++) {
            if (administradores.get(i).getEmail().equalsIgnoreCase(atualizado.getEmail())) {
                administradores.set(i, atualizado);
                salvarLista(administradores);
                return true;
            }
        }
        return false;
    }

    public static boolean excluirAdministrador(String email) {
        String emailNormalizado = email.trim().toLowerCase();
        ArrayList<Administrador> todos = lerLista();
        ArrayList<Administrador> restantes = new ArrayList<>();

        for (Administrador administrador : todos) {
            if (!administrador.getEmail().trim().toLowerCase().equals(emailNormalizado)) {
                restantes.add(administrador);
            }
        }

        boolean removido = restantes.size() < todos.size();
        if (removido) {
            salvarLista(restantes);
        }
        return removido;
    }
}
