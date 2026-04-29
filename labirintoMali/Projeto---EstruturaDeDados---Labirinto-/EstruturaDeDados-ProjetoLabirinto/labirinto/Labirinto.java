package labirinto;

import classesAuxiliares.*;
import java.io.*;

public class Labirinto {

    private String nomeArquivo;
    private Character[][] labirinto;
    private int linhas;
    private int colunas;
    private Pilha<Coordenada> caminho;
    private Pilha<FilaParada<Coordenada>> possibilidades;
    private Coordenada atual;
    private boolean saidaEncontrada = false;
    private int qtdEntrada = 0;
    private int qtdSaida = 0;

    public Labirinto(String nomeArquivo) throws Exception {
        if (nomeArquivo == null) throw new Exception("Nome do arquivo Inválido!");
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
    public Pilha<Coordenada> getCaminho() {
        return caminho;
    }

    public void leitorDoLabirinto() throws Exception {
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        try (leitor) {
            String qtdLinhas = leitor.readLine();
            String qtdColunas = leitor.readLine();

            if (qtdLinhas == null) throw new Exception("Quantidade de linhas do labirinto Inválida");
            if (qtdColunas == null) throw new Exception("Quantidade de colunas do labirinto Inválida");

            this.linhas = Integer.parseInt(qtdLinhas.trim());
            this.colunas = Integer.parseInt(qtdColunas.trim());

            labirinto = new Character[linhas][colunas];

            for (int l = 0; l < linhas; l++) {
                String linhaAtual = leitor.readLine();
                if (linhaAtual == null) throw new Exception("Linha do Labirinto Inválida");
                if (linhaAtual.length() != colunas) throw new Exception("Tamanho da Linha está Inválida");

                for (int c = 0; c < colunas; c++) {
                    Character caracter = linhaAtual.charAt(c);
                    if (caracter != '#' && caracter != ' ' && caracter != 'S' && caracter != 'E')
                        throw new Exception("Caractere Inválido");
                    labirinto[l][c] = caracter;
                }
            }
        } catch (Exception erro) {
            throw new Exception("A leitura do Labirinto não foi possível " + erro.getMessage());
        }
    }

    public void encontrarEntrada(Character[][] labirinto) throws Exception {
        boolean encontrou = false;

        for (int l = 0; l < labirinto.length; l++) {
            for (int c = 0; c < labirinto[l].length; c++) {
                if (labirinto[l][c] == 'E') {
                    qtdEntrada++;
                    atual = new Coordenada(l, c);
                    System.out.println("Entrada encontrada em: " + atual);
                    encontrou = true;
                }

                if (labirinto[l][c] == 'S') {
                    qtdSaida++;
                }

                if (qtdSaida == 0) {
                    throw new Exception("Saída do labirinto não encontrada!");
                }
                
                if (qtdEntrada == 1 && qtdSaida == 1) {
                    break;
                }

                if (qtdEntrada >  1 || qtdSaida > 1) {
                    throw new Exception("O labirinto deve conter exatamente uma entrada (E) ou saida(S)");
                }

            }
        }

        if (encontrou == false) {
            throw new Exception("Entrada do labirinto não encontrada!");
        }

    }

    public void ArmazenarCoordenadas() throws Exception {
        FilaParada<Coordenada> fila = new FilaParada<>(3);

        int l = atual.getLinha();
        int c = atual.getColuna();

        
        if (l > 0 && (labirinto[l - 1][c] == ' ' || labirinto[l - 1][c] == 'S')){
            fila.guardeUmItem(new Coordenada(l - 1, c));
        }

        if (l < labirinto.length - 1 && (labirinto[l + 1][c] == ' ' || labirinto[l + 1][c] == 'S')){
            fila.guardeUmItem(new Coordenada(l + 1, c));
        }

        if (c > 0 && (labirinto[l][c - 1] == ' ' || labirinto[l][c - 1] == 'S')){
            fila.guardeUmItem(new Coordenada(l, c - 1));
        }

        if (c < labirinto[l].length - 1 && (labirinto[l][c + 1] == ' ' || labirinto[l][c + 1] == 'S')){
            fila.guardeUmItem(new Coordenada(l, c + 1));
        }

        if (fila.isVazia()){
            throw new Exception("Não há caminhos a partir de " + atual);
        }

      
        Coordenada proximo = fila.recupereUmItem();
        fila.removaUmItem();

        
        caminho.guardeUmItem(atual);
        possibilidades.guardeUmItem(fila);

   
        atual = proximo;

        if (labirinto[atual.getLinha()][atual.getColuna()] == 'S') {
            saidaEncontrada = true;
 
            caminho.guardeUmItem(atual);
            System.out.println("Saída encontrada em: " + atual);
            return;
        }

        labirinto[atual.getLinha()][atual.getColuna()] = '*';
    }

    
    public void VoltarCoordenadas() throws Exception {
    
        if (labirinto[atual.getLinha()][atual.getColuna()] == '*'){
            labirinto[atual.getLinha()][atual.getColuna()] = ' ';
        }

        while (true) {
            if (caminho.isVazia() || possibilidades.isVazia()){
                throw new Exception("Não há mais caminhos para voltar!");
            }

    
            Coordenada de = caminho.recupereUmItem();
            caminho.removaUmItem();

            FilaParada<Coordenada> filaAlternativa = possibilidades.recupereUmItem();
            possibilidades.removaUmItem();

            if (filaAlternativa.isVazia()) {
                if (labirinto[de.getLinha()][de.getColuna()] == '*')
                    labirinto[de.getLinha()][de.getColuna()] = '*';
                atual = de; 
                continue;
            }

            
            caminho.guardeUmItem(de);

            
            atual = filaAlternativa.recupereUmItem();
            filaAlternativa.removaUmItem();

            
            if (labirinto[atual.getLinha()][atual.getColuna()] == 'S') {
                saidaEncontrada = true;
            } else if (labirinto[atual.getLinha()][atual.getColuna()] != 'E') {
                labirinto[atual.getLinha()][atual.getColuna()] = '*';
            }


            caminho.guardeUmItem(atual);
            possibilidades.guardeUmItem(filaAlternativa);
            break;
        }
    }

    public boolean chegouNaSaida() {
        return saidaEncontrada;
    }

    public static void pintarCaminhoFinal(Labirinto lab) throws Exception {
        Character[][] matriz = lab.getLabirinto();
 
        //for (int l = 0; l < matriz.length; l++)
            //for (int c = 0; c < matriz[l].length; c++)
                //if (matriz[l][c] == '*') matriz[l][c] = '*';
 
        Pilha<Coordenada> caminho = lab.getCaminho();
        Pilha<Coordenada> aux = new Pilha<>(lab.getLinhas() * lab.getColunas());
 
        while (!caminho.isVazia()) {
            aux.guardeUmItem(caminho.recupereUmItem());
            caminho.removaUmItem();
        }
 
        while (!aux.isVazia()) {
            Coordenada coord = aux.recupereUmItem();
            aux.removaUmItem();
            caminho.guardeUmItem(coord);
 
            char ch = matriz[coord.getLinha()][coord.getColuna()];
            if (ch != 'E' && ch != 'S')
                matriz[coord.getLinha()][coord.getColuna()] = '*';
        }
    }

    
    public static void imprimirCaminhoFinal(Labirinto lab) throws Exception {
        Pilha<Coordenada> caminho = lab.getCaminho();
        Pilha<Coordenada> inverso = new Pilha<>(lab.getLinhas() * lab.getColunas());
        Pilha<Coordenada> temp    = new Pilha<>(lab.getLinhas() * lab.getColunas());
 
        while (!caminho.isVazia()) {
            inverso.guardeUmItem(caminho.recupereUmItem());
            caminho.removaUmItem();
        }
 
        System.out.print("Caminho da entrada até a saida: ");
        while (!inverso.isVazia()) {
            Coordenada c = inverso.recupereUmItem();
            inverso.removaUmItem();
            System.out.print(c + " ");
            temp.guardeUmItem(c);
        }
        System.out.println();
 
        
        while (!temp.isVazia()) {
            caminho.guardeUmItem(temp.recupereUmItem());
            temp.removaUmItem();
        }
    }
}