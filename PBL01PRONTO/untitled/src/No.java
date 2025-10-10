public class No {

    private int x;
    private int y;
    private No proximo;

    public No(int x, int y) {
        this.x = x;
        this.y = y;
        this.proximo = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}