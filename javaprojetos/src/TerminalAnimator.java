import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// A classe ListaDinamica é necessária e deve estar no mesmo projeto
// A classe TerminalUtils é necessária para a exibição no console

public class TerminalAnimator {
    private final ListaDinamica frames; // Sua ListaDinamica customizada
    private int delayTime; // Em milissegundos
    private String framesBasePath;
    private final int MAX_WIDTH = 100; // Largura máxima para caber na maioria dos terminais
    private final int MAX_HEIGHT = 50; // Altura máxima para caber na maioria dos terminais

    public TerminalAnimator(String framesBasePath, int delayTime) {
        this.frames = new ListaDinamica();
        this.delayTime = Math.max(50, delayTime); // Aumentado para melhor visualização no terminal
        this.framesBasePath = framesBasePath;
        
        // Carrega os frames que foram criados pelo FloodFill
        carregarFramesDeArquivos(); 
    }

    public void carregarFramesDeArquivos() {
        if (framesBasePath == null) return;

        int frameCount = 0;
        int skipFrames = 3;
        System.out.println("Procurando frames em: " + framesBasePath + "_frame_XXXX.png");

        while (true) {
            String nomeArquivo = framesBasePath + "_frame_" + String.format("%04d", frameCount) + ".png";
            File arquivo = new File(nomeArquivo);
            if (!arquivo.exists()) break;

            try {
                // Diminui o número de frames para a animação não ficar muito longa no console
                if (frameCount < 5 || frameCount % skipFrames == 0) {
                    BufferedImage img = ImageIO.read(arquivo);
                    if (img != null) frames.adicionar(img); // Usa a ListaDinamica
                }
                frameCount++;
            } catch (IOException e) {
                System.err.println("Erro ao ler frame " + nomeArquivo + ": " + e.getMessage());
                break;
            }
        }

        System.out.println("Frames carregados: " + frames.tamanho() + " (encontrados: " + frameCount + ")");
    }


    /**
     * Método principal para exibir a animação diretamente no console.
     * @param useColor Se deve usar códigos ANSI para cor de fundo (recomendado)
     */
    public void exibirNoTerminal(boolean useColor) {
        if (frames.estaVazia()) {
            System.err.println("Nenhum frame carregado para exibição.");
            return;
        }

        System.out.println("\n🎬 Iniciando Animação ASCII/ANSI no Terminal...");
        System.out.println(" (Pressione Ctrl+C para parar)");
        
        int numeroFrames = frames.tamanho();
        
        try {
            for (int i = 0; i < numeroFrames; i++) {
                
                // 1. Limpa a tela para simular o movimento
                System.out.print(TerminalUtils.ANSI_CLEAR_SCREEN);

                // 2. Renderiza o frame
                renderizarFrame(frames.get(i), useColor);

                // 3. Imprime o progresso
                System.out.println("\n Frame: " + (i + 1) + "/" + numeroFrames);
                
                // 4. Pausa para o delay
                Thread.sleep(delayTime);
            }
            
            // 5. Volta ao normal
            System.out.println(TerminalUtils.ANSI_RESET);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("\nErro durante a exibição da animação: " + e.getMessage());
        }
        
        System.out.println("Animação no terminal concluída.");
        frames.limpar();
    }

    private void renderizarFrame(BufferedImage frame, boolean useColor) {
        int originalW = frame.getWidth();
        int originalH = frame.getHeight();
        
        // Define o tamanho de exibição (mantendo a proporção)
        double ratio = (double) originalW / originalH;
        int targetW = originalW;
        int targetH = originalH;
        
        // Redimensiona se for maior que os limites máximos
        if (originalW > MAX_WIDTH) {
            targetW = MAX_WIDTH;
            targetH = (int) (MAX_WIDTH / ratio * 0.5); // *0.5 para corrigir a proporção do caractere
        }
        if (targetH > MAX_HEIGHT) {
            targetH = MAX_HEIGHT;
            targetW = (int) (MAX_HEIGHT * ratio * 2); // *2 para corrigir a proporção do caractere
        }
        
        // Limita a largura final
        targetW = Math.min(targetW, MAX_WIDTH);
        
        // A proporção vertical do caractere do terminal é diferente, então ajustamos a altura
        int finalH = (int) (targetW / ratio * 0.5);
        
        // Itera sobre a imagem com amostragem para caber no console
        for (int y = 0; y < finalH; y++) {
            StringBuilder linha = new StringBuilder();
            for (int x = 0; x < targetW; x++) {
                
                // Amostragem: calcula a posição do pixel original
                int originalX = (int) (((double) x / targetW) * originalW);
                int originalY = (int) (((double) y / finalH) * originalH);
                
                // Pega a cor e converte para ASCII/ANSI
                int cor = frame.getRGB(originalX, originalY);
                linha.append(TerminalUtils.pixelToAscii(cor, useColor));
            }
            System.out.println(linha.toString());
        }
    }
}
