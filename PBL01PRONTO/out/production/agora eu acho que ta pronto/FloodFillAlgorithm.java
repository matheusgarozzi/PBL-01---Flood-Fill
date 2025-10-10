

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FloodFillAlgorithm {
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
        this.iniciarFloodFill(startX, startY, novaCor, pilha);
    }

    public void fillComFila(int startX, int startY, Color novaCor) throws InterruptedException {
        Fila fila = new Fila();
        this.iniciarFloodFill(startX, startY, novaCor, fila);
    }

    private void iniciarFloodFill(int startX, int startY, Color novaCor, Object estrutura) throws InterruptedException {
        if (this.isDentroLimites(startX, startY)) {
            this.corAlvoRGB = this.imagem.getRGB(startX, startY);
            this.novaCorRGB = novaCor.getRGB();
            if (this.corAlvoRGB != this.novaCorRGB && this.corAlvoRGB == Color.WHITE.getRGB()) {
                this.pixelsPintados = 0;
                if (estrutura instanceof Pilha) {
                    ((Pilha)estrutura).push(startX, startY);
                    this.processar((Pilha)estrutura);
                } else if (estrutura instanceof Fila) {
                    ((Fila)estrutura).enqueue(startX, startY);
                    this.processar((Fila)estrutura);
                }

            } else {
                JOptionPane.showMessageDialog((Component)null, "Ponto de partida não é a cor de fundo (Branco). O preenchimento não será executado.", "Aviso de Cor", 2);
            }
        }
    }

    private void processar(Pilha pilha) throws InterruptedException {
        while(!pilha.estaVazia()) {
            No pixelAtual = pilha.pop();
            int x = pixelAtual.getX();
            int y = pixelAtual.getY();
            if (this.isPintavel(x, y)) {
                this.imagem.setRGB(x, y, this.novaCorRGB);
                ++this.pixelsPintados;
                this.painel.atualizar();
                Thread.sleep(5L);
                if (this.pixelsPintados % 250 == 0) {
                    this.salvarFrame(this.pixelsPintados);
                }

                this.adicionarVizinho(pilha, x, y - 1);
                this.adicionarVizinho(pilha, x, y + 1);
                this.adicionarVizinho(pilha, x - 1, y);
                this.adicionarVizinho(pilha, x + 1, y);
            }
        }

    }

    private void processar(Fila fila) throws InterruptedException {
        while(!fila.estaVazia()) {
            No pixelAtual = fila.dequeue();
            int x = pixelAtual.getX();
            int y = pixelAtual.getY();
            if (this.isPintavel(x, y)) {
                this.imagem.setRGB(x, y, this.novaCorRGB);
                ++this.pixelsPintados;
                this.painel.atualizar();
                Thread.sleep(5L);
                if (this.pixelsPintados % 250 == 0) {
                    this.salvarFrame(this.pixelsPintados);
                }

                this.adicionarVizinho(fila, x, y - 1);
                this.adicionarVizinho(fila, x, y + 1);
                this.adicionarVizinho(fila, x - 1, y);
                this.adicionarVizinho(fila, x + 1, y);
            }
        }

    }

    private boolean isDentroLimites(int x, int y) {
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura;
    }

    private boolean isPintavel(int x, int y) {
        return this.isDentroLimites(x, y) && this.imagem.getRGB(x, y) == this.corAlvoRGB;
    }

    private void adicionarVizinho(Object estrutura, int x, int y) {
        if (this.isDentroLimites(x, y)) {
            if (estrutura instanceof Pilha) {
                ((Pilha)estrutura).push(x, y);
            } else if (estrutura instanceof Fila) {
                ((Fila)estrutura).enqueue(x, y);
            }
        }

    }

    private void salvarFrame(int count) {
        try {
            String fileName = String.format("%s_%04d.png", this.nomeBaseArquivo, count);
            ImageIO.write(this.imagem, "png", new File(fileName));
        } catch (IOException e) {
            System.err.println("Erro ao salvar o frame: " + e.getMessage());
        }

    }
}
