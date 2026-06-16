package com.example.alignupyumi.persistence;

import com.example.alignupyumi.domains.Analista;

import java.io.*;
import java.util.ArrayList;

//Classe responsável por salvar e ler objetos Analista em arquivo (material base Exemplo 10 - JavaFX - estudos)

public class ArquivoAnalista {

    // Nome fixo do arquivo onde os dados serão persistidos
    private static final String CAMINHO_ARQUIVO = "analistas.dat";

    // Salva toda a lista de analistas no arquivo
    public static void salvarLista(ArrayList<Analista> analistas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(analistas);
            System.out.println("Analistas salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar analistas: " + e.getMessage());
        }
    }

    // Lê a lista de analistas do arquivo - Se o arquivo não existir, retorna lista vazia
    @SuppressWarnings("unchecked")
    public static ArrayList<Analista> lerLista() {
        ArrayList<Analista> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
                lista = (ArrayList<Analista>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler analistas: " + e.getMessage());
            }
        }
        return lista;
    }

    // Adiciona um novo analista, verificando se o e-mail já existe
    public static boolean adicionarAnalista(Analista novo) {
        ArrayList<Analista> analistas = lerLista();

        // Verifica se o email já existe
        for (Analista a : analistas) {
            if (a.getEmail().equalsIgnoreCase(novo.getEmail())) {
                return false;   //Email já cadastrado
            }
        }
        analistas.add(novo);
        salvarLista(analistas);
        return true;
    }

    // Busca um analista pelo e-mail - Retorna null se não encontrar
    public static Analista buscarPorEmail(String email) {
        for (Analista a : lerLista()) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                return a;
            }
        }
        return null;
    }

    // Atualiza dados do analista (usando email como chave)
    public static boolean atualizarAnalista(Analista atualizado) {
        ArrayList<Analista> analistas = lerLista();
        for (int i = 0; i < analistas.size(); i++) {
            if (analistas.get(i).getEmail().equalsIgnoreCase(atualizado.getEmail())) {
                analistas.set(i, atualizado);
                salvarLista(analistas);
                return true;
            }
        }
        return false;
    }

    // Excluir analista por email
    public static boolean excluirAnalista(String email) {
        String emailNorm = email.trim().toLowerCase();
        ArrayList<Analista> todos = lerLista();
        ArrayList<Analista> restantes = new ArrayList<>();
        for (Analista a : todos) {
            if (!a.getEmail().trim().toLowerCase().equals(emailNorm)) {
                restantes.add(a);
            }
        }
        boolean removido = restantes.size() < todos.size();
        if (removido) salvarLista(restantes);
        return removido;
    }
}
