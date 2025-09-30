import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Implementação de uma lista dinâmica de BufferedImage (equivalente a um ArrayList
 * feito do zero), para substituir o java.util.ArrayList no GifAnimator.
 */
public class ListaDinamica {
    private BufferedImage[] elementos;
    private int tamanhoAtual;
    private static final int CAPACIDADE_INICIAL = 10;

    public ListaDinamica() {
        this.elementos = new BufferedImage[CAPACIDADE_INICIAL];
        this.tamanhoAtual = 0;
    }

    /**
     * Adiciona um frame ao final da lista.
     * @param frame O BufferedImage a ser adicionado.
     */
    public void adicionar(BufferedImage frame) {
        if (tamanhoAtual == elementos.length) {
            redimensionar();
        }
        elementos[tamanhoAtual++] = frame;
    }

    /**
     * Retorna o elemento na posição especificada.
     * @param indice O índice do elemento.
     * @return O BufferedImage no índice.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    public BufferedImage get(int indice) {
        if (indice < 0 || indice >= tamanhoAtual) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamanho: " + tamanhoAtual);
        }
        return elementos[indice];
    }

    /**
     * Retorna o número de elementos na lista.
     * @return O tamanho atual da lista.
     */
    public int tamanho() {
        return tamanhoAtual;
    }

    /**
     * Verifica se a lista está vazia.
     * @return true se a lista não contiver elementos.
     */
    public boolean estaVazia() {
        return tamanhoAtual == 0;
    }

    /**
     * Remove todos os elementos da lista.
     */
    public void limpar() {
        // Ajuda o garbage collector liberando as referências
        for (int i = 0; i < tamanhoAtual; i++) {
            elementos[i] = null;
        }
        tamanhoAtual = 0;
    }

    /**
     * Aumenta a capacidade do array interno.
     */
    private void redimensionar() {
        int novaCapacidade = elementos.length * 2;
        // Cria um novo array e copia os elementos existentes
        this.elementos = Arrays.copyOf(this.elementos, novaCapacidade);
    }
}
