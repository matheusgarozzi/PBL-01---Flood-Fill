import java.awt.Color;

public class FloodFillAlgorithm {
    private ImageProcessor processador;
    private int corOriginal;
    private int novaCor;
    private int largura;
    private int altura;
    private int pixelsProcessados;

    public FloodFillAlgorithm(ImageProcessor processador) {
        this.processador = processador;
        this.largura = processador.getLargura();
        this.altura = processador.getAltura();
        this.pixelsProcessados = 0;
    }

    public void executarComPilha(int x, int y, Color novaCor) {
        System.out.println("Iniciando Flood Fill com PILHA na posição (" + x + ", " + y + ")");

        if (!validarPosicao(x, y)) {
            System.err.println("Posição inválida!");
            return;
        }

        this.corOriginal = processador.getCorPixel(x, y);
        this.novaCor = novaCor.getRGB();

        if (corOriginal == this.novaCor) {
            System.out.println("Cor original igual à nova cor. Nada a fazer.");
            return;
        }

        processador.salvarFrameAnimacao();

        Pilha pilha = new Pilha(1000);
        pilha.empilhar(new Ponto(x, y));
        pixelsProcessados = 0;

        while (!pilha.vazia()) {
            Ponto ponto = pilha.desempilhar();

            if (validarEPintar(ponto.getX(), ponto.getY())) {
                adicionarVizinhos(pilha, ponto.getX(), ponto.getY());

                if (pixelsProcessados % 500 == 0 && pixelsProcessados > 0) {
                    processador.salvarFrameAnimacao();
                    System.out.println(" Frame salvo - Pixels processados: " + pixelsProcessados);
                }
            }
        }

        processador.salvarFrameAnimacao();
        System.out.println(" Flood Fill com PILHA concluído. Total de pixels: " + pixelsProcessados);
    }

    public void executarComFila(int x, int y, Color novaCor) {
        System.out.println("Iniciando Flood Fill com FILA na posição (" + x + ", " + y + ")");

        if (!validarPosicao(x, y)) {
            System.err.println("Posição inválida!");
            return;
        }

        this.corOriginal = processador.getCorPixel(x, y);
        this.novaCor = novaCor.getRGB();

        if (corOriginal == this.novaCor) {
            System.out.println("Cor original igual à nova cor. Nada a fazer.");
            return;
        }

        processador.salvarFrameAnimacao();

        Fila fila = new Fila(1000);
        fila.enfileirar(new Ponto(x, y));
        pixelsProcessados = 0;

        while (!fila.vazia()) {
            Ponto ponto = fila.desenfileirar();

            if (validarEPintar(ponto.getX(), ponto.getY())) {
                adicionarVizinhos(fila, ponto.getX(), ponto.getY());

                if (pixelsProcessados % 500 == 0 && pixelsProcessados > 0) {
                    processador.salvarFrameAnimacao();
                    System.out.println(" Frame salvo - Pixels processados: " + pixelsProcessados);
                }
            }
        }

        processador.salvarFrameAnimacao();
        System.out.println(" Flood Fill com FILA concluído. Total de pixels: " + pixelsProcessados);
    }

    private boolean validarPosicao(int x, int y) {
        return x >= 0 && x < largura && y >= 0 && y < altura;
    }

    private boolean validarEPintar(int x, int y) {
        if (!validarPosicao(x, y)) {
            return false;
        }

        int corAtual = processador.getCorPixel(x, y);
        if (corAtual != corOriginal) {
            return false;
        }

        processador.setCorPixel(x, y, novaCor);
        pixelsProcessados++;

        if (pixelsProcessados % 1000 == 0) {
            System.out.println(" Pixels processados: " + pixelsProcessados);
        }

        return true;
    }

    private void adicionarVizinhos(Pilha pilha, int x, int y) {
        pilha.empilhar(new Ponto(x + 1, y));
        pilha.empilhar(new Ponto(x - 1, y));
        pilha.empilhar(new Ponto(x, y + 1));
        pilha.empilhar(new Ponto(x, y - 1));
    }

    private void adicionarVizinhos(Fila fila, int x, int y) {
        fila.enfileirar(new Ponto(x + 1, y));
        fila.enfileirar(new Ponto(x - 1, y));
        fila.enfileirar(new Ponto(x, y + 1));
        fila.enfileirar(new Ponto(x, y - 1));
    }

}
