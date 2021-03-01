/*
 * Jogo da velha -- via terminal !
 * 
 */
package matrizes;

import java.util.Scanner;

/**
 *
 * @author Sara Tuma
 */
public class JogoDaVelha {
    final int  TAML=5; //Número de linhas do tabuleiro
    final int  TAMC=5; //Número de colunas do tabuleiro
    //Variaveis para armazenasr as peças: X ou O
    public static char peca1=' '; 
    public static char peca2=' ';
    public char peca=' '; //Variavel que guardara o valor do jogador actual
    boolean addpeca=false; //Variavel de verificação
    //Tabuleiro do jogo
    public char[][] tabuleiro = new char[TAML][TAMC];
    
    //Método PARA INICIALIZAR O TABULEIRO
    public void inic(){
        for(int i=0;i<tabuleiro.length; i++){
            for(int j=0; j<tabuleiro[i].length; j++){
                tabuleiro[i][j]='*';
            }
        }
    }
    
    //Função para verificar se a linha e a coluna dadas, é válido ou não!
    public boolean verifPos(char p, int i, int j){
        if(i>-1 && i<tabuleiro.length){
            if(j>-1 && j<tabuleiro[i].length){
                return true;
            }
        }   
        return false;
    }
    
    //Metodo para imprimir os números da colunas!
    public void mostrarColunas(){
        System.out.print("   ");
        for(int j=1; j<=tabuleiro.length;j++){
            System.out.print(" "+j);
        }
    }
    
    //Metodo para mostrar o tabuleiro a cada jogada!
    public void print(){
        mostrarColunas();
        System.out.println();
        for(int i=0;i<tabuleiro.length; i++){
            for(int j=0; j<tabuleiro[i].length; j++){
                if(j==tabuleiro[i].length-1){
                    System.out.println(" "+tabuleiro[i][j]+" |");
                }else if(j==0)
                    System.out.print((i+1)+" | "+tabuleiro[i][j]);
                else 
                    System.out.print(" "+tabuleiro[i][j]);
            }
        }
    }
    
    //Função para trocar o valor da peça em  runtime!
    // Ou seja mudar de jogador
    public char trocarPeca(char p){
        if(p==peca1)
            return peca2;
        else
            return peca1;
    }
    
    //VERIFICA A DIAGONAL PRINCIPAL, se alguma peça preencheu ela!
    public boolean verifDiagonal1(char p){
        int fimc=TAMC-1;
        for(int i=0; i<tabuleiro.length;i++){
            if(tabuleiro[i][i]!=p)
                return false;
        }
        return true;
    }
    
    //VERIFICA A DIAGONAL SECUNDARIA, se alguma peça preencheu ela!
    public boolean verifDiagonal2(char p){
        int fimc=TAMC-1;
        for(int i=0; i<tabuleiro.length;i++){
            if(tabuleiro[i][fimc]!=p)
                return false;
            fimc--;
        }
        return true;
    }
    
    //VERIFICA A LINHA, se alguma peça preencheu ela!
    public boolean verifLinha(char p, int l){
        for(int j=0; j<tabuleiro[l].length;j++){
            if(tabuleiro[l][j]!=p)
                return false;
        }
        return true;
    }
    
    //VERIFICA A Coluna, se alguma peça preencheu ela!
    public boolean verifColuna(char p, int c){
        for(int i=0; i<tabuleiro.length;i++){
            if(tabuleiro[i][c]!=p)
                return false;
        }
        return true;
    }
    
    //Aqui eu chamo as funções de verificação, para ver se alguém ganhou, a cada jogada!
    public boolean verifEstado(char p){
        System.out.println("Verificando estado!");
        
        for(int i=0;i<tabuleiro.length; i++){
            if(verifLinha(p, i))
                return true;
            if(verifColuna(p, i))
                return true;
        }
        if(verifDiagonal1(p))
            return true;
        if(verifDiagonal2(p))
            return true;
        return false;
    }
    
    //Metódo responsavel por pedir a linha e a coluna e valida-las
    // E coloca a peça naquela posição, se for válida!
    public void escolherPos(char p){
        int linha=0,coluna=0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Jogador ->"+p+"<- , escolhe uma linha e a coluna!");
        System.out.print("Linha: "); linha=scan.nextInt(); 
        while(linha<0 || linha>tabuleiro.length){
            System.out.print("Linha Inválida!!!! Digite novamente: ");
            linha=scan.nextInt();
        }
        System.out.print("Coluna: "); coluna=scan.nextInt();
        while(coluna<0 || coluna>TAMC){
            System.out.print("Coluna Inválida!!!! Digite novamente: ");
            coluna=scan.nextInt();
        }
        coluna--; linha--;
        if(tabuleiro[linha][coluna]=='x' || tabuleiro[linha][coluna]=='o'){
            addpeca=false;
            System.out.println("ERRO: Jogada inválida, tente novamente!");
        }
            
        else{
            addpeca=true;
            tabuleiro[linha][coluna]=p;
        }
                
    }
    
    //Pede para o usuario escolher a peça e verifica se é válida ou não
    //Se for ele retorna, se não pede novamente até ele digitar um caracter válido('x' ou 'o')!
    public char validarPeca(){
        Scanner scan = new Scanner(System.in);
        String op; char p=' ';
        System.out.print("Qual peca deve começar o jogo?!  X ou O : ");
        op=scan.next();
        op=op.toLowerCase();
        while(true)
        {
            if(op.equals("x") || op.equals("o")) break;
            System.out.println("Peça inválida (Digite X ou O): "+op);
            op=scan.next(); 
        }
        op=op.toLowerCase();
        if(op.equals("x")){
           peca1='x'; peca2='o'; 
           p='x';
        }
        else
        {
            peca1='o'; peca2='x';
            p='o';
        }
        return p;
    }
    
    //Método principal do jogo, é aqui onde tudo acontece!
    public void startGame(){
        int jogadas=1; //Para contabilizar as jogadas!
        peca=validarPeca(); //variavel para guardar a peça em runtime!
        while(jogadas<=TAML*TAMC){
            System.out.println("JOGADA "+jogadas);
            escolherPos(peca); //Recebe uma peça a cada jogada, via teclado!
            print();  //Mostra o estado do tabuleiro, a cada jogada
            
            if(jogadas>=(TAML+TAMC)-1) //Condição para verificar o tabuleiro
            {
                if(verifEstado(peca)){ //Função que verifica nas diagonais, linhas e colunas
                    System.out.println("O jogador da peça ->"+peca+"<- venceu o jogo!!!!");
                    break;
                } 
            }
            if(addpeca){ //Se a peça anterior foi adicionada
                    jogadas++; //então podemos ir para a próxima jogada
                    peca=trocarPeca(peca); //e mudar o nosso jogador, ou seja a sua peça!
            }
        }
        if(jogadas>TAML*TAMC)
            System.out.println("Ninguém ganhou está partida!!! ");
        System.out.println("-------- GAME OVER ----------");
        System.out.println("By: Sara Tuma ~_^ ");
    }
    
    //Método principal
    public static void main(String[] args) {
        
        JogoDaVelha jogo= new JogoDaVelha();
        jogo.inic();
        System.out.println("-------- START GAME ----------");
        System.out.println("Benvindo ao jogo da velha: ");
        jogo.print();
        jogo.startGame();
    }
}
