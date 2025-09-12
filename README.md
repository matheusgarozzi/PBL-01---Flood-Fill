# PBL-01---Flood-Fill

Este projeto é uma implementação do famoso algoritmo de preenchimento de áreas (Flood Fill), popularmente conhecido como "balde de tinta". Ele demonstra e compara o desempenho e o comportamento visual de duas estruturas de dados: Pilha e Fila.

A aplicação permite que o usuário selecione uma imagem PNG, defina um ponto de partida e uma nova cor para o preenchimento. O processo é visualmente animado, com a opção de salvar um GIF animado do preenchimento.

⚙️ Funcionalidades
Implementação com Pilha (Stack): Executa o algoritmo usando uma busca em profundidade (DFS), criando um padrão de preenchimento que se ramifica rapidamente.

Implementação com Fila (Queue): Executa o algoritmo usando uma busca em largura (BFS), criando um padrão de preenchimento que se expande uniformemente, como uma onda.

Comparação de Desempenho: Permite executar e comparar o tempo de processamento das duas implementações.

Geração de GIF: Salva o processo de preenchimento em um arquivo GIF animado para visualização.

Entrada Dinâmica: O usuário pode definir a imagem, o ponto inicial e a cor de preenchimento via console.

🚀 Tecnologias Utilizadas
Java: Linguagem de programação principal.

Swing/AWT: Para manipulação de imagens (BufferedImage).

Pilha e Fila: Implementações próprias para a demonstração das estruturas de dados.

💻 Como Executar o Projeto
Pré-requisitos
Java Development Kit (JDK) 8 ou superior.

Instruções de Execução




Execute o programa

Caminho da Imagem: Digite o caminho para o seu arquivo .png de entrada (ex: caminho/para/imagem.png). Se você deixar em branco, o programa usará o arquivo padrão entrada.png.

Coordenadas: Informe as coordenadas X e Y do pixel onde o preenchimento deve começar.

Cor: Digite os valores R, G e B da nova cor.

Estrutura de Dados: Escolha entre Pilha, Fila ou a comparação de ambas.

Animação GIF: Opcionalmente, escolha criar um GIF animado do processo.

Exemplo de Uso
Ao executar o programa, a saída no console será similar a:


Digite o caminho da imagem PNG de entrada: entrada.png
Usando arquivo padrão: entrada.png

Imagem carregada: 800 x 600 pixels
Digite a coordenada X inicial (0 a 799): 400
Digite a coordenada Y inicial (0 a 599): 300
Digite o valor R da nova cor (0-255): 255
Digite o valor G da nova cor (0-255): 0
Digite o valor B da nova cor (0-255): 0
Nova cor selecionada: RGB(255, 0, 0)

Escolha a estrutura de dados:
1 - Pilha
2 - Fila
3 - Ambas (comparação)
Opção: 2

Deseja criar uma animação GIF do processo? (s/n): s
Iniciando Flood Fill com FILA na posição (400, 300)
🎬 Frame salvo - Pixels processados: 500
...
✅ Flood Fill com FILA concluído.
Tempo de execução (Fila): 120ms
Total de pixels preenchidos: 15400
Processamento concluído!
Imagem resultado salva com sucesso.
Animação(ões) GIF criada(s) com sucesso!
🤝 Contribuição
Contribuições são sempre bem-vindas! Se você tiver sugestões, melhorias ou encontrar algum bug, sinta-se à vontade para abrir uma issue ou enviar um pull request.
