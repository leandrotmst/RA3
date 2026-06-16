package com.example.ra3.persistence.analista;

import com.example.ra3.domains.analista.Solucao;
import java.io.*;
import java.util.ArrayList;

public class ArquivoSolucao {

    private static final String CAMINHO_ARQUIVO = "solucoes.dat";

    // Salva a lista inteira
    public static void salvarLista(ArrayList<Solucao> solucoes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(solucoes);
            System.out.println("Lista de soluções salva com " + solucoes.size() + " itens.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar soluções: " + e.getMessage());
        }
    }

    // Lê a lista do arquivo (ou retorna vazia se não existir)
    @SuppressWarnings("unchecked")
    public static ArrayList<Solucao> lerLista() {
        ArrayList<Solucao> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
                lista = (ArrayList<Solucao>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler soluções: " + e.getMessage());
            }
        }
        return lista;
    }

    // Adiciona uma solução
    public static void adicionarSolucao(Solucao solucao) {
        ArrayList<Solucao> solucoes = lerLista();
        solucoes.add(solucao);
        salvarLista(solucoes);
        System.out.println("Solução adicionada: " + solucao.getTitulo());
    }

    // Busca soluções por email do analista (ignorando maiúsculas/minúsculas)
    public static ArrayList<Solucao> buscarPorAnalista(String emailAnalista) {
        String emailNorm = emailAnalista.trim().toLowerCase();
        ArrayList<Solucao> todas = lerLista();
        ArrayList<Solucao> resultado = new ArrayList<>();
        for (Solucao s : todas) {
            if (s.getEmailAnalista().trim().toLowerCase().equals(emailNorm)) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    // Atualiza uma solução
    public static void atualizarSolucao(Solucao antiga, Solucao nova) {
        ArrayList<Solucao> solucoes = lerLista();
        for (int i = 0; i < solucoes.size(); i++) {
            Solucao s = solucoes.get(i);
            if (s.getTitulo().equals(antiga.getTitulo()) &&
                    s.getEmailAnalista().equalsIgnoreCase(antiga.getEmailAnalista())) {
                solucoes.set(i, nova);
                break;
            }
        }
        salvarLista(solucoes);
    }

    // Exclui uma solução específica (compara título e email)
    public static void excluirSolucao(Solucao solucao) {
        ArrayList<Solucao> todas = lerLista();
        ArrayList<Solucao> restantes = new ArrayList<>();
        for (Solucao s : todas) {
            if (!(s.getTitulo().equals(solucao.getTitulo()) &&
                    s.getEmailAnalista().equalsIgnoreCase(solucao.getEmailAnalista()))) {
                restantes.add(s);
            }
        }
        salvarLista(restantes);
        System.out.println("Solução excluída: " + solucao.getTitulo());
    }

    // Exclui todas as soluções de um analista
    public static void excluirSolucoesDoAnalista(String email) {
        // Normaliza o email recebido (remove espaços e converte para minúsculas)
        String emailNormalizado = email.trim().toLowerCase();

        ArrayList<Solucao> todas = lerLista();
        ArrayList<Solucao> restantes = new ArrayList<>();

        for (Solucao s : todas) {
            // Normaliza o email da solução também
            String emailSolucao = s.getEmailAnalista().trim().toLowerCase();
            if (!emailSolucao.equals(emailNormalizado)) {
                restantes.add(s);
            }
        }

        salvarLista(restantes);
        System.out.println("Soluções restantes após exclusão: " + restantes.size());
    }
}