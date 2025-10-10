public class Pilha {
    private No topo;

    public Pilha() {
        this.topo = null;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    // Adiciona novo nó no topo da pilha.
    public void push(int x, int y) {
        No novoNo = new No(x, y);
        novoNo.setProximo(topo);
        topo = novoNo;
    }

    // Remove e retorna o nó do topo.

    public No pop() {
        if (estaVazia()) {
            return null;
        }
        No noRemovido = topo;
        topo = topo.getProximo();
        return noRemovido;
    }
}