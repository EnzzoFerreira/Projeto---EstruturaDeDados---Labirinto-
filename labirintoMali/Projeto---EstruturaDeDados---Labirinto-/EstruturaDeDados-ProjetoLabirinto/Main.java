import labirinto.Labirinto;

public class Main {

    public static void main(String[] args) {
        try {

            String nomeArquivo = "EstruturaDeDados-ProjetoLabirinto/testes/teste0.txt";

            Labirinto labirinto = new Labirinto(nomeArquivo);

            System.out.println("Labirinto Original");
            imprimirLabirinto(labirinto.getLabirinto());


            labirinto.encontrarEntrada(labirinto.getLabirinto());

            System.out.println("Iniciando a Resolução....");
            System.out.println();
            while(!labirinto.chegouNaSaida()) {
                try{
                    if(labirinto.chegouNaSaida()) {
                        System.out.println("Saída encontrada!");
                        break;
                    }

                    labirinto.ArmazenarCoordenadas();
                    
                } catch(Exception e) {
                    try{
                        labirinto.VoltarCoordenadas();
                    }
                    catch(Exception e2) {
                        System.out.println("nao ha caminhos para voltar");
                        break;
                    }
                    
                }

            }
            Labirinto.pintarCaminhoFinal(labirinto);
            imprimirLabirinto(labirinto.getLabirinto());

        } catch(Exception erro) {
            System.out.println("Erro:"+ erro.getMessage());
            erro.printStackTrace();
        }

    }

    public static void imprimirLabirinto(Character[][] labirinto) {
        for (int l = 0; l < labirinto.length; l++) {
                for(int c = 0; c < labirinto[l].length; c++) {
                    System.out.print(labirinto[l][c]);
                }
                System.out.println();
        }
    }

}

/* System.out.println("Labirinto carregado com Sucesso!");
            System.out.println();

            for (int l = 0; l < labirinto.getLabirinto().length; l++) {
                for(int c = 0; c < labirinto.getLabirinto()[l].length; c++) {
                    System.out.print(labirinto.getLabirinto()[l][c]);
                }
                System.out.println();
            }

            System.out.println();
            labirinto.encontrarEntrada(labirinto.getLabirinto()); */
