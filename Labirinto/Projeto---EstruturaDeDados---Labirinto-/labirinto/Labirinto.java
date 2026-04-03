package labirinto;


public class Labirinto {

    private int linhas; // Quantidade de linhas do labirinto
    private int colunas; // Quantidade de colunas do labirinto

    public Labirinto(int linhas, int colunas) throws Exception{
        this.linhas = linhas;
        this.colunas = colunas;

        if ( linhas <= 0 )  throw new Exception("Quantidade de linhas Inválido!");
        if ( colunas <= 0 ) throw new Exception("Qauntidade de colunas Inválido!");
    }

    public String getLinhas(int linhas) {
        return "Quantidade de linhas: " + this.linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public String getColunas(int colunas) {
        return "Quantidade de colunas: " + this.colunas;
    }

    public void setColunas(int colunas) {

    }
}