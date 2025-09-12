import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;


public class GifAnimator {
    private final List<BufferedImage> frames;
    private int delayTime;
    private String outputPath;

    public GifAnimator(String outputPath, int delayTime) {
        this.frames = new ArrayList<>();
        this.delayTime = Math.max(20, delayTime);
        this.outputPath = outputPath;
    }


    public void adicionarFrame(BufferedImage frame) {
        if (frame == null) return;
        BufferedImage rgb = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgb.createGraphics();
        g.drawImage(frame, 0, 0, null);
        g.dispose();
        frames.add(rgb);
    }

    public void carregarFramesDeArquivos(String basePath) {
        if (basePath == null) return;

        int frameCount = 0;
        int skipFrames = 3;
        System.out.println("Procurando frames em: " + basePath + "_frame_XXXX.png");

        while (true) {
            String nomeArquivo = basePath + "_frame_" + String.format("%04d", frameCount) + ".png";
            File arquivo = new File(nomeArquivo);
            if (!arquivo.exists()) break;

            try {
                if (frameCount < 5 || frameCount % skipFrames == 0) {
                    BufferedImage img = ImageIO.read(arquivo);
                    if (img != null) adicionarFrame(img);
                }
                frameCount++;
            } catch (IOException e) {
                System.err.println("Erro ao ler frame " + nomeArquivo + ": " + e.getMessage());
                break;
            }
        }

        System.out.println("Frames carregados: " + frames.size() + " (encontrados: " + frameCount + ")");
    }


    public int getNumeroFrames() {
        return frames.size();
    }


    public void limparFrames() {
        frames.clear();
        System.gc();
    }

    public void gerarGif() {
        if (frames.isEmpty()) {
            System.err.println(" Nenhum frame encontrado para criar GIF!");
            return;
        }
        if (frames.size() < 2) {
            System.err.println(" Precisa de pelo menos 2 frames para criar animação!");
            return;
        }

        System.out.println("🎬 Iniciando criação do GIF com " + frames.size() + " frames...");
        File outputFile = new File(outputPath);
        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        if (outputFile.exists()) outputFile.delete();

        ImageWriter gifWriter = null;
        ImageOutputStream ios = null;
        try {
            gifWriter = ImageIO.getImageWritersByFormatName("gif").next();
            if (gifWriter == null) throw new IOException("Nenhum writer GIF disponível.");

            ios = ImageIO.createImageOutputStream(outputFile);
            gifWriter.setOutput(ios);
            gifWriter.prepareWriteSequence(null);

            int delayCentis = Math.max(1, delayTime / 10); // unidade do GIF é 1/100s

            for (int i = 0; i < frames.size(); i++) {
                BufferedImage frame = frames.get(i);
                ImageWriteParam iwp = gifWriter.getDefaultWriteParam();

                ImageTypeSpecifier typeSpec = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
                IIOMetadata metadata = gifWriter.getDefaultImageMetadata(typeSpec, iwp);

                configurarMetadados(metadata, delayCentis, i == 0);

                IIOImage ii = new IIOImage(frame, null, metadata);
                gifWriter.writeToSequence(ii, iwp);

                if ((i + 1) % 10 == 0 || i == frames.size() - 1) {
                    int pct = (int) (((i + 1) * 100.0) / frames.size());
                    System.out.println(" Progresso: " + pct + "% (" + (i + 1) + "/" + frames.size() + ")");
                }
            }

            gifWriter.endWriteSequence();
            System.out.println(" GIF criado com sucesso em: " + outputFile.getAbsolutePath());
            System.out.println(" Tamanho: " + formatarTamanho(outputFile.length()));
            System.out.println(" Frames: " + frames.size());

        } catch (Exception e) {
            System.err.println(" Erro ao criar GIF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (ios != null) ios.close(); } catch (IOException ignored) {}
            if (gifWriter != null) gifWriter.dispose();
        }
    }


    private void configurarMetadados(IIOMetadata metadata, int delayCentis, boolean primeiroFrame) {
        try {
            String metaFormat = metadata.getNativeMetadataFormatName();
            IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormat);

            IIOMetadataNode gce = buscarOuCriarNo(root, "GraphicControlExtension");
            gce.setAttribute("disposalMethod", "none");
            gce.setAttribute("userInputFlag", "FALSE");
            gce.setAttribute("transparentColorFlag", "FALSE");
            gce.setAttribute("delayTime", Integer.toString(delayCentis));
            gce.setAttribute("transparentColorIndex", "0");

            if (primeiroFrame) {
                IIOMetadataNode appExt = buscarOuCriarNo(root, "ApplicationExtensions");
                IIOMetadataNode netscape = new IIOMetadataNode("ApplicationExtension");
                netscape.setAttribute("applicationID", "NETSCAPE");
                netscape.setAttribute("authenticationCode", "2.0");
                byte[] loop = {1, 0, 0}; // loop infinito
                netscape.setUserObject(loop);
                appExt.appendChild(netscape);
            }

            metadata.setFromTree(metaFormat, root);
        } catch (Exception e) {
            System.err.println(" Aviso: erro ao configurar metadados GIF: " + e.getMessage());
        }
    }

    private IIOMetadataNode buscarOuCriarNo(IIOMetadataNode pai, String nome) {
        for (int i = 0; i < pai.getLength(); i++) {
            if (pai.item(i).getNodeName().equalsIgnoreCase(nome)) {
                return (IIOMetadataNode) pai.item(i);
            }
        }
        IIOMetadataNode novo = new IIOMetadataNode(nome);
        pai.appendChild(novo);
        return novo;
    }

    private String formatarTamanho(long bytes) {
        if (bytes < 1024) return bytes + " bytes";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
}
