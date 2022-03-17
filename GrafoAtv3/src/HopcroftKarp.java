import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class HopcroftKarp extends Grafo_ND_NP {
	protected int[] X;
	protected int[] Y;
	protected int[] D;
	protected int[] mate = new int[super.V.size()];
	protected int m;
	
	
	
	public HopcroftKarp(Grafo_ND_NP grafo) {
		super(grafo.V, grafo.E);
		encontraPartes();
		executarHopcroftKarp();
	}
	
	public HopcroftKarp(File fileGrafo) throws IOException {
		super(fileGrafo);
		encontraPartes();
		executarHopcroftKarp();
	}
	
	public void encontraPartes() {
		List<Integer> elements = new ArrayList<Integer>();
		for (int i=0; i < super.E.size(); i++) {
			if (i > 0) {
				if (super.E.get(i).get(0).intValue() != super.E.get(i-1).get(0).intValue()) {
					elements.add(super.E.get(i).get(0));
				} else {
					
				}
			} else {
				elements.add(super.E.get(i).get(0));
			}
		}
		this.X = new int[elements.size()];
		int restante = super.V.size() - elements.size();
		if (restante < 0) { restante = restante * (-1); }
		this.Y = new int[restante];
		int indexX = 0;
		int indexY = 0;
		for (int i=1; i<=super.V.size(); i++) {
			if (elements.contains(i)) {
				this.X[indexX] = i;
				indexX += 1;
			} else {
				this.Y[indexY] = i;
				indexY += 1;
			}
		}
		
	}
	
	public void executarHopcroftKarp() {
		this.D = new int[super.V.size()+1]; // +1 para o grafo nulo
		for (int i = 0; i < this.D.length; i++) {
			this.D[i] = Integer.MAX_VALUE; // valor máximo de int como infinito 
		}
		for (int i = 0; i < this.mate.length; i++) {
			this.mate[i] = this.D.length; // todos casados com o vértice fictício null, que seria o último espaço do vetor D 
		}
		this.m = 0;
		boolean BFSReturn = BFS();
		while (BFSReturn) {
			for (int i = 0; i < this.X.length; i++) {
				if (this.mate[this.X[i]-1]-1 == D.length-1) {  // D.length-1 é nosso vetor nulo fictício
					boolean DFSReturn = DFS(this.X[i]);
					if (DFSReturn) {
						this.m += 1;
					}
				}
			}
			BFSReturn = BFS();
		}
		printHopcroftKarp();
	}
	
	public boolean BFS() {
		Queue<Integer> Q = new PriorityQueue<Integer>();
		for (int i=0; i<this.X.length; i++) {
			if (this.mate[this.X[i]-1]-1 == this.D.length-1) {
				this.D[this.X[i]-1] = 0;
				Q.add(this.X[i]);
			} else {
				this.D[this.X[i]-1] = Integer.MAX_VALUE;
			}
		}
		this.D[this.D.length-1] = Integer.MAX_VALUE;
		while (!Q.isEmpty()) {
			int x = Q.poll();
			if (this.D[x-1] < this.D[this.D.length-1]) {
				List<Integer> y = super.vizinhos(x);
				for (int i=0; i < y.size(); i++) {
					if (this.D[this.mate[y.get(i)-1]-1] == Integer.MAX_VALUE) {
						this.D[this.mate[y.get(i)-1]-1] = this.D[x-1] + 1;
						Q.add(this.mate[y.get(i)-1]);
					}
				}
			} 
		}
		return this.D[this.D.length-1] != Integer.MAX_VALUE;
	}
	
	public boolean DFS(int x) {
		if (x-1 != this.D.length-1) {
			List<Integer> y = super.vizinhos(x);
			for (int i=0; i < y.size(); i++) {
				if (this.D[this.mate[y.get(i)-1]-1] == this.D[x-1] + 1) {
					boolean DFSReturn = DFS(this.mate[y.get(i)-1]);
					if (DFSReturn) {
						this.mate[y.get(i)-1] = x;
						this.mate[x-1] = y.get(i);
						return true;
					}
				}
			}
			this.D[x-1] = Integer.MAX_VALUE;
			return false;
		}
		return true;
	}
	
	public void printHopcroftKarp() {
		System.out.println("Emparelhamento Máximo: "+this.m);
		System.out.print("{ ");
		for (int i=0; i<this.X.length; i++) {
			System.out.print("{"+this.X[i]+", "+this.mate[this.X[i]-1]+"}");
			if (i != this.X.length-1) {
				System.out.print(", ");
			} else {
				System.out.print(" }");
			}
		}
	}
}
