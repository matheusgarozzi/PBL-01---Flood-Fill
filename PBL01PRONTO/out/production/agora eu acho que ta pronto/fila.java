

public class Fila {
    private No inicio = null;
    private No fim = null;

    public Fila() {
    }

    public boolean estaVazia() {
        return this.inicio == null;
    }

    public void enqueue(int x, int y) {
        No novoNo = new No(x, y);
        if (this.estaVazia()) {
            this.inicio = novoNo;
            this.fim = novoNo;
        } else {
            this.fim.setProximo(novoNo);
            this.fim = novoNo;
        }

    }

    public No dequeue() {
        if (this.estaVazia()) {
            return null;
        } else {
            No noRemovido = this.inicio;
            this.inicio = this.inicio.getProximo();
            if (this.inicio == null) {
                this.fim = null;
            }

            return noRemovido;
        }
    }
}
