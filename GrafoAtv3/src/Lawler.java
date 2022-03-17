import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Lawler extends Grafo_ND_P{
	protected List<List<Integer>> coresPorVertices;
	
	public Lawler(Grafo_ND_P grafo) {
		super(grafo.V, grafo.E, grafo.w);
		executarLawler();
	}
	
	public Lawler(File fileGrafo) throws IOException {
		super(fileGrafo);
		executarLawler();
	}
	
	public void executarLawler() {
		
		int[] X = new int[(int) Math.pow(2.0, Double.valueOf(super.V.length))];
		X[0] = 0;
		List<List<Integer>> S = geraS();
		for (int s=1; s<S.size(); s++) {
			X[s] = Integer.MAX_VALUE;
			Grafo_ND_P g = this.criaGrafoS(S.get(s));
			List<List<Integer>> conjuntosIndependetes = this.encontraConjIndep(g);
			for (int j=0; j<conjuntosIndependetes.size(); j++) {
				int I = this.f(S, conjuntosIndependetes.get(j));
				int i = s - I;
				if (X[i] + 1 < X[s]) {
					X[s] = X[i] + 1;
				}
			}
		}
		printLawler(X[X.length - 1]);
		
	}
	
	public List<List<Integer>> geraS() {
		List<List<Integer>> S = new ArrayList<List<Integer>>();
		for (int i=0; i<Math.pow(2.0, Double.valueOf(super.V.length)); i++) {
			S.add(null);
			if (i > 0) {
				char[] binario = Integer.toBinaryString(i).toCharArray();
				int difer = super.V.length - binario.length;
				List<Integer> verts = new ArrayList<Integer>();
				for (int j=0; j<binario.length; j++) {
					if (binario[j] == '1') {
						verts.add(Integer.valueOf(super.V[j+difer]));
					}
				}
				S.set(i, verts);
			}
		}
		return S;
	}
	
	public Grafo_ND_P criaGrafoS(List<Integer> S) {
		String[] newV = new String[S.size()];
		float[]  neww = null;
		for (int i=0; i<newV.length; i++) {
			newV[i] = S.get(i).toString();
		}
		List<List<Integer>> arestas = new ArrayList<List<Integer>>();
		for (int i=0; i<super.E.length; i++) {
			if(S.contains(super.E[i][0]) && S.contains(super.E[i][1])) {
				List<Integer> values = new ArrayList<Integer>();
				values.add(super.E[i][0]);
				values.add(super.E[i][1]);
				arestas.add(values);
			}
		}
		int[][] newE = new int[arestas.size()][2];
		for (int i=0; i<newE.length; i++) {
			newE[i][0] = arestas.get(i).get(0);
			newE[i][1] = arestas.get(i).get(1);
		}
		Grafo_ND_P gLinha = new Grafo_ND_P(newV, newE, neww); 
		return gLinha;
	}
	
	public List<List<Integer>> encontraConjIndep(Grafo_ND_P grafo) {
		Queue<Integer> q = new PriorityQueue<Integer>();
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		int indexResult = -1;
		for (int i=0; i < grafo.V.length; i++) {
			q.add(Integer.valueOf(grafo.V[i]));
		}
		while (!q.isEmpty()) {
			int x = q.poll();
			indexResult += 1;
			result.add(new ArrayList<Integer>());
			result.get(indexResult).add(x);
			List<Integer> vizX = super.vizinhos(x);
			Integer[] disponiveis = new Integer[q.size()];
			disponiveis = q.toArray(disponiveis);
			for (int i=0; i<disponiveis.length; i++) {
				if (!(vizX.contains(disponiveis[i])) && disponiveis[i] != x) {
					if (result.get(indexResult).size() == 1) {
						result.get(indexResult).add(Integer.valueOf(disponiveis[i]));
						if (q.contains(Integer.valueOf(disponiveis[i]))) {
							q.remove(Integer.valueOf(disponiveis[i]));
						}
					} else {
						boolean ehVizinho = false;
						for (int j=1; j<result.get(indexResult).size(); j++) {
							if (super.vizinhos(result.get(indexResult).get(j)).contains(Integer.valueOf(disponiveis[i]))) {
								ehVizinho = true;
							}
						}
						if (!ehVizinho) {
							result.get(indexResult).add(Integer.valueOf(disponiveis[i]));
							if (q.contains(Integer.valueOf(disponiveis[i]))) {
								q.remove(Integer.valueOf(disponiveis[i]));
							}
						}
					}
					
				}
			}
		}
		return result;
	}
	
	public int f(List<List<Integer>> S, List<Integer> I) {
		for (int i=1; i<S.size(); i++) {
			if (I.containsAll(S.get(i)) && I.size() == S.get(i).size()) {
				return i;
			}
		}
		return 0;
	}
	
	public void printLawler(int colorMin) {
		System.out.println("Coloração mínima: "+colorMin);
		List<List<Integer>> conjIndep = this.encontraConjIndep(new Grafo_ND_P(super.V, super.E, super.w));
		for (int i=0; i<conjIndep.size(); i++) {
			System.out.println(Arrays.toString(conjIndep.get(i).toArray())+" cor: "+(colorMin-i));
		}
		
	}
}
