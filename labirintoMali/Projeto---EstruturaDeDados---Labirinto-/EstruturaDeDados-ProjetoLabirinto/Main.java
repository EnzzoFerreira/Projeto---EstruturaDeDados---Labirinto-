import labirinto.Labirinto;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o nome do arquivo do labirinto: ");
            String nomeArquivo = scanner.nextLine();

            Labirinto labirinto = new Labirinto(nomeArquivo);

            System.out.println("\nLabirinto Original:");
            imprimirLabirinto(labirinto.getLabirinto());

            labirinto.encontrarEntrada(labirinto.getLabirinto());

            System.out.println("\nIniciando a Resolução....");

            while (!labirinto.chegouNaSaida()) {
                try {

                    labirinto.ArmazenarCoordenadas();

                } catch (Exception e) {
                    
                    try {
                        labirinto.VoltarCoordenadas();
                        
                    } catch (Exception e2) {
                        System.out.println("Não existe caminho que leva da entrada até a saída.");
                        return;
                    }
                }
            }

            System.out.println("\nLabirinto com caminho encontrado:");
            Labirinto.pintarCaminhoFinal(labirinto);
            imprimirLabirinto(labirinto.getLabirinto());

            System.out.println();
            Labirinto.imprimirCaminhoFinal(labirinto);

        } catch (Exception erro) {
            System.out.println("Erro: " + erro.getMessage());
            erro.printStackTrace();
        }
    }

    public static void imprimirLabirinto(Character[][] labirinto) {
        for (int l = 0; l < labirinto.length; l++) {
            for (int c = 0; c < labirinto[l].length; c++) {
                System.out.print(labirinto[l][c]);
            }
            System.out.println();
        }
    }
}