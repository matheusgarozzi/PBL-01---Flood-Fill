

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FloodFillMain {

    private static final int OPCAO_PILHA = 0;
    private static final int OPCAO_FILA = 1;
    private static final String NOME_BASE_FRAME = "frame_flood";

    public static void main(String[] args) {
        // entrada
        String caminhoImagem = "C:\\Users\\INTEL\\Documents\\agora eu acho que ta pronto\\untitled\\entrada.png";

        BufferedImage imagemOriginal = null;
        try {
            imagemOriginal = ImageIO.read(new File(caminhoImagem));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BufferedImage imagemSaida = new BufferedImage(imagemOriginal.getWidth(), imagemOriginal.getHeight(), BufferedImage.TYPE_INT_RGB);
        imagemSaida.getGraphics().drawImage(imagemOriginal, 0, 0, null);

        //  SALVAR INICIAL
        try {
            ImageIO.write(imagemSaida, "png", new File(NOME_BASE_FRAME + "_0000_INICIAL.png"));
            System.out.println("Imagem inicial salva como 'frame_flood_0000_INICIAL.png'.");
        } catch (IOException e) {
            System.err.println("Aviso: Não foi possível salvar a imagem inicial.");
        }

        // SOLICITA COORDENADAS
        int startX = -1;
        int startY = -1;

        try {
            String xInput = JOptionPane.showInputDialog(null, "Informe a coordenada X (Coluna, de 0 a " + (imagemSaida.getWidth() - 1) + "):");
            if (xInput == null) return;
            startX = Integer.parseInt(xInput.trim());

            String yInput = JOptionPane.showInputDialog(null, "Informe a coordenada Y (Linha, de 0 a " + (imagemSaida.getHeight() - 1) + "):");
            if (yInput == null) return;
            startY = Integer.parseInt(yInput.trim());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida. Digite apenas números inteiros.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (startX < 0 || startX >= imagemSaida.getWidth() || startY < 0 || startY >= imagemSaida.getHeight()) {
            JOptionPane.showMessageDialog(null, "Coordenada inicial fora dos limites da imagem.", "Erro de Limite", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] options = {"Pilha (LIFO)", "Fila (FIFO)"};
        int escolhaEstrutura = JOptionPane.showOptionDialog(
                null,
                "Escolha a estrutura de dados a ser usada:",
                "Escolha de Estrutura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (escolhaEstrutura == JOptionPane.CLOSED_OPTION) return;

        // JANELA E ANIMAÇÃO
        String titulo = (escolhaEstrutura == OPCAO_PILHA) ? "Pilha (LIFO)" : "Fila (FIFO)";
        JFrame frame = new JFrame("Flood Fill Animation - " + titulo + " em (" + startX + ", " + startY + ")");

        PainelImagem painel = new PainelImagem(imagemSaida);
        frame.add(painel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // EXECUÇÃO

        FloodFillAlgorithm algoritmo = new FloodFillAlgorithm(imagemSaida, painel, NOME_BASE_FRAME);
        Color novaCor = new Color(153, 0, 153); // Roxo

        try {
            if (escolhaEstrutura == OPCAO_PILHA) {
                System.out.println("Iniciando Flood Fill com PILHA...");
                algoritmo.fillComPilha(startX, startY, novaCor);
            } else if (escolhaEstrutura == OPCAO_FILA) {
                System.out.println("Iniciando Flood Fill com FILA...");
                algoritmo.fillComFila(startX, startY, novaCor);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            JOptionPane.showMessageDialog(null, "O processo de preenchimento foi interrompido.", "Interrupção", JOptionPane.WARNING_MESSAGE);
        }

        // SALVAR  FINAL
        try {
            ImageIO.write(imagemSaida, "png", new File(NOME_BASE_FRAME + "_FINAL.png"));
            JOptionPane.showMessageDialog(null, "Processo concluído. Imagem final e frames intermediários salvos.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a imagem final: " + e.getMessage(), "Erro de IO", JOptionPane.ERROR_MESSAGE);
        }
    }
}