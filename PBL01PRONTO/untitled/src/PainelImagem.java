import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class PainelImagem extends JPanel {
    private BufferedImage imagem;

    public PainelImagem(BufferedImage imagem) {
        this.imagem = imagem;
        setPreferredSize(new Dimension(imagem.getWidth(), imagem.getHeight()));
    }

    public BufferedImage getImagem() {
        return imagem;
    }


    public void atualizar() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(imagem, 0, 0, this);
    }
}