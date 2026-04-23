package labirinto;

import classesAuxiliares.*;
import java.io.*;

public class Labirinto  {

    private String nomeArquivo;
    private Character[][] labirinto; // vai receber [linhas][colunas] - futuramente
    private int linhas;
    private int colunas;
    private Pilha<Coordenada> caminho;
    private Pilha<FilaParada<Coordenada>> possibilidades;
    private Coordenada atual;
    

    public Labirinto(String nomeArquivo) throws Exception{
        if(nomeArquivo == null) throw new Exception("Nome do arquivo Inválido!");
        this.nomeArquivo = nomeArquivo;

        this.leitorDoLabirinto();
        this.caminho = new Pilha<>(linhas * colunas);
        this.possibilidades = new Pilha<>(linhas * colunas);
    }

    

    public String getNomeDoArquivo() {
        return nomeArquivo;
    }

    public Character[][] getLabirinto() {
        return labirinto;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void leitorDoLabirinto() throws Exception{          // Leitor do Arquivo
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        try(leitor) {
        
            String qtdLinhas = leitor.readLine();   // primeira linha
            String qtdColunas = leitor.readLine();  // segunda linha

            if(qtdLinhas == null) throw new Exception("Quantidade de linhas do labirinto Inválida!");
            if(qtdColunas == null) throw new Exception("Quantidade de colunas do labirinto Inválida!");

            this.linhas = Integer.parseInt(qtdLinhas.trim()); // conversão da quantidade de linha recebida pelo arquivo na primeira linha  
            this.colunas = Integer.parseInt(qtdColunas.trim());

            labirinto = new Character[linhas][colunas]; // recebeu

            for(int l = 0; l < linhas; l++) {
                String linhaAtual = leitor.readLine();
                if (linhaAtual == null )  throw new Exception("Linha do Labirito Inválida");
                if (linhaAtual.length() != colunas) throw new Exception("Tamanho da Linha está Inválida, maior que o Labirinto!");

                for(int c = 0; c < colunas; c++) {
                    Character caracter = linhaAtual.charAt(c); // pega caracter da linha
                    if( 
                        caracter != '#' &&
                        caracter != ' ' &&
                        caracter != 'S' &&
                        caracter != 'E'
                    ) throw new Exception("Caractere Inválido");
                    labirinto[l][c] = caracter;
                }
            }

        } catch(Exception erro) {
            throw new Exception("A leitura do Labirinto não foi possível!" + erro.getMessage());

        }
        
    }

    public void encontrarEntrada(Character[][] labirinto) throws Exception {   
        boolean encontrou = true;       
 
        for(int l = 0; l < labirinto.length; l++) {
            for(int c = 0; c < labirinto[l].length; c++) {
                if(labirinto[l][c] == 'E') {
                    atual = new Coordenada(l, c);  // atual = [(1,0)] - posição
                    System.out.println("Achou a entrada!");
                    encontrou = true;
                }
                if(!encontrou) {
                    throw new Exception("Entrada do labirinto não encontrada!");
                }
            }
        }
    }

    public void ArmazenarCoordenadas(Character[][]labirinto, FilaParada<Coordenada> fila) throws Exception {     // Modo Progressivo
        fila = new FilaParada<Coordenada>(3);

        int l = atual.getLinha();
        int c = atual.getColuna();

        
        // linha - em cima
        if(l > 0 && (labirinto[l - 1][c] == ' ' || labirinto[l - 1][c] == 'S')) {
            fila.guardeUmItem(new Coordenada(l - 1, c));
        }

        // linhas - abaixo
        if(l < labirinto.length - 1 && (labirinto[l + 1][c] == ' ' || labirinto[l + 1][c] == 'S')) {
            fila.guardeUmItem(new Coordenada(l + 1, c));
        }

        // coluna - esquerda
        if(c > 0 && (labirinto[l][c - 1] == ' ' || labirinto[l][c - 1] == 'S')) {
            fila.guardeUmItem(new Coordenada(l, c - 1));
        }

        // coluna - direita
        if (c < labirinto[l].length - 1 && (labirinto[l][c + 1] == ' ' || labirinto[l][c + 1] == 'S')) {
            fila.guardeUmItem(new Coordenada(l, c + 1));
        }

        if(fila.isVazia()) {
            return;
        }
    
        atual =  fila.recupereUmItem();     // retira coordenada de fila e guarda em atual
        fila.removaUmItem();

        if(labirinto[atual.getLinha()][atual.getColuna()] == 'S') {
            System.out.println("Saída Encontrada!");
            return;
        }

        labirinto[atual.getLinha()][atual.getColuna()] = '*';

        caminho.guardeUmItem(atual);        // caminho = [(1,1)]

        possibilidades.guardeUmItem(fila);      // fila = [  |   |   ]
    
    }

    public void VoltarCoordenadas(FilaParada<Coordenada> fila) throws Exception {     // // Modo Regressivo
        try {
            atual = (Coordenada) caminho.recupereUmItem();     // Desempilha de caminho e coloca em atual 
            caminho.removaUmItem();

            if(labirinto[atual.getLinha()][atual.getColuna()] == '*') {
                labirinto[atual.getLinha()][atual.getColuna()] = ' ';
            }

           FilaParada<Coordenada> filaAlternativa = possibilidades.recupereUmItem();
           atual = (Coordenada) filaAlternativa.recupereUmItem();
           fila.guardeUmItem(atual);
           filaAlternativa.removaUmItem();
           possibilidades.removaUmItem();

           

        } catch(Exception erro) {

        }
    }

    // Testar Labirinto
    public boolean chegouNaSaida() {
        return labirinto[atual.getLinha()][atual.getColuna()] == 'S';
    }
    
    
}