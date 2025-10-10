public class Fila {
    private No inicio;
    private No fim;

    public Fila() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean estaVazia() {
        return inicio == null;
    }

     // Adiciona  novo nó no fim da fila.

    public void enqueue(int x, int y) {
        No novoNo = new No(x, y);
        if (estaVazia()) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.setProximo(novoNo);
            fim = novoNo;
        }
    }

    // Remove e retorna o nó do início.

    public No dequeue() {
        if (estaVazia()) {
            return null;
        }
        No noRemovido = inicio;
        inicio = inicio.getProximo();

        if (inicio == null) {
            fim = null;
        }
        return noRemovido;
    }
}