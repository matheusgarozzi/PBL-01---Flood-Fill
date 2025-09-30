public class TerminalUtils {
    // Sequências ANSI padrão
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CLEAR_SCREEN = "\u001B[2J\u001B[H";

    /**
     * Gera o código ANSI para definir a cor de fundo (background) a partir de um valor RGB.
     * Requer que o terminal suporte cores True Color (24-bit).
     * @param rgb O valor inteiro da cor do pixel (ARGB).
     * @return A string de código ANSI.
     */
    public static String getAnsiBgColor(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        // Código para cor de fundo (background) em 24-bit (True Color)
        return "\u001B[48;2;" + r + ";" + g + ";" + b + "m";
    }

    /**
     * Mapeia a intensidade do pixel para um caractere ASCII.
     * O algoritmo de Arte ASCII usa o brilho (luminância) do pixel.
     * @param r Componente Vermelha (0-255).
     * @param g Componente Verde (0-255).
     * @param b Componente Azul (0-255).
     * @return Um caractere ASCII que representa a intensidade.
     */
    private static char getAsciiChar(int r, int g, int b) {
        // Cálculo de luminância (aproximação para escala de cinza)
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;

        // Conjunto de caracteres ASCII ordenados por "densidade"
        String chars = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXIyxtfjeunsclFzcvPjLTwH?vr!+<>i^1~:/'\"`.\t ";
        int index = (int) ((luminance / 255.0) * (chars.length() - 1));

        return chars.charAt(chars.length() - 1 - index); // Inverte para que o mais brilhante seja o espaço
    }

    /**
     * Converte um pixel RGB em sua representação ASCII colorida.
     * @param rgb O valor inteiro da cor do pixel (ARGB).
     * @param useColor Se deve incluir códigos de cor ANSI (True Color).
     * @return Uma string representando o pixel no terminal.
     */
    public static String pixelToAscii(int rgb, boolean useColor) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // Calcula o caractere baseado na luminância
        char asciiChar = getAsciiChar(r, g, b);

        if (useColor) {
            // Usa a cor do pixel como fundo e o caractere como espaço (para "pintar" o fundo)
            return getAnsiBgColor(rgb) + " " + ANSI_RESET;
        } else {
            // Se não usar cor, retorna o caractere mapeado
            return String.valueOf(asciiChar);
        }
    }
}
