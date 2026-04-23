package labirinto;

public class Coordenada implements Cloneable {
    private int linha;
    private int coluna;

    public Coordenada(int linha, int coluna) throws Exception {
        if(linha < 0) throw new Exception("Quantidade de linhas Inválida!");
        if(coluna < 0) throw new Exception("Quantidade de colunas Inválidas!");

        this.linha = linha;         // posição
        this.coluna = coluna;       // posição
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) throws Exception {
        if(linha <= 0) throw new Exception("Quantidade de linhas Inválida!");
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) throws Exception {
        if(coluna <= 0) throw new Exception("Quantidade de colunas Inválidas!");
        this.coluna = coluna;
    }

    @Override
    public String toString() {
        return "(" + linha + "," + coluna + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() != this.getClass()) return false;
        Coordenada c = (Coordenada) obj;
        return this.linha == c.linha && this.coluna == c.coluna;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 7 + ((Integer)this.linha).hashCode();
        ret = ret * 7 + ((Integer)this.coluna).hashCode();

        if ( ret < 0 ) {
            ret=-ret;
        }

        return ret;
    }

    @Override
    public Coordenada clone() {
        Coordenada ret = null;
        try {
            ret = new Coordenada(linha, coluna);
        } catch(Exception erro) {}

        return ret;
    }
    
}
