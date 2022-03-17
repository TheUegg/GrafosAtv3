// Arquivo desenvolvido na AT1
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grafo_ND_NP {
	
	private static final Float POSITIVE_INFINITY = null;
	List<Integer> V = new ArrayList<Integer>();
	List<List<Integer>>  E = new ArrayList<List<Integer>>();

	// Construtor vazio
	public Grafo_ND_NP() {
		
	}
	
	public Grafo_ND_NP (File file) throws IOException {
		ler(file);
	}
	
	// Construtor com parï¿½metros
	public Grafo_ND_NP(List<Integer> Vertice, List<List<Integer>> E_Aresta) {
		// Array de vertices
		V = Vertice;
		// Array de Arestas, sendo N por 3, [N][0] = recebe primeiro elemento da aresta,
		// [N][1] = recebe segundo elemento da aresta, [N][2] = recebe o index desta aresta
		// [N][3] = Marcaï¿½ï¿½o, usado para saber o estado da aresta "fechado" ou "aberto"
		E = E_Aresta;
	}
	
	// Leitura de um arquivo e inserï¿½ï¿½o do mesmo em uma lista, sendo que cada indice contï¿½m uma linha completa
	public static List<String> readFileInList(String fileName) {
	    List<String> lines = Collections.emptyList();
	    try
	    {
	      lines =
	       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return lines;
	}
	
	// Verifica a quantidade de vertices por meio do tamanho do array V
	protected int qtdVertices() {
		return V.size();
	}
	
	// Verifica a quantidade de Arestas por meio do tamanho do array E
	protected int qtdArestas() {
		return E.size();
	}
	
	// Verifica o grau de uma vertice por meio de um contador de arestas
	protected int grau(int v) {
		// Contador utilizado para contagem de grau
		int count = 0;
		// Percorre todas as arestas
		for (int i = 0; i < E.size(); i++) {
			// Verifica se a aresta possue o vertice do parï¿½metro no primeiro elemento
			if (E.get(i).get(0) == v) {
				// Incrementa contador
				count++;
			}
			// Verifica se a aresta possue o vertice do parï¿½metro no segundo elemento
			if(E.get(i).get(1) == v ) {
				// Incrementa contador
				count++;
			}
		}
		// Retorna o contador
		return count;
	}
	
	// Retorna o conteudo do Array de Vertices(Rï¿½tulo)
	protected Integer rotulo(int index) {
		return V.get(index-1);
	}
	
	// Verifica nas arestas quais ligaï¿½ï¿½es o vertice possue, e retorna o seus vizinhos
	protected List<Integer> vizinhos(int v) {
		// Lista para armazenar os vizinhos
	    List<Integer> viz = new ArrayList<Integer>();
	    // Percorre todas as arestas
	    for (int i = 0; i < E.size(); i++) {
	    	// Verifica se a aresta possue o vertice do parï¿½metro no primeiro elemento
	    	if (E.get(i).get(0) == v) {
	    		// Adiciona o segundo elemento como vizinho
	    		viz.add(E.get(i).get(1));
			}
	    	// Verifica se a aresta possue o vertice do parï¿½metro no segundo elemento
			if(E.get(i).get(1) == v ) {
				// Adiciona o primeiro elemento como vizinho
				viz.add(E.get(i).get(0));
			}
		}
	    // Retorna lista de vizinhos
		return viz;
	}
	
	// Leitura do arquivo e inserção nos atributos do Grafo
	protected void ler(File file) throws IOException {
		List l = readFileInList(file.getPath());
		List<Integer> vert = new ArrayList<Integer>();
		List<List<Integer>> arest = new ArrayList<List<Integer>>();
		boolean lendoArestas = false;
		for (int i=1; i < l.size(); i++) {
			String line = (String) l.get(i);
			if (line.equals("*edges")) {
				lendoArestas = true;
			} else {
				if (!lendoArestas) {
					vert.add(Integer.valueOf(line));
				} else {
					String[] verts = line.split(" ");
					List<Integer> vertsIntList = new ArrayList<Integer>();
					vertsIntList.add(Integer.valueOf(verts[0]));
					vertsIntList.add(Integer.valueOf(verts[1]));
					arest.add(vertsIntList);
				}
			}
		}
		this.V = vert;
		this.E = arest;
		
		/*System.out.println(Arrays.toString(vert.toArray()));
		for (int i=0; i < arest.size(); i++) {
			System.out.println(Arrays.toString(arest.get(i).toArray()));
		}*/
		
		
	}
	
}
