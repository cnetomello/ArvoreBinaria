import java.util.*;

public class MainArvoreAVL {
    public static void main(String[] args) throws InterruptedException {

        int limiteSuperior = 20000; // Define o limite superior para geração de números aleatórios
        Random geradorAleatorio = new Random(1234); // Inicializa um gerador de números aleatórios com uma semente específica
        Scanner entrada = new Scanner(System.in); // Prepara um scanner para entrada do usuário

        int numeroAleatorio = geradorAleatorio.nextInt(limiteSuperior); // Gera um número aleatório dentro do limite

        ArvoreAVL arvoreAVL = new ArvoreAVL(); // Cria uma instância da classe ArvoreAVL para construir uma árvore AVL

        String numeros = ""; // Variável para armazenar os números inseridos na árvore em formato de string
        long inicioTempo = System.nanoTime(); // Marca o início da contagem de tempo

        for (int i = 0; i < 10000; i++) {
            arvoreAVL.inserir(numeroAleatorio); // Insere números aleatórios na árvore AVL
            numeros += numeroAleatorio + " ,"; // Adiciona o número atual à string de números
            numeroAleatorio = geradorAleatorio.nextInt(limiteSuperior); // Gera um novo número aleatório
        }
        long fimTempo = System.nanoTime(); // Marca o fim da contagem de tempo
        long duracao = (fimTempo - inicioTempo) / 1000000; // Calcula a duração da inserção em milissegundos

        arvoreAVL.imprimir(); // Imprime a árvore AVL em pré-ordem
        System.out.println(numeros); // Imprime a sequência de números inseridos na árvore
        System.out.println("Duração da inserção: " + duracao + " ms"); // Exibe a duração da operação de inserção

        int elementoParaRemover = 0; // Variável para armazenar o elemento a ser removido
        System.out.println("Qual valor deseja remover?"); // Solicita ao usuário um valor para remoção
        elementoParaRemover = entrada.nextInt(); // Lê o valor a ser removido da árvore
        //Thread.sleep(30000); - Comentado para não pausar a execução

        inicioTempo = System.nanoTime(); // Marca o início da contagem de tempo para a remoção
        arvoreAVL.deletar(arvoreAVL.getRaiz(), elementoParaRemover); // Remove o elemento especificado da árvore AVL
        fimTempo = System.nanoTime(); // Marca o fim da contagem de tempo
        arvoreAVL.imprimir(); // Imprime a árvore após a remoção
        duracao = fimTempo - inicioTempo; // Calcula a duração da remoção em nanossegundos
        System.out.println("Duração da remoção: " + duracao + " ns"); // Exibe a duração da operação de remoção
        System.out.println();
    }
}
