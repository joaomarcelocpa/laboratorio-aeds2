package clientes;

import pedidos.Pedido;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nome;
    private int documento;
    private List<Pedido> pedidos;

    public Cliente(String nome, int documento) {
        if (nome == null || !nome.contains(" ") || nome.split(" ").length < 2) {
            throw new IllegalArgumentException("Favor cadastrar cliente com um nome e um sobrenome");
        }
        if (documento < 10_000) {
            throw new IllegalArgumentException("Documentos válidos devem ter pelo menos 5 dígitos");
        }
        this.documento = documento;
        this.nome = nome;
        this.pedidos = new ArrayList<>();
    }

    public void adicionarPedido(Pedido novo) {
        if (novo != null) {
            pedidos.add(novo);
        }
    }

    public double totalGasto() {
        double total = 0.0;
        for (Pedido pedido : pedidos) {
            total += pedido.valorFinal();
        }
        return total;
    }

    public String getNome() {
        return nome;
    }

    public int getDocumento() {
        return documento;
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    @Override
    public String toString() {
        return nome + " (" + documento + ")";
    }

    @Override
    public int hashCode() {
        return documento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return documento == cliente.documento;
    }
}