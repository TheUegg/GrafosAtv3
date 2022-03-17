import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		
		/*
		Scanner reader = new Scanner(System.in);
		System.out.println("Qual o vertice inicial do fluxo?");
		int a = reader.nextInt();
		System.out.println("Qual o vertice final do fluxo?");
		int b = reader.nextInt();
		System.out.println();
		*/
		System.out.println("============================================================");
		System.out.println("Exercício 1 | grafo: fluxo_maximo_aula.net | s:1 t=6");
		File file = new File("./grafos/fluxo_maximo_aula.net");
		List<Edge>[] grafo;
		grafo = Edge.ler(file);
		//System.out.println(Edge.fluxo(grafo, a-1, b-1));
		System.out.println(Edge.fluxo(grafo, 1-1, 6-1));
		System.out.println();
		System.out.println("============================================================");
		System.out.println("Exercício 2 | grafo: gr128_10.net");
		File gr128_10File = new File("./grafos/gr128_10.net");
		HopcroftKarp hk = new HopcroftKarp(gr128_10File);
		System.out.println("\n");
		System.out.println("============================================================");
		System.out.println("Exercício 3 | grafo: cor3.net");
		File cor3File = new File("./grafos/cor3.net");
		Lawler ll = new Lawler(cor3File);
		
	}

}
