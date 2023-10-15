import java.util.Random;
import java.util.Scanner;

public class MainArvoreBin {
    public static void main(String[] args) throws InterruptedException {

        int limiteSuperior = 20000; // Define o limite superior para geração de números aleatórios
        Random geradorAleatorio = new Random(1234); // Inicializa um gerador de números aleatórios com uma semente específica
        Scanner entrada = new Scanner(System.in); // Prepara um scanner para entrada do usuário

        int numeroAleatorio = geradorAleatorio.nextInt(limiteSuperior); // Gera um número aleatório dentro do limite

        ArvoreBin arvore = new ArvoreBin(); // Cria uma instância da classe ArvoreBin para construir uma árvore binária

        String numeros = ""; // Variável para armazenar os números inseridos na árvore em formato de string
        long inicioTempo = System.nanoTime(); // Marca o início da contagem de tempo

        for (int i = 0; i < 10000; i++) {
            arvore.inserir(numeroAleatorio); // Insere números aleatórios na árvore
            numeros += numeroAleatorio + " ,"; // Adiciona o número atual à string de números
            numeroAleatorio = geradorAleatorio.nextInt(limiteSuperior); // Gera um novo número aleatório
        }
        long fimTempo = System.nanoTime(); // Marca o fim da contagem de tempo
        long duracao = (fimTempo - inicioTempo) / 1000000; // Calcula a duração da inserção em milissegundos

        arvore.imprimir(); // Imprime a árvore em pré-ordem
        System.out.println(numeros); // Imprime a sequência de números inseridos na árvore
        System.out.println("Duração da inserção: " + duracao + " ms"); // Exibe a duração da operação de inserção

        int elementoParaRemover = 0; // Variável para armazenar o elemento a ser removido
        System.out.println("Qual valor deseja remover?"); // Solicita ao usuário um valor para remoção
        elementoParaRemover = entrada.nextInt(); // Lê o valor a ser removido da árvore

        inicioTempo = System.nanoTime(); // Marca o início da contagem de tempo para a remoção
        arvore.removerNo(arvore.getRoot(), elementoParaRemover); // Remove o elemento especificado da árvore
        fimTempo = System.nanoTime(); // Marca o fim da contagem de tempo
        arvore.imprimir(); // Imprime a árvore após a remoção
        duracao = fimTempo - inicioTempo; // Calcula a duração da remoção em nanossegundos
        System.out.println("Duração da remoção: " + duracao + " ns"); // Exibe a duração da operação de remoção
        System.out.println();
    }
}
