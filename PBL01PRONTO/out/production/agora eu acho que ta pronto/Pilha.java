

public class Pilha {
    private No topo = null;

    public Pilha() {
    }

    public boolean estaVazia() {
        return this.topo == null;
    }

    public void push(int x, int y) {
        No novoNo = new No(x, y);
        novoNo.setProximo(this.topo);
        this.topo = novoNo;
    }

    public No pop() {
        if (this.estaVazia()) {
            return null;
        } else {
            No noRemovido = this.topo;
            this.topo = this.topo.getProximo();
            return noRemovido;
        }
    }
}
