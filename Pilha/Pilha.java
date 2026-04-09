import java.lang.reflect.*;

public class Pilha <X> implements Cloneable
{
    private Object[]    elemento; // private X[] elemento;
    private final int   tamanhoInicial;
    private int         ultimo=-1; // vazio
    private Clonador<X> clonador;

    public Pilha (int tamanho) throws Exception
    {
        if (tamanho<=0)
            throw new Exception ("Tamanho invalido");

        this.elemento       = new Object [tamanho]; //this.elemento=new X [tamanho];
        this.tamanhoInicial = tamanho;
        
        this.clonador = new Clonador<X> ();
    }
    
    public Pilha ()
    {
        this.elemento       = new Object [10]; //this.elemento=new X [10];
        this.tamanhoInicial = 10;
        
        this.clonador = new Clonador<X> ();
    }
    
    private void redimensioneSe (int novoTamanho)
    {
        // X[] novo = new X [novoTamanho];
        Object[] novo = new Object [novoTamanho];
        
        for(int i=0; i<=this.ultimo; i++)
            novo[i] = this.elemento[i];

        this.elemento = novo;
        // System.gc();
    }

    public void guardeUmItem (X x) throws Exception // LIFO
    {
        if (x==null)
            throw new Exception ("Falta o que guardar");

        if (this.ultimo+1==this.elemento.length) // cheia
            this.redimensioneSe (2*this.elemento.length);

        this.ultimo++;

        if (x instanceof Cloneable)
            this.elemento[this.ultimo]=this.clonador.clone(x);
        else
            this.elemento[this.ultimo]=x;
    }

    public X recupereUmItem () throws Exception // LIFO
    {
        if (this.ultimo==-1) // vazia
            throw new Exception ("Nada a recuperar");

        X ret=null;
        if (this.elemento[this.ultimo] instanceof Cloneable)
            ret = this.clonador.clone((X)this.elemento[this.ultimo]);
        else
            ret = (X)this.elemento[this.ultimo];

        return ret;
    }

    public void removaUmItem () throws Exception // LIFO
    {
        if (this.ultimo==-1) // vazia
            throw new Exception ("Nada a remover");

        this.elemento[this.ultimo] = null;
        this.ultimo--;

        if (this.elemento.length>this.tamanhoInicial &&
            this.ultimo+1<=this.elemento.length/4)
            this.redimensioneSe (this.elemento.length/2);
    }

    public boolean isCheia ()
    {
		return this.ultimo+1==this.elemento.length;
		/*
        if(this.ultimo+1==this.elemento.length)
            return true;

        return false;
        */
    }

    public boolean isVazia ()
    {
		return this.ultimo==-1;
		/*
        if (this.ultimo==-1)
            return true;

        return false;
        */
    }

    @Override
    public String toString ()
    {
        String ret = (this.ultimo+1) + " elemento(s)";
        
        if (this.ultimo!=-1)
            ret += ", sendo o ultimo "+this.elemento[this.ultimo];
            
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

        Pilha<X> pil = (Pilha<X>) obj;

        if(this.ultimo!=pil.ultimo)
            return false;
        /*
        if(this.tamanhoInicial!=pil.tamanhoInicial)
            return false;
        */
        for(int i=0 ; i<=this.ultimo;i++)
            if(!this.elemento[i].equals(pil.elemento[i]))
                return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret=666/*qualquer positivo*/;

        ret = ret*7/*primo*/ + ((Integer)(this.ultimo        )).hashCode();
      //ret = ret*7/*primo*/ + ((Integer)(this.tamanhoInicial)).hashCode();

        for (int i=0; i<=this.ultimo; i++)
            ret = ret*7/*primo*/ + this.elemento[i].hashCode();

        if (ret<0)
            ret=-ret;

        return ret;
    }

    // construtor de copia
    public Pilha (Pilha<X> modelo) throws Exception
    {
        if(modelo == null)
            throw new Exception("Modelo ausente");

        this.tamanhoInicial = modelo.tamanhoInicial;
        this.ultimo         = modelo.ultimo;
        this.clonador       = modelo.clonador;

        // para fazer a copia dum vetor
        // precisa criar um vetor novo, com new
        // nao pode fazer this.elemento=modelo.elemento
        // pois se assim fizermos estaremos com dois
        // objetos, o this e o modelo, compartilhando
        // o mesmo vetor
        this.elemento = new Object[modelo.elemento.length]; // this.elemento = new X [modelo.elemento.length];

        for(int i=0;i<=modelo.ultimo;i++)
            this.elemento[i] = modelo.elemento[i];
    }

    @Override
    public Object clone ()
    {
        Pilha<X> ret=null;

        try
        {
            ret = new Pilha<X>(this);
        }
        catch(Exception erro)
        {}

        return ret;
    }
}
