public class Pilha {
    private Ponto[] elementos;
    private int topo;
    private int capacidade;

    public Pilha(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new Ponto[capacidade];
        this.topo = -1;
    }

    public void empilhar(Ponto ponto) {
        if (topo >= capacidade - 1) {
            redimensionar();
        }
        elementos[++topo] = ponto;
    }

    public Ponto desempilhar() {
        if (vazia()) {
            return null;
        }
        return elementos[topo--];
    }

    public boolean vazia() {
        return topo == -1;
    }

    public int tamanho() {
        return topo + 1;
    }

    private void redimensionar() {
        capacidade *= 2;
        Ponto[] novoArray = new Ponto[capacidade];
        System.arraycopy(elementos, 0, novoArray, 0, topo + 1);
        elementos = novoArray;
    }
}