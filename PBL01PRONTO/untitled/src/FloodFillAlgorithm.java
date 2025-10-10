import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FloodFillAlgorithm {

    // Salva  pixels pintados cada 10
    private static final int FRAME_INTERVALO = 250;
    private int pixelsPintados = 0;

    private BufferedImage imagem;
    private int largura;
    private int altura;
    private int corAlvoRGB;
    private int novaCorRGB;
    private PainelImagem painel;
    private String nomeBaseArquivo;

    public FloodFillAlgorithm(BufferedImage imagem, PainelImagem painel, String nomeBaseArquivo) {
        this.imagem = imagem;
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
        this.painel = painel;
        this.nomeBaseArquivo = nomeBaseArquivo;
    }

    public void fillComPilha(int startX, int startY, Color novaCor) throws InterruptedException {
        Pilha pilha = new Pilha();
        iniciarFloodFill(startX, startY, novaCor, pilha);
    }

    public void fillComFila(int startX, int startY, Color novaCor) throws InterruptedException {
        Fila fila = new Fila();
        iniciarFloodFill(startX, startY, novaCor, fila);
    }

    private void iniciarFloodFill(int startX, int startY, Color novaCor, Object estrutura) throws InterruptedException {
        if (!isDentroLimites(startX, startY)) return;

        this.corAlvoRGB = imagem.getRGB(startX, startY);
        this.novaCorRGB = novaCor.getRGB();


        if (corAlvoRGB == novaCorRGB || corAlvoRGB != Color.WHITE.getRGB()) {
            JOptionPane.showMessageDialog(null, "Ponto de partida não é a cor de fundo (Branco). O preenchimento não será executado.", "Aviso de Cor", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Zera contador
        this.pixelsPintados = 0;

        if (estrutura instanceof Pilha) {
            ((Pilha) estrutura).push(startX, startY);
            processar((Pilha) estrutura);
        } else if (estrutura instanceof Fila) {
            ((Fila) estrutura).enqueue(startX, startY);
            processar((Fila) estrutura);
        }
    }

    private void processar(Pilha pilha) throws InterruptedException {
        while (!pilha.estaVazia()) {
            No pixelAtual = pilha.pop();
            int x = pixelAtual.getX();
            int y = pixelAtual.getY();

            if (isPintavel(x, y)) {
                imagem.setRGB(x, y, novaCorRGB);

                // Lógica de contagem e animação
                pixelsPintados++;
                painel.atualizar();
                Thread.sleep(5);

                // Salva o frame a cada FRAME_INTERVALO pixels
                if (pixelsPintados % FRAME_INTERVALO == 0) {
                    salvarFrame(pixelsPintados);
                }

                // Empilhamos os 4 vizinhos laterais
                adicionarVizinho(pilha, x, y - 1);
                adicionarVizinho(pilha, x, y + 1);
                adicionarVizinho(pilha, x - 1, y);
                adicionarVizinho(pilha, x + 1, y);
            }
        }
    }

    private void processar(Fila fila) throws InterruptedException {
        while (!fila.estaVazia()) {
            No pixelAtual = fila.dequeue();
            int x = pixelAtual.getX();
            int y = pixelAtual.getY();

            if (isPintavel(x, y)) {
                imagem.setRGB(x, y, novaCorRGB);

                // contagem e animação
                pixelsPintados++;
                painel.atualizar();
                Thread.sleep(5);


                if (pixelsPintados % FRAME_INTERVALO == 0) {
                    salvarFrame(pixelsPintados);
                }


                adicionarVizinho(fila, x, y - 1);
                adicionarVizinho(fila, x, y + 1);
                adicionarVizinho(fila, x - 1, y);
                adicionarVizinho(fila, x + 1, y);
            }
        }
    }

    private boolean isDentroLimites(int x, int y) {
        // Checa se pixel não tá ultrapassando o limite da matriz
        return x >= 0 && x < largura && y >= 0 && y < altura;
    }

    private boolean isPintavel(int x, int y) {

        return isDentroLimites(x, y) && imagem.getRGB(x, y) == corAlvoRGB;
    }

    private void adicionarVizinho(Object estrutura, int x, int y) {
        if (isDentroLimites(x, y)) {
            if (estrutura instanceof Pilha) {
                ((Pilha) estrutura).push(x, y);
            } else if (estrutura instanceof Fila) {
                ((Fila) estrutura).enqueue(x, y);
            }
        }
    }

    private void salvarFrame(int count) {
        try {

            String fileName = String.format("%s_%04d.png", nomeBaseArquivo, count);
            ImageIO.write(imagem, "png", new File(fileName));
        } catch (IOException e) {

            System.err.println("Erro ao salvar o frame: " + e.getMessage());
        }
    }
}