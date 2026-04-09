public class Main
{
	public static void main(String[] args)
	{
	    try
	    {
    	    FilaA<String> fil = new FilaA<String> (1000000);
	    
			long inicio = System.currentTimeMillis();			
			for (int i=0; i<1000000; i++)
    	        fil.guardeUmItem("COTUCA");
            long fim = System.currentTimeMillis();			
    	    long tempoDecorrido = fim-inicio;
    	    System.out.println("Para encher a fila levou " +
    	                       tempoDecorrido + " ms");
    	    
            inicio = System.currentTimeMillis();			
			for (int i=0; i<1000000; i++)
			{
				String desenfileirado = fil.recupereUmItem();
				// System.out.println(desenfileirado);
    	        fil.removaUmItem();
    	    }
            fim = System.currentTimeMillis();			
    	    tempoDecorrido = fim-inicio;
    	    System.out.println("Para esvaziar a fila levou " +
    	                       tempoDecorrido + " ms");
	    }
	    catch (Exception erro)
	    {
	        System.err.println (erro.getMessage());
	    }
	}
}
