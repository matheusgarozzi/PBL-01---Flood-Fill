import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageProcessor {
    private BufferedImage imagem;
    private String caminhoEntrada;
    private String caminhoSaida;
    private int contador;

    public ImageProcessor(String caminhoEntrada, String caminhoSaida) {
        this.caminhoEntrada = caminhoEntrada;
        this.caminhoSaida = caminhoSaida;
        this.contador = 0;
    }

    public boolean carregarImagem() {
        try {
            File arquivo = new File(caminhoEntrada);
            if (!arquivo.exists()) {
                System.err.println("Arquivo não encontrado: " + caminhoEntrada);
                return false;
            }
            imagem = ImageIO.read(arquivo);
            System.out.println("Imagem carregada: " + imagem.getWidth() + "x" + imagem.getHeight());
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            return false;
        }
    }

    public void salvarImagem() {
        try {
            File arquivo = new File(caminhoSaida);
            ImageIO.write(imagem, "PNG", arquivo);
            System.out.println("Imagem final salva: " + caminhoSaida);
        } catch (IOException e) {
            System.err.println("Erro ao salvar imagem: " + e.getMessage());
        }
    }

    public void salvarFrameAnimacao() {
        try {
            String nomeFrame = caminhoSaida.replace(".png", "_frame_" + String.format("%04d", contador) + ".png");
            File arquivo = new File(nomeFrame);
            ImageIO.write(imagem, "PNG", arquivo);
            contador++;
        } catch (IOException e) {
            System.err.println("Erro ao salvar frame: " + e.getMessage());
        }
    }


    public BufferedImage getImagem() {
        return imagem;
    }


    public int getLargura() {
        return imagem != null ? imagem.getWidth() : 0;
    }


    public int getAltura() {
        return imagem != null ? imagem.getHeight() : 0;
    }


    public int getCorPixel(int x, int y) {
        if (imagem == null || x < 0 || x >= imagem.getWidth() || y < 0 || y >= imagem.getHeight()) {
            return -1;
        }
        return imagem.getRGB(x, y);
    }


    public void setCorPixel(int x, int y, int cor) {
        if (imagem != null && x >= 0 && x < imagem.getWidth() && y >= 0 && y < imagem.getHeight()) {
            imagem.setRGB(x, y, cor);
        }
    }


    private String getImageTypeString(int tipo) {
        switch (tipo) {
            case BufferedImage.TYPE_INT_RGB: return "RGB (INT)";
            case BufferedImage.TYPE_INT_ARGB: return "ARGB (INT)";
            case BufferedImage.TYPE_INT_BGR: return "BGR (INT)";
            case BufferedImage.TYPE_3BYTE_BGR: return "BGR (3 BYTE)";
            case BufferedImage.TYPE_4BYTE_ABGR: return "ABGR (4 BYTE)";
            case BufferedImage.TYPE_BYTE_GRAY: return "GRAYSCALE (BYTE)";
            case BufferedImage.TYPE_BYTE_INDEXED: return "INDEXED (BYTE)";
            default: return "Tipo " + tipo;
        }
    }

    private String formatarTamanho(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
}