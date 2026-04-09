package labirinto;

import java.util.List;

public class Labirinto extends Pilha<Coordenada> {

    private String nomeArquivo;

    private List<Character> labirinto;
    
    private int coluna, linha;


    public void setLabirinto(List<Character> labirinto) {
        this.labirinto = labirinto;
    }

    public List<Character> getLabirinto() {
        return labirinto;
    }

    public Labirinto(String nomeArquivo, List<Character> labirinto, int coluna, int linha){
        this.nomeArquivo = nomeArquivo;
        this.labirinto = labirinto;
        this.coluna = coluna;
        this.linha = linha;
    }

    public String getNomeArquivo(String nomeArquivo){
        return nomeArquivo;
    }
    
    public void setNomeArquivo(String nomeArquivo){
        this.nomeArquivo = nomeArquivo;
    }

    public void lerLabirinto(List<Character> labirinto){

    }

    public Pilha<Coordenada> caminho(List<Character> labirinto, int coluna, int linha){
        return labirinto.guardeUmItem(new Coordenada(coluna, linha));
    }

    public Pilha<Fila<Coordenada>> possibilidades(List<Character> labirinto, int coluna, int linha){
        return labirinto.guardeUmItem(new Coordenada(coluna, linha));
    }   

    
    

}