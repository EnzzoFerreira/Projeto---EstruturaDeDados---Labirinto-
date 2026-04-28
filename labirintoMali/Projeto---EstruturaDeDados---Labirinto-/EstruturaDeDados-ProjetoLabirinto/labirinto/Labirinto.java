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

                    caminho.guardeUmItem(atual);
                }
                if(!encontrou) {
                    throw new Exception("Entrada do labirinto não encontrada!");
                }
            }
        }
    }

    public void ArmazenarCoordenadas() throws Exception {

    FilaParada<Coordenada> fila = new FilaParada<>(3);

    int l = atual.getLinha();
    int c = atual.getColuna();

    if(l > 0 && (labirinto[l - 1][c] == ' ' || labirinto[l - 1][c] == 'S'))
        fila.guardeUmItem(new Coordenada(l - 1, c));

    if(l < labirinto.length - 1 && (labirinto[l + 1][c] == ' ' || labirinto[l + 1][c] == 'S'))
        fila.guardeUmItem(new Coordenada(l + 1, c));

    if(c > 0 && (labirinto[l][c - 1] == ' ' || labirinto[l][c - 1] == 'S'))
        fila.guardeUmItem(new Coordenada(l, c - 1));

    if(c < labirinto[l].length - 1 && (labirinto[l][c + 1] == ' ' || labirinto[l][c + 1] == 'S'))
        fila.guardeUmItem(new Coordenada(l, c + 1));

    if(fila.isVazia())
        throw new Exception("Não há caminhos!");

    atual = fila.recupereUmItem();
    fila.removaUmItem();

    if(labirinto[atual.getLinha()][atual.getColuna()] == 'S') {
        System.out.println("Saída encontrada!");
        return;
    }

    labirinto[atual.getLinha()][atual.getColuna()] = '*';

    caminho.guardeUmItem(atual);

    possibilidades.guardeUmItem(fila);
}

    public void VoltarCoordenadas() throws Exception {

    while (true) {

        if (caminho.isVazia() || possibilidades.isVazia())
            throw new Exception("Não há mais caminhos para voltar!");

        atual = caminho.recupereUmItem();
        caminho.removaUmItem();

        if (labirinto[atual.getLinha()][atual.getColuna()] == '*')
            labirinto[atual.getLinha()][atual.getColuna()] = ' ';

        
        FilaParada<Coordenada> filaAlternativa = possibilidades.recupereUmItem();
        possibilidades.removaUmItem();

        
        if (filaAlternativa.isVazia()) {
            continue;
        }

        atual = filaAlternativa.recupereUmItem();
        filaAlternativa.removaUmItem();

        if(labirinto[atual.getLinha()][atual.getColuna()] != 'S') {
            labirinto[atual.getLinha()][atual.getColuna()] = '*';
        }

        caminho.guardeUmItem(atual);
        break;
    }
}
    // Testar Labirinto
    public boolean chegouNaSaida() {
        return labirinto[atual.getLinha()][atual.getColuna()] == 'S';
    }

    //class teste só pra pintar a poha do lab
    public static void pintarCaminhoFinal(Labirinto labirinto) throws Exception {

        // limpa tudo antes
        Character[][] matriz = labirinto.getLabirinto();
        for (int l = 0; l < matriz.length; l++) {
            for (int c = 0; c < matriz[l].length; c++) {
                if (matriz[l][c] == '*') {
                    matriz[l][c] = ' ';
                }
            }
        }

        Pilha<Coordenada> caminho = labirinto.getCaminho();

        // pilha auxiliar (pra não perder os dados)
        Pilha<Coordenada> aux = new Pilha<>(labirinto.getLinhas() * labirinto.getColunas());

        // copia tudo
        while (!caminho.isVazia()) {
            Coordenada c = caminho.recupereUmItem();
            caminho.removaUmItem();
            aux.guardeUmItem(c);
        }

        // devolve e pinta
        while (!aux.isVazia()) {
            Coordenada c = aux.recupereUmItem();
            aux.removaUmItem();

            caminho.guardeUmItem(c); // devolve

            // não sobrescreve E nem S
            if (matriz[c.getLinha()][c.getColuna()] != 'E' &&
                matriz[c.getLinha()][c.getColuna()] != 'S') {

                matriz[c.getLinha()][c.getColuna()] = '*';
            }}
        }
    public Pilha<Coordenada> getCaminho() {
        return caminho;
    }
}