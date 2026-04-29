package classesAuxiliares;

public class FilaAnda<X> implements Cloneable {
    private Object[]    elemento;
    private final int tamanhoInicial;
    private int ultimo = -1;
    private Clonador<X> clonador;

    public FilaAnda(int tamanho) throws Exception {
        if (tamanho <= 0) throw new Exception("Tamanho Inválido!");
        this.elemento = new Object[tamanho];
        this.tamanhoInicial = tamanho;
        this.clonador = new Clonador<X> ();
    }

    public FilaAnda() throws Exception{
        this.elemento = new Object[10];
        this.tamanhoInicial = 10;

        this.clonador = new Clonador<X> ();
    }

    private void redimensioneSe(int novoTamanho) throws Exception {
        Object[] novo = new Object[novoTamanho];

        for(int i = 0; i < this.ultimo; i++) {
            novo[i] = this.elemento[i];
        }

        this.elemento = novo;
    }

    public void guardeUmItem(X x) throws Exception {
        if(x == null) throw new Exception("Falta o que guardar!");

        if(this.ultimo+1 == this.elemento.length)
            this.ultimo ++;

        if(x instanceof Cloneable) 
            this.elemento[this.ultimo] = this.clonador.clone(x);
        else
            this.elemento[this.ultimo] = x;
    }

    public X recupereUmItem() throws Exception {
        if(this.ultimo == -1) throw new Exception("Nada a recuperar!");

        X ret = null;
        if(this.elemento[this.ultimo] instanceof Cloneable)
            ret = this.clonador.clone((X)this.elemento[0]);
        else
            ret = (X)this.elemento[0];

        return ret;
    }

    public void removaUmItem() throws Exception {
        if(this.ultimo == -1) throw new Exception("Nada a remover");

        for(int i = 0; i < this.ultimo; i++) {
            this.elemento[i] = this.elemento[i + 1];
        }

        this.elemento[this.ultimo] = null;
        this.ultimo--;

        if(this.elemento.length > this.tamanhoInicial && this.ultimo+1 <= this.elemento.length / 4)
            this.redimensioneSe(this.elemento.length / 2);
    }

    public boolean isCheia() {
        return this.ultimo+1 == this.elemento.length;
    }

    public boolean isVazia() {
        return this.ultimo == -1;
    }

    @Override
    public String toString() {
        String ret = (this.ultimo+1) + "elemento(s)";

        if(this.ultimo != 1)
            ret += ", sendo o ultimo" + this.elemento[this.ultimo];

        return ret;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if(obj.getClass() != this.getClass()) {
            return false;
        }

        FilaAnda<X> fil = (FilaAnda<X>) obj;

        if (this.ultimo != fil.ultimo) return false;

        for (int i = 0; i < this.ultimo; i++ ) {
            if(!this.elemento[i].equals(fil.elemento[i]))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int ret = 666;

        ret = ret * 7 + ((Integer)(this.ultimo)).hashCode();
        ret = ret * 7 + ((Integer)(this.tamanhoInicial)).hashCode();

        for (int i = 0; i <= this.ultimo; i++) {
            ret = ret * 7 + this.elemento[i].hashCode();
        }

        if (ret < 0) {
            ret=-ret;
        }

        return ret;
    }

    public FilaAnda(FilaAnda<X> modelo) throws Exception {
        if(modelo == null) throw new Exception("Modelo Ausente!");

        this.tamanhoInicial = modelo.tamanhoInicial;
        this.ultimo = modelo.ultimo;
        this.clonador = modelo.clonador;

        this.elemento = new Object[modelo.elemento.length];

        for(int i = 0; i <= modelo.ultimo; i++) {
            this.elemento[i] = modelo.elemento[i];
        }
    }

    @Override
    public Object clone() {
        FilaAnda<X> ret = null;

        try {
            ret = new FilaAnda<X>(this);
        } catch(Exception erro) {

        }

        return ret;
    }
}
