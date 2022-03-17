// Arquivo desenvolvido na AT1
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grafo_ND_P {
	
	private static final Float POSITIVE_INFINITY = null;
	String[] V = null;
	int[][]  E = null;
	float[]  w = null;

	// Construtor vazio
	public Grafo_ND_P() {
		
	}
	
	public Grafo_ND_P (File file) throws IOException {
		ler(file);
	}
	
	// Construtor com par�metros
	public Grafo_ND_P(String[] Vertice,int[][] E_Aresta,float[] w_Peso) {
		// Array de vertices
		V = Vertice;
		// Array de Arestas, sendo N por 3, [N][0] = recebe primeiro elemento da aresta,
		// [N][1] = recebe segundo elemento da aresta, [N][2] = recebe o index desta aresta
		// [N][3] = Marca��o, usado para saber o estado da aresta "fechado" ou "aberto"
		E = E_Aresta;
		// Array de pesos
		w = w_Peso;
	}
	
	// Leitura de um arquivo e inser��o do mesmo em uma lista, sendo que cada indice cont�m uma linha completa
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
		return V.length;
	}
	
	// Verifica a quantidade de Arestas por meio do tamanho do array E
	protected int qtdArestas() {
		return E.length;
	}
	
	// Verifica o grau de uma vertice por meio de um contador de arestas
	protected int grau(int v) {
		// Contador utilizado para contagem de grau
		int count = 0;
		// Percorre todas as arestas
		for (int i = 0; i < E.length; i++) {
			// Verifica se a aresta possue o vertice do par�metro no primeiro elemento
			if (E[i][0] == v) {
				// Incrementa contador
				count++;
			}
			// Verifica se a aresta possue o vertice do par�metro no segundo elemento
			if(E[i][1] == v ) {
				// Incrementa contador
				count++;
			}
		}
		// Retorna o contador
		return count;
	}
	
	// Retorna o conteudo do Array de Vertices(R�tulo)
	protected String rotulo(int index) {
		return V[index-1];
	}
	
	// Verifica nas arestas quais liga��es o vertice possue, e retorna o seus vizinhos
	protected List<Integer> vizinhos(int v) {
		// Lista para armazenar os vizinhos
	    List<Integer> viz = new ArrayList<Integer>();
	    // Percorre todas as arestas
	    for (int i = 0; i < E.length; i++) {
	    	// Verifica se a aresta possue o vertice do par�metro no primeiro elemento
	    	if (E[i][0] == v) {
	    		// Adiciona o segundo elemento como vizinho
	    		viz.add(E[i][1]);
			}
	    	// Verifica se a aresta possue o vertice do par�metro no segundo elemento
			if(E[i][1] == v ) {
				// Adiciona o primeiro elemento como vizinho
				viz.add(E[i][0]);
			}
		}
	    // Retorna lista de vizinhos
		return viz;
	}
	
	// Verifica exist�ncia de aresta entre u e v
	protected boolean haAresta(int u, int v) {
		// Percorre todas as arestas
		for (int i = 0; i < E.length; i++) {
			// Verifica se u � o primeiro elemento da aresta e v � o segundo elemento, al�m de verificar o caso inverso
			if ((E[i][0] == u && E[i][1] == v) || (E[i][0] == v && E[i][1] == u)) {
				// Retorna verdadeiro
				return true;
			}
		}
		// Retorna false por default
		return false;
	}
	
	// Verifica o peso da aresta entre u e v
	protected Float peso(int u, int v) {
		// Percorre todas as arestas
		for (int i = 0; i < E.length; i++) {
			// Verifica se u � o primeiro elemento da aresta e v � o segundo elemento, al�m de verificar o caso inverso
			if ((E[i][0] == u && E[i][1] == v) || (E[i][0] == v && E[i][1] == u)) {
				// Retorna seu peso
				return w[E[i][2]];
			}
		}
		// Retorna peso infinito positivo caso n�o exista areta entre os dois
		return POSITIVE_INFINITY;
	}
	
	// Retorna uma lista das arestas em ordem crescente de peso usando bubble sort
	protected int[][] arestasCrescentes() {
		int [][] result = this.E;
		for (int j=0; j<this.qtdArestas()-1; j++) {
			for (int i=0; i<this.qtdArestas()-1-j; i++) {
				if (this.peso(result[i][0], result[i][1]) > this.peso(result[i+1][0], result[i+1][1])) {
					int[] temp = new int[3];
					temp[0] = result[i+1][0];
					temp[1] = result[i+1][1];
					// a terceira casa no array de arestas representa o index no array de peso
					temp[2] = result[i+1][2];
					result[i+1][0] = result[i][0];
					result[i+1][1] = result[i][1];
					result[i+1][2] = result[i][2];
					result[i][0] = temp[0];
					result[i][1] = temp[1];
					result[i][2] = temp[2];
				}
			}
		}
		return result;
	}
	
	// Leitura do arquivo e inser��o nos atributos do Grafo
	protected void ler(File file) throws IOException {
		// Chama o m�todo para colocar o arquivo em uma lista
		List l = readFileInList(file.getPath());
		
		// Variavel para conta de Vertices(numero de linhas at� chegar nas arestas - 1)
		int lineEdges = -1;
		
		// Coloca a primeira linha em uma string
		String strFor1 = (String) l.get(0);
		// Manipula a string para pegar o valor de Vertices
		lineEdges = Integer.parseInt(strFor1.substring(10));
		
		// Array para as Vertices
		String[] Vert = new String [lineEdges];
		
		// Calculo para verificar o numero de Arestas
		int calcAr = l.size() - lineEdges-2;
		
		// Array para os Pesos
		float[] Pesos = new float[calcAr];
		
		// MultArray para as Arestas
		int[][] Arest = new int[calcAr][4];
		
		// Padr�o utilizado para pegar os valores das arestas e pesos
		Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");
		// Lista que recebe os valores dos pesos e arestas
		List<Float> arestasPesosList = new ArrayList<Float>();
		
		// Percorre as linhas do arquivo que possuem as arestas e pesos
		for (int i = lineEdges+1; i < l.size(); i++) {
			// Verifica o pardrao com o texto
			Matcher m = p.matcher((CharSequence) l.get(i));
			// Onde o padr�o bater com o descrito
			while (m.find()) {
				// Adiciona a lista os valores
				arestasPesosList.add(Float.parseFloat(m.group()));
			}
		}
		
		// Percorre o numero de vertices vezes
		for (int i = 0; i < lineEdges; i++) {
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Pesos
			// Index do Peso - Index da arestasPesoList
			// 0-2 1-5 2-8 3-11 4-14 5-17 6-20
			Pesos[i] = arestasPesosList.get((i)+2*(i+1));	
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Arestas
			// Index da Aresta[i][0] - Index da arestasPesoList
			// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
			Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Arestas
			// Index do da Aresta[i][1] - Index da arestasPesoList
			// 0-1 1-4 2-7 3-10 4-13 5-16 6-19		
			Arest[i][1] = Math.round(arestasPesosList.get(((i)*3)+1));
			// Recebe o index
			Arest[i][2] = (i);
			// str1 definida para pegar todas as Vertices
			String str1 = (String) l.get(i+1);
			// Retira '"' da string
			str1 = str1.replace("\"", "");
			// Separa a string em partes
			String[] parts = str1.split(" ");
			// Retira o numero do vertice da string
			parts[0] = "";
			// Junta todas as partes
			str1 = String.join(" ",parts);
			// Retira espa�o em branco no inicio da string
			str1 = str1.substring(1);
			// Coloca o rotulo da vertice no array
			Vert[i] = str1;
		}
		
		// Percorre desde o numero de vertices at� o numero de arestas
		for (int i = lineEdges; i < calcAr; i++) {
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Pesos
			// Index do Peso - Index da arestasPesoList
			// 0-2 1-5 2-8 3-11 4-14 5-17 6-20
			Pesos[i] = arestasPesosList.get((i)+2*(i+1));	
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Arestas
			// Index da Aresta[i][0] - Index da arestasPesoList
			// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
			Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
			// Como a lista de valores possue tanto pesos quanto arestas, utiliza l�gica de PA
			// Para inserir no array de Arestas
			// Index do da Aresta[i][1] - Index da arestasPesoList
			// 0-1 1-4 2-7 3-10 4-13 5-16 6-19		
			Arest[i][1] = Math.round(arestasPesosList.get(((i)*3)+1));
			// Recebe o index
			Arest[i][2] = (i);
		}		
		// w recebe os Pesos
		w = Pesos;
		// E recebe as Arestas
		E = Arest;
		// V recebe os Vertices
		V = Vert;	
	}
	
	// =========================== Ciclo Euleriano ===
	// Verifica se todos os graus s�o par, caso n�o sejam, o ciclo Euleriano n�o existe
	protected boolean existeCicloEuleriano() {	
		// Percorre o numero de Vertices
		for (int i = 1; i <= V.length; i++) {
			// Chama o m�todo que pega o grau e verifica se � par
			if ((grau(i)) % 2 != 0) {
				// Caso n�o par retorna 0
				return false;
			}
		}
		// Caso todos sejam pares retorna 1 
		return true;
	}
	
	// Encontra o index e outro vertice
	protected List<Integer> findByNumber(int path, int v) {
		// Lista para index e Vertice
		List<Integer> indexN2 = new ArrayList<Integer>();
		// Percorre todas as Arestas
		for (int i = 0; i < E.length; i++) {
			// Caso aresta esteja disponivel, e valores das arestas estejam corretas
			if (E[i][1] == path && E[i][0] == v && E[i][3] == 1) {
				// Adiciona index
				indexN2.add(i);
				// Adiciona Vertice da aresta
				indexN2.add(E[i][1]);
				// retorna Lista
				return indexN2;
			}
			// Caso aresta esteja disponivel, e valores das arestas estejam corretas
			if (E[i][0] == path && E[i][1] == v && E[i][3] == 1) {
				// Adiciona index
				indexN2.add(i);
				// Adiciona Vertice da aresta
				indexN2.add(E[i][0]);
				// retorna Lista
				return indexN2;
			}
		}
		// Retorna null, caso n tenha
		return null;
	}
	
	// Busca o caminho euleriano por meio do primeiro Vertice
	protected List<Integer> caminhoCicloEuleriano() {
		// Arestas podem estar "abertas" ou "fechadas", caso abertas o caminho pode passar por elas, caso fechadas
		// n�o passa
		
		// Deixa todas as Arestas como abertas
		for (int i = 0; i < E.length; i++) {
			E[i][3] = 1;
		}
		
		// Lista do Caminho
		List<Integer> pathFinal = new ArrayList<Integer>();
		
		// Lista para o index e o outro vertice
		List<Integer> indexN2 = new ArrayList<Integer>();
		
		// Vertice usado para o caminho
		int v = 1;
		// Adiciona vertice ao caminho
		pathFinal.add(v);
		while (true) {
			// Lista de vizinhos para verificar os caminhos
			List<Integer> paths = vizinhos(v);
			// Sort para sempre buscar o caminho com menor indice dos Vertices disponiveis
			paths.sort(null);
			// Percorre o tamanho dos vizinhos
			for (int i = 0; i < paths.size(); i++) {
				// Busca o vizinho que possui disponibilidade
				indexN2 = findByNumber(paths.get(i), v);
				// Se est� aresta est� dispon�vel
				if (indexN2 != null) {
					// Adiciona vertice ao caminho
					pathFinal.add(indexN2.get(1));
					// v recebe novo valor
					v = indexN2.get(1);
					// Fecha a aresta
					E[indexN2.get(0)][3] = 0;
					// "Pula" o for
					break;
				}
			}
			// Caso v volte a ser 1, encerra o while
			if (v == 1) {
				// "Pula" o while
				break;
			}
		}
		// Retorna o caminho
		return pathFinal;
	}
	
}
