package busca;

public interface IBuscador<T> {
    T buscar(String nome);
    long getComparacoes();
    double getTempo();
}