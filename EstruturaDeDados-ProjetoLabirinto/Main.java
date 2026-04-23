import labirinto.Labirinto;
import labirinto.Coordenada;
import classesAuxiliares.FilaParada;

public class Main {

    public static void main(String[] args) {
        try {

            String nomeArquivo = "testes/teste1.txt";

            Labirinto labirinto = new Labirinto(nomeArquivo);

            Character[][] matriz = labirinto.getLabirinto();

            System.out.println("Labirinto Original");
            imprimirLabirinto(labirinto.getLabirinto());

            System.out.println("Encontrar Entrada");
            labirinto.encontrarEntrada(labirinto.getLabirinto());

            FilaParada<Coordenada> fila = new FilaParada<>(3);

            System.out.println("Iniciando a Resolução....");
            System.out.println();
            while(!labirinto.chegouNaSaida()) {
                labirinto.ArmazenarCoordenadas(labirinto.getLabirinto(), fila);

                imprimirLabirinto(matriz);
                System.out.println("-----------------------------");

                Thread.sleep(500);
            }

            
            
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