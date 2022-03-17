import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		
		Scanner reader = new Scanner(System.in);
		File file = new File("C:\\Users\\gabri\\Downloads\\fluxo_maximo_aula.net");
		List<Edge>[] grafo;
		grafo = Edge.ler(file);
		System.out.println("Qual o vertice incial do fluxo?");
		int a = reader.nextInt();
		System.out.println("Qual o vertice final do fluxo?");
		int b = reader.nextInt();
		System.out.println("============================================================");
		System.out.println("Exercício 1 | grafo: fluxo_maximo_aula.net");
		System.out.println(Edge.fluxo(grafo, a-1, b-1));
	}

}


