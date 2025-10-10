

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FloodFillMain {
    private static final int OPCAO_PILHA = 0;
    private static final int OPCAO_FILA = 1;
    private static final String NOME_BASE_FRAME = "frame_flood";

    public FloodFillMain() {
    }

    public static void main(String[] args) {
        String caminhoImagem = "C:\\Users\\INTEL\\Documents\\agora eu acho que ta pronto\\untitled\\entrada.png";
        BufferedImage imagemOriginal = null;

        try {
            imagemOriginal = ImageIO.read(new File(caminhoImagem));
        } catch (IOException e) {
            JOptionPane.showMessageDialog((Component)null, "Erro ao carregar a imagem: " + e.getMessage(), "Erro", 0);
            return;
        }

        BufferedImage imagemSaida = new BufferedImage(imagemOriginal.getWidth(), imagemOriginal.getHeight(), 1);
        imagemSaida.getGraphics().drawImage(imagemOriginal, 0, 0, (ImageObserver)null);

        try {
            ImageIO.write(imagemSaida, "png", new File("frame_flood_0000_INICIAL.png"));
            System.out.println("Imagem inicial salva como 'frame_flood_0000_INICIAL.png'.");
        } catch (IOException var16) {
            System.err.println("Aviso: Não foi possível salvar a imagem inicial.");
        }

        int startX = -1;
        int startY = -1;

        try {
            int var10001 = imagemSaida.getWidth();
            String xInput = JOptionPane.showInputDialog((Component)null, "Informe a coordenada X (Coluna, de 0 a " + (var10001 - 1) + "):");
            if (xInput == null) {
                return;
            }

            startX = Integer.parseInt(xInput.trim());
            var10001 = imagemSaida.getHeight();
            String yInput = JOptionPane.showInputDialog((Component)null, "Informe a coordenada Y (Linha, de 0 a " + (var10001 - 1) + "):");
            if (yInput == null) {
                return;
            }

            startY = Integer.parseInt(yInput.trim());
        } catch (NumberFormatException var18) {
            JOptionPane.showMessageDialog((Component)null, "Entrada inválida. Digite apenas números inteiros.", "Erro de Entrada", 0);
            return;
        }

        if (startX >= 0 && startX < imagemSaida.getWidth() && startY >= 0 && startY < imagemSaida.getHeight()) {
            Object[] options = new Object[]{"Pilha (LIFO)", "Fila (FIFO)"};
            int escolhaEstrutura = JOptionPane.showOptionDialog((Component)null, "Escolha a estrutura de dados a ser usada:", "Escolha de Estrutura", 0, 3, (Icon)null, options, options[0]);
            if (escolhaEstrutura != -1) {
                String titulo = escolhaEstrutura == 0 ? "Pilha (LIFO)" : "Fila (FIFO)";
                JFrame frame = new JFrame("Flood Fill Animation - " + titulo + " em (" + startX + ", " + startY + ")");
                PainelImagem painel = new PainelImagem(imagemSaida);
                frame.add(painel);
                frame.pack();
                frame.setDefaultCloseOperation(3);
                frame.setVisible(true);
                FloodFillAlgorithm algoritmo = new FloodFillAlgorithm(imagemSaida, painel, "frame_flood");
                Color novaCor = new Color(153, 0, 153);

                try {
                    if (escolhaEstrutura == 0) {
                        System.out.println("Iniciando Flood Fill com PILHA...");
                        algoritmo.fillComPilha(startX, startY, novaCor);
                    } else if (escolhaEstrutura == 1) {
                        System.out.println("Iniciando Flood Fill com FILA...");
                        algoritmo.fillComFila(startX, startY, novaCor);
                    }
                } catch (InterruptedException var15) {
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog((Component)null, "O processo de preenchimento foi interrompido.", "Interrupção", 2);
                }

                try {
                    ImageIO.write(imagemSaida, "png", new File("frame_flood_FINAL.png"));
                    JOptionPane.showMessageDialog((Component)null, "Processo concluído. Imagem final e frames intermediários salvos.", "Sucesso", 1);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog((Component)null, "Erro ao salvar a imagem final: " + e.getMessage(), "Erro de IO", 0);
                }

            }
        } else {
            JOptionPane.showMessageDialog((Component)null, "Coordenada inicial fora dos limites da imagem.", "Erro de Limite", 0);
        }
    }
}
