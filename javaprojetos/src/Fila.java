public class Fila {
    private Ponto[] elementos;
    private int frente;
    private int tras;
    private int tamanho;
    private int capacidade;

    public Fila(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new Ponto[capacidade];
        this.frente = 0;
        this.tras = -1;
        this.tamanho = 0;
    }

    public void enfileirar(Ponto ponto) {
        if (tamanho >= capacidade) {
            redimensionar();
        }
        tras = (tras + 1) % capacidade;
        elementos[tras] = ponto;
        tamanho++;
    }

    public Ponto desenfileirar() {
        if (vazia()) {
            return null;
        }
        Ponto ponto = elementos[frente];
        elementos[frente] = null;
        frente = (frente + 1) % capacidade;
        tamanho--;
        return ponto;
    }

    public boolean vazia() {
        return tamanho == 0;
    }

    public int tamanho() {
        return tamanho;
    }

    private void redimensionar() {
        int novaCapacidade = capacidade * 2;
        Ponto[] novoArray = new Ponto[novaCapacidade];

        for (int i = 0; i < tamanho; i++) {
            novoArray[i] = elementos[(frente + i) % capacidade];
        }

        elementos = novoArray;
        frente = 0;
        tras = tamanho - 1;
        capacidade = novaCapacidade;
    }
}
