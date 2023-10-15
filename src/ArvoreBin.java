public class ArvoreBin {
    private Node root; // Nó raiz da árvore

    public ArvoreBin() {
        this.root = null; // Inicializa a árvore com a raiz como nula
    }

    public Node getRoot() {
        return root; // Obtém a raiz da árvore
    }

    public void setRoot(Node root) {
        this.root = root; // Define a raiz da árvore
    }

    public Node inserir(Node root, int dado) {
        // Método para inserir um nó com o dado na árvore

        if (this.root == null) {
            // Se a árvore estiver vazia, cria um novo nó e define como raiz
            Node temp = new Node();
            temp.setData(dado);
            this.root = temp;
            return temp;
        } else if (root == null) {
            // Se a raiz atual (ou subárvore) for nula, cria um novo nó
            Node temp = new Node();
            temp.setData(dado);
            return temp;
        } else if (dado < root.getData()) {
            // Se o dado for menor que o dado no nó atual, insere na subárvore esquerda
            root.setLeft(inserir(root.getLeft(), dado));
        } else if (dado >= root.getData()) {
            // Se o dado for maior ou igual ao dado no nó atual, insere na subárvore direita
            root.setRight(inserir(root.getRight(), dado));
            if (dado == root.getData()) {
                root.setCount(root.getCount() + 1);
            }
        }
        return root; // Retorna o nó raiz atualizado após a inserção
    }

    public Node inserir(int dado) {
        // Método para inserir um nó com o dado na árvore (chama a versão com a raiz como parâmetro)
        return inserir(root, dado);
    }

    public Node removerNo(Node root, int n) {
        // Método para remover um nó com um dado específico da árvore

        if (root == null) {
            return null; // Se a raiz atual for nula, não há nada a fazer
        } else if (n < root.getData()) {
            // Se o dado for menor que o dado no nó atual, remove da subárvore esquerda
            root.setLeft(removerNo(root.getLeft(), n));
        } else if (n > root.getData()) {
            // Se o dado for maior que o dado no nó atual, remove da subárvore direita
            root.setRight(removerNo(root.getRight(), n));
        } else {
            if (root.getCount() >= 2) {
                // Se o nó possui mais de uma instância do dado, decrementa a contagem
                root.setRight(removerNo(root.getRight(), n));
                root.setCount(root.getCount() - 1);
            } else if (root.getLeft() == null || root.getRight() == null) {
                // Se o nó tem um filho ou nenhum, remove diretamente
                // Lida com o caso de a raiz ser removida
                // Lida com os casos de remoção de nós com 1 filho
            } else {
                // Se o nó tem dois filhos, encontra o sucessor e o substitui
                Node sucessor = getSucessor(root);
                root.setData(sucessor.getData());
                root.setRight(removerNo(root.getRight(), root.getData()));
            }
        }
        return root; // Retorna o nó raiz atualizado após a remoção
    }

    public Node getSucessor(Node root) {
        // Encontra o sucessor de um nó (nó com o próximo maior valor)
        if (root == null) {
            return null;
        }
        Node temp = root.getRight();
        while (temp.getLeft() != null && temp.getData() != root.getData()) {
            temp = temp.getLeft();
        }
        return temp;
    }

    public String percorrerPreOrdem(Node root) {
        // Realiza uma travessia em pré-ordem da árvore e retorna os nós visitados como uma string
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(root.getData());
        String ponteiroDireito = "└──";
        String ponteiroEsquerdo = (root.getRight() != null) ? "├──" : "└──(esquerda)";
        percorrerNodos(sb, "", ponteiroEsquerdo, root.getLeft(), root.getRight() != null);
        percorrerNodos(sb, "", ponteiroDireito, root.getRight(), false);
        return sb.toString();
    }

    public void percorrerNodos(StringBuilder sb, String padding, String ponteiro, Node node,
                               boolean possuiIrmaoDireito) {
        // Função auxiliar para percorrer os nós da árvore e montar a string de travessia
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(ponteiro);
            sb.append(node.getData());
            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (possuiIrmaoDireito) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }
            String paddingParaAmbos = paddingBuilder.toString();
            String ponteiroDireito = "└──";
            String ponteiroEsquerdo = (node.getRight() != null) ? "├──" : "└──(esquerda)";
            percorrerNodos(sb, paddingParaAmbos, ponteiroEsquerdo, node.getLeft(), node.getRight() != null);
            percorrerNodos(sb, paddingParaAmbos, ponteiroDireito, node.getRight(), false);
        }
    }

    public void imprimir() {
        // Imprime a árvore em pré-ordem
        System.out.println(percorrerPreOrdem(root));
    }

    public Node buscar(int elemento) {
        // Realiza uma busca na árvore e retorna o nó com o elemento especificado
        Node atual = this.root;
        while (atual != null && atual.getData() != elemento) {
            if (atual.getData() > elemento)
                atual = atual.getLeft();
            else
                atual = atual.getRight();
        }
        return atual;
    }
}
