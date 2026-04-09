public class Main
{
	public static void main(String[] args)
	{
	    Pilha<String> pil = new Pilha<String> ();
	    
	    try
	    {
    	    pil.guardeUmItem("PYTHON");
    	    pil.guardeUmItem("C");
    	    pil.guardeUmItem("JAVA");
    	    pil.guardeUmItem("C++");
    	    pil.guardeUmItem("HTML");
    	    
    	    System.out.println (pil.recupereUmItem());
    	    pil.removaUmItem   ();
    	    System.out.println (pil.recupereUmItem());
    	    pil.removaUmItem   ();
    	    System.out.println (pil.recupereUmItem());
    	    pil.removaUmItem   ();
	    }
	    catch (Exception erro)
	    {
	        System.err.println (erro.getMessage());
	    }
	}
}
