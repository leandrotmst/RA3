package com.example.ra3.persistence.administrador;

import com.example.ra3.domains.administrador.Cliente;

import java.io.*;
import java.util.ArrayList;

public class ArquivoCliente {

    private static final String CAMINHO_ARQUIVO = "clientes.dat";

    public static void salvarLista(ArrayList<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO_ARQUIVO))) {
            oos.writeObject(clientes);
            System.out.println("Lista de clientes salva com " + clientes.size() + " itens.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Cliente> lerLista() {
        ArrayList<Cliente> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
                lista = (ArrayList<Cliente>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler clientes: " + e.getMessage());
            }
        }
        return lista;
    }

    public static boolean adicionarCliente(Cliente novo) {
        ArrayList<Cliente> clientes = lerLista();
        if (existeCliente(novo, clientes, null)) {
            return false;
        }

        clientes.add(novo);
        salvarLista(clientes);
        System.out.println("Cliente adicionado: " + novo.getNome());
        return true;
    }

    public static Cliente buscarPorEmail(String email) {
        for (Cliente cliente : lerLista()) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null;
    }

    public static boolean atualizarCliente(Cliente antigo, Cliente atualizado) {
        ArrayList<Cliente> clientes = lerLista();
        if (existeCliente(atualizado, clientes, antigo)) {
            return false;
        }

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente.getCnpj().equalsIgnoreCase(antigo.getCnpj())) {
                clientes.set(i, atualizado);
                salvarLista(clientes);
                return true;
            }
        }
        return false;
    }

    public static void excluirCliente(Cliente clienteParaExcluir) {
        ArrayList<Cliente> restantes = new ArrayList<>();

        for (Cliente cliente : lerLista()) {
            if (!cliente.getCnpj().equalsIgnoreCase(clienteParaExcluir.getCnpj())) {
                restantes.add(cliente);
            }
        }

        salvarLista(restantes);
        System.out.println("Cliente excluido: " + clienteParaExcluir.getNome());
    }

    private static boolean existeCliente(Cliente novo, ArrayList<Cliente> clientes, Cliente ignorar) {
        for (Cliente cliente : clientes) {
            if (ignorar != null && cliente.getCnpj().equalsIgnoreCase(ignorar.getCnpj())) {
                continue;
            }

            boolean mesmoCnpj = cliente.getCnpj().equalsIgnoreCase(novo.getCnpj());
            boolean mesmoEmail = cliente.getEmail().equalsIgnoreCase(novo.getEmail());
            if (mesmoCnpj || mesmoEmail) {
                return true;
            }
        }
        return false;
    }
}
