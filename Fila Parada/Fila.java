import java.lang.reflect.*;

/* Fila Parada */

public class Fila <X> implements Cloneable
{
    private Object[]    elemento; // private X[] elemento;
    private final int   tamanhoInicial;
    private int         inicio=0, fim=0, qtd=0;
    private Clonador<X> clonador;

    public Fila (int tamanho) throws Exception
    {
        if (tamanho<=0)
            throw new Exception ("Tamanho invalido");

        this.elemento       = new Object [tamanho]; //this.elemento=new X [tamanho];
        this.tamanhoInicial = tamanho;
        
        this.clonador = new Clonador<X> ();
    }
    
    public Fila ()
    {
        this.elemento       = new Object [10]; //this.elemento=new X [10];
        this.tamanhoInicial = 10;
        
        this.clonador = new Clonador<X> ();
    }
    
    private void redimensioneSe (int novoTamanho)
    {
        // X[] novo = new X [novoTamanho];
        Object[] novo = new Object [novoTamanho];
        
        int atual=this.inicio;
        for(int i=0; i<this.qtd; i++, atual=atual==this.elemento.length-1?0:atual+1)
            novo[i] = this.elemento[atual];

        this.elemento = novo;
        // System.gc();
        
        this.inicio=0;
        this.fim   =this.qtd;
    }

    public void guardeUmItem (X x) throws Exception // FIFO
    {
        if (x==null)
            throw new Exception ("Falta o que guardar");

        if (this.qtd==this.elemento.length) // cheia
            this.redimensioneSe (2*this.elemento.length);

        if (x instanceof Cloneable)
            this.elemento[this.fim]=this.clonador.clone(x);
        else
            this.elemento[this.fim]=x;
            
        this.fim=this.fim==this.elemento.length-1?0:this.fim+1;
        this.qtd++;
    }

    public X recupereUmItem () throws Exception // FIFO
    {
        if (this.qtd==0) // vazia
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elemento[this.inicio] instanceof Cloneable)
            ret = this.clonador.clone((X)this.elemento[this.inicio]);
        else
            ret = (X)this.elemento[this.inicio];

        return ret;
    }

    public void removaUmItem () throws Exception // FIFO
    {
        if (this.qtd==0) // vazia
            throw new Exception ("Nada a remover");

        this.elemento[this.inicio] = null;
        this.inicio=this.inicio==this.elemento.length-1?0:this.inicio+1;

        if (this.elemento.length>this.tamanhoInicial &&
            this.qtd<=this.elemento.length/4)
            this.redimensioneSe (this.elemento.length/2);
    }

    public boolean isCheia ()
    {
		return this.qtd==this.elemento.length;
		/*
        if(this.qtd==this.elemento.length)
            return true;

        return false;
        */
    }

    public boolean isVazia ()
    {
		return this.qtd==0;
		/*
        if (this.qtd==0)
            return true;

        return false;
        */
    }

    @Override
    public String toString ()
    {
        String ret = this.qtd + " elemento(s)";
        
        if (this.qtd!=0)
            ret += ", sendo o primeiro "+this.elemento[this.inicio];
            
        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if(obj==this)
            return true;

        if(obj==null)
            return false;

        if(obj.getClass()!=this.getClass())
            return false;

        Fila<X> fil = (Fila<X>) obj;

        if(this.qtd!=fil.qtd)
            return false;
        /*
        if(this.tamanhoInicial!=fil.tamanhoInicial)
            return false;
        */

        for(int i=0,
            atualThis=this.inicio,
            atualFil=fil.inicio;
            
            i<this.qtd; // i<fil.qtd
            
            i++,
            atualThis=atualThis==this.elemento.length-1?0:atualThis+1,
            atualFil=atualFil==this.elemento.length-1?0:atualFil+1)
            
            if(!this.elemento[atualThis].equals(fil.elemento[atualFil]))
                return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret=666/*qualquer positivo*/;

        ret = ret*7/*primo*/ + ((Integer)(this.qtd           )).hashCode();
      //ret = ret*7/*primo*/ + ((Integer)(this.tamanhoInicial)).hashCode();

        for(int i=0,
            atual=this.inicio;
            
            i<this.qtd;
            
            i++,
            atual=atual==this.elemento.length-1?0:atual+1)

            ret = ret*7/*primo*/ + this.elemento[atual].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }

    // construtor de copia
    public Fila (Fila<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo ausente");

        this.tamanhoInicial = modelo.tamanhoInicial;
        this.inicio         = modelo.inicio;
        this.fim            = modelo.fim;
        this.qtd            = modelo.qtd;
        this.clonador       = modelo.clonador;

        // para fazer a copia dum vetor
        // precisa criar um vetor novo, com new
        // nao pode fazer this.elemento=modelo.elemento
        // pois se assim fizermos estaremos com dois
        // objetos, o this e o modelo, compartilhando
        // o mesmo vetor
        this.elemento = new Object[modelo.elemento.length]; // this.elemento = new X [modelo.elemento.length];

        for(int i=0,
            atual=this.inicio;
            
            i<this.qtd;
            
            i++,
            atual=atual==this.elemento.length-1?0:atual+1)
            
            this.elemento[atual] = modelo.elemento[atual];
    }

    @Override
    public Object clone ()
    {
        Fila<X> ret=null;

        try
        {
            ret = new Fila<X>(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}
