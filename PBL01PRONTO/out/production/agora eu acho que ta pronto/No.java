
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
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public No getProximo() {
        return this.proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }
}
