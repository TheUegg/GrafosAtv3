import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Edge {
		    int s, t, cap, f;

		    //Formato para cada aresta
		    public Edge(int s, int t, int cap) {
		      this.s = s;
		      this.t = t;
		      this.cap = cap;
		    }

		    //Adiciona as vertices no grafo, com todas as suas informações
			public static void addAresta(List<Edge>[] grafo, int s, int t, int cap) {
				grafo[s].add(new Edge(s, t, cap));
				grafo[t].add(new Edge(t, s, 0));
			}
		    
			//Criação do grafo, no caso um array de Edges com as vertices
			public static List<Edge>[] criarGrafo(int nodes) {
				List<Edge>[] grafo = new List[nodes];
				for (int i = 0; i < nodes; i++)
					grafo[i] = new ArrayList<>();
				return grafo;
			}

		  
		  
			//Edmond-karp fluxo maximo
			public static int fluxo(List<Edge>[] grafo, int s, int t) {
				//fluxo 
				int fluxo = 0;
				//seta uma lista Fila
				int[] Fila = new int[grafo.length];
				//loop para rodar até finalizar o processo
				while (1 == 1) {
					//incializa o contador
					int count = 0;
					//Fila recebe o primeiro elemento
					Fila[count++] = s;
					//lista de arestas/caminho
					Edge[] caminho = new Edge[grafo.length];
					//loop referente a fila enquanto n acessa o elemento final e o contador n passe do loop
					for (int x = 0; x < count && caminho[t] == null; x++) {
						//loop para os vizinhos
						for (Edge e : grafo[Fila[x]]) {
							//caso a capacidade é maior que o peso e o elemento destino da aresta exista
							if (caminho[e.t] == null && e.cap > e.f) {
								//caminho recebe a aresta
								caminho[e.t] = e;
								//Fila recebe o destino da aresta
								Fila[count++] = e.t;
							}
						}
					}
					//Caso o caminho n encontre o elemento final
					if (caminho[t] == null)
						break;
					// variavel infinita positiva que no caso é a capacidade minima do caminho
					int minCap = Integer.MAX_VALUE;
					//loop do caminho de volta
					for (int u = t; u != s; u = caminho[u].s)
						//verifica a menor capacidade do caminho
						minCap = Math.min(minCap, caminho[u].cap - caminho[u].f);
					//loop do caminho de volta
					for (int w = t; w != s; w = caminho[w].s) {
						//incremento no valor da capacidade 
						caminho[w].f += minCap;
					}
					//adiciona o valor no fluxo
					fluxo += minCap;
				}
				//retorna o fluxo
				return fluxo;
			}
			
			//Leitura de um arquivo e inserção do mesmo em uma lista, sendo que cada indice contém uma linha completa
			public static List<String> readFileInList(String fileName) {
				List<String> lines = Collections.emptyList();
				try
				{
					lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return lines;
			}  
		  
			// Leitura do arquivo e inserção nos atributos do Grafo
			public static List<Edge>[] ler(File file) throws IOException {
				// Chama o método para colocar o arquivo em uma lista
				List l = readFileInList(file.getPath());
				// Variavel para conta de Vertices(numero de linhas até chegar nas arestas - 1)
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
				// Padrão utilizado para pegar os valores das arestas e pesos
				Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");
				// Lista que recebe os valores dos pesos e arestas
				List<Float> arestasPesosList = new ArrayList<Float>();		
				// Percorre as linhas do arquivo que possuem as arestas e pesos
				for (int i = lineEdges+1; i < l.size(); i++) {
					// Verifica o pardrao com o texto
					Matcher m = p.matcher((CharSequence) l.get(i));
					// Onde o padrão bater com o descrito
					while (m.find()) {
						// Adiciona a lista os valores
						arestasPesosList.add(Float.parseFloat(m.group()));
					}
				}	
				// Percorre o numero de vertices vezes
				for (int i = 0; i < lineEdges; i++) {
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
					// Para inserir no array de Pesos
					// Index do Peso - Index da arestasPesoList
					// 0-2 1-5 2-8 3-11 4-14 5-17 6-20
					Pesos[i] = arestasPesosList.get((i)+2*(i+1));	
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
					// Para inserir no array de Arestas
					// Index da Aresta[i][0] - Index da arestasPesoList
					// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
					Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
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
					// Retira espaço em branco no inicio da string
					str1 = str1.substring(1);
					// Coloca o rotulo da vertice no array
					Vert[i] = str1;
				}	
				// Percorre desde o numero de vertices até o numero de arestas
				for (int i = lineEdges; i < calcAr; i++) {
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
					// Para inserir no array de Pesos
					// Index do Peso - Index da arestasPesoList
					// 0-2 1-5 2-8 3-11 4-14 5-17 6-20
					Pesos[i] = arestasPesosList.get((i)+2*(i+1));	
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
					// Para inserir no array de Arestas
					// Index da Aresta[i][0] - Index da arestasPesoList
					// 0-0 1-3 2-6 3-9  4-12 5-15 6-18
					Arest[i][0] = Math.round(arestasPesosList.get((i)*3));
					// Como a lista de valores possue tanto pesos quanto arestas, utiliza lógica de PA
					// Para inserir no array de Arestas
					// Index do da Aresta[i][1] - Index da arestasPesoList
					// 0-1 1-4 2-7 3-10 4-13 5-16 6-19		
					Arest[i][1] = Math.round(arestasPesosList.get(((i)*3)+1));
					// Recebe o index
					Arest[i][2] = (i);
				}		
				List<Edge>[] grafo = criarGrafo(Vert.length);
				for (int i = 0; i < Arest.length; i++) {
					addAresta(grafo,Arest[i][0]-1,Arest[i][1]-1,(int)Pesos[i]);
				}
				return grafo;
			}
	}
