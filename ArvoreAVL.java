public class ArvoreAVL {
    private Node raiz;                        //possui um campo privado raiz, que é o nó raiz da árvore.

    public Node getRaiz() {
        return raiz;                           //Retorna o nó raiz da árvore.

    }

    public static int calcularAltura(Node no) {
        if (no == null) {                //Calcula a altura de um nó na árvore AVL. Essa altura representa a distância entre o nó e as folhas mais distantes da árvore.
            return -1;                   //Se o nó for nulo, retorna -1.
        }

        int alturaEsquerda = calcularAltura(no.getLeft());  //Caso contrário, calcula as alturas da sub árvore esquerda e direita do nó e retorna a altura máxima mais 1.
        int alturaDireita = calcularAltura(no.getRight());
        if (alturaEsquerda > alturaDireita) {
            return 1 + alturaEsquerda;
        }
        return 1 + alturaDireita;
    }

    public Node rotacaoEsquerda(Node no) {
        Node b = no.getRight();          //Realiza uma rotação para a esquerda em torno do nó no para manter o equilíbrio da árvore.
        Node y = b.getLeft();            //Os nós `no` e `b` são trocados, com `b` se tornando o novo nó pai e `no` passando a ser o filho direito de `b`.
        int temp = b.getCount();

        b.setLeft(no);
        no.setRight(y);
        if (no == this.raiz) {            //Verifica se `no` é a raiz da árvore e, se for o caso, atualiza a raiz da árvore.
            this.raiz = b;
        }
        if (no.getData() == b.getData()) {
            b.setCount(no.getCount());           //Também verifica se os dados (valores) em `no` e `b` são iguais e, se forem, atualiza a contagem desses nós.
            no.setCount(temp);
        }

        return b;         //Retorna o novo nó que agora é a raiz da subárvore onde a rotação ocorreu.
    }

    public Node rotacaoDireita(Node no) {
        Node b = no.getLeft();          //Realiza uma rotação para a direita, simetricamente à rotação esquerda, para manter o equilíbrio da árvore.
        Node y = b.getRight();
        int temp = b.getCount();        //Troca os papéis dos nós `no` e `b`, com `b` se tornando o novo pai e `no` tornando-se o filho esquerdo de `b`.

        b.setRight(no);
        no.setLeft(y);
        if (no == this.raiz) {
            this.raiz = b;             // atualiza a raiz da árvore, se necessário, e lida com a contagem de nós
        }
        if (no.getData() == b.getData()) {
            b.setCount(no.getCount());
            no.setCount(temp);
        }

        return b;                       //Retorna o novo nó raiz da subárvore onde a rotação ocorreu.
    }

    public int calcularBalanceamento(Node no) {
        return calcularAltura(no.getLeft()) - calcularAltura(no.getRight());
    }         //Calcula o fator de balanceamento de um nó. O fator de balanceamento é a diferença entre a altura da subárvore esquerda e a altura da subárvore direita do nó.

    public Node inserir(Node no, int chave) {
        if (this.raiz == null) {     //Adiciona um nó com a chave especificada na árvore AVL, mantendo o equilíbrio.
            Node temp = new Node();
            temp.setData(chave);
            this.raiz = temp;
            return this.raiz;
        }
        if (no == null) {            //Usa a recursão para encontrar a posição correta para inserção.
            Node temp = new Node();
            temp.setData(chave);
            return temp;
        }
        if (chave < no.getData()) {
            no.setLeft(inserir(no.getLeft(), chave));
        } else if (chave >= no.getData()) {
            no.setRight(inserir(no.getRight(), chave));
            if (chave == no.getData()) {
                no.setCount(no.getCount() + 1);
            }
        }

        int balanceamento = calcularBalanceamento(no);

        if (balanceamento > 1 && chave < no.getLeft().getData()) {
            return rotacaoDireita(no);
        }
        if (balanceamento > 1 && chave >= no.getLeft().getData()) {
            no.setLeft(rotacaoEsquerda(no.getLeft()));
            return rotacaoDireita(no);
        }                                       //Quando um nó é inserido, verifica o fator de balanceamento e aplica rotações, se necessário, para manter o equilíbrio da árvore.
        if (balanceamento < -1 && chave < no.getRight().getData()) {
            no.setRight(rotacaoDireita(no.getRight()));
            return rotacaoEsquerda(no);
        }
        if (balanceamento < -1 && chave >= no.getRight().getData()) {
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public void inserir(int chave) {
        inserir(raiz, chave);
    }

    public Node deletar(Node no, int chave) {
        if (no == null)
            return no;

        if (raiz.getData() == chave && raiz.getRight() == null && raiz.getLeft() == null) {
            raiz = null;
        }                           //Remove um nó com a chave especificada da árvore, mantendo o equilíbrio.

        if (chave < no.getData()) {
            no.setLeft(deletar(no.getLeft(), chave));
        } else if (chave > no.getData()) {
            no.setRight(deletar(no.getRight(), chave));
        } else {
            if (no.getRight() == null || no.getLeft() == null) {
                Node temp = no.getLeft() == null ? no.getRight() : no.getLeft();
                if (temp == null) {
                    no = null;
                    if (this.raiz == no) {
                        this.raiz = null;
                    }
                } else {
                    if (this.raiz == no) {
                        if (temp == no.getLeft()) {
                            this.raiz = no.getLeft();
                        } else {
                            this.raiz = no.getRight();
                        }
                    }
                    no = temp;
                }
            } else {
                Node temp = getProx(no);  //Pega o proximo/sucessor
                no.setData(temp.getData());
                no.setCount(temp.getCount());
                no.setRight(deletar(no.getRight(), temp.getData()));
            }
        }

        if (no == null)
            return no;

        int balanceamento = calcularBalanceamento(no);  //Verifica o fator de balanceamento após a exclusão e aplica rotações, se necessário, para manter o equilíbrio da árvore.

        if (balanceamento > 1 && calcularBalanceamento(no.getLeft()) >= 0)
            return rotacaoDireita(no);

        if (balanceamento > 1 && calcularBalanceamento(no.getLeft()) < 0) {
            no.setLeft(rotacaoEsquerda(no.getLeft()));
            return rotacaoDireita(no);
        }

        if (balanceamento < -1 && calcularBalanceamento(no.getRight()) <= 0)
            return rotacaoEsquerda(no);

        if (balanceamento < -1 && calcularBalanceamento(no.getRight()) > 0) {
            no.setRight(rotacaoDireita(no.getRight()));
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public Node getProx(Node no) {  //Pega o proximo/sucessor da arvore
        if (no == null) {
            return null;
        }
        Node temp = no.getRight();
        while (temp.getLeft() != null && temp.getData() != no.getData()) {
            temp = temp.getLeft();
        }
        return temp;
    }

    public Node buscar(int chave) {    //Busca na árvore um nó com a chave especificada.
        Node atual = this.raiz;
        while (atual != null && atual.getData() != chave) {
            if (atual.getData() > chave)
                atual = atual.getLeft();
            else
                atual = atual.getRight();
        }
        return atual;
    }

    public Node buscarIgual(Node no, int chave) {
        if (no == null) {  //Busca na árvore um nó com a chave especificada e uma contagem de 1.
                           // Esse método lida com casos onde a árvore permite chaves duplicadas com contagem.
            return null;
        }

        if (no.getData() == chave && no.getCount() == 1) {
            return no;
        }

        Node resultadoEsquerda = buscarIgual(no.getLeft(), chave);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        return buscarIgual(no.getRight(), chave);
    }

    public String percorrerPreOrdem(Node raiz) {
        if (raiz == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(raiz.getData());
        String ponteiroDireito = "└──";
        String ponteiroEsquerdo = (raiz.getRight() != null) ? "├──" : "└──(esquerda)";
        percorrerNodos(sb, "", ponteiroEsquerdo, raiz.getLeft(), raiz.getRight() != null);
        percorrerNodos(sb, "", ponteiroDireito, raiz.getRight(), false);
        return sb.toString();
    }

    public void percorrerNodos(StringBuilder sb, String padding, String pointer, Node no, boolean temIrmaoDireito) {
        if (no != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(no.getData());
            StringBuilder construtorPadding = new StringBuilder(padding);
            if (temIrmaoDireito) {
                construtorPadding.append("│  ");
            } else {
                construtorPadding.append("   ");
            }
            String paddingParaAmbos = construtorPadding.toString();
            String ponteiroDireito = "└──";
            String ponteiroEsquerdo = (no.getRight() != null) ? "├──" : "└──(esquerda)";
            percorrerNodos(sb, paddingParaAmbos, ponteiroEsquerdo, no.getLeft(), no.getRight() != null);
            percorrerNodos(sb, paddingParaAmbos, ponteiroDireito, no.getRight(), false);
        }
    }

    public void imprimir() {
        System.out.println((percorrerPreOrdem(raiz)));
    }

    public Node procurar(int chave) {   //Realiza uma busca na árvore e retorna o nó com a chave especificada.
        Node atual = this.raiz;
        while (atual != null && atual.getData() != chave) {
            if (atual.getData() > chave)
                atual = atual.getLeft();
            else
                atual = atual.getRight();
        }
        return atual;
    }
}
