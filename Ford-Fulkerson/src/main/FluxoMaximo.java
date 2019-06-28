package main;

import java.util.LinkedList;

public class FluxoMaximo {
	
	// Numero de vértices.
	private Integer V;

	public FluxoMaximo(Integer v) {
		super();
		V = v;
	}

	// Retorna True se existe um caminhoAumentante de s para t no grafo residual.
	public boolean bfs(Integer GrafoF[][], Integer s, Integer t, Integer caminhoAumentante[]) {
		boolean visitado[] = new boolean[V];
		
		for (Integer i = 0; i < V; ++i) {
			visitado[i] = false;
		}

		LinkedList<Integer> fila = new LinkedList<Integer>();
		fila.add(s);
		visitado[s] = true;
		caminhoAumentante[s] = -1;

		while (fila.size() != 0) {
			Integer u = fila.poll();

			for (Integer v = 0; v < V; v++) {
				if (visitado[v] == false && GrafoF[u][v] > 0) {
					fila.add(v);
					caminhoAumentante[v] = u;
					visitado[v] = true;
				}
			}
		}
		return (visitado[t] == true);
	}

	Integer fordFulkerson(Integer grafo[][]) {
		
		// vértices internos.
		Integer u, v;
		
		// index da fonte e do sumidouro.
		Integer s = 0;
		Integer t = V - 1;
				
		// grafo residual.
		Integer GrafoF[][] = new Integer[V][V];

		for (u = 0; u < V; u++) {
			for (v = 0; v < V; v++) {
				GrafoF[u][v] = grafo[u][v];
			}
		}

		// caminho aumentante da fonte para o sumidouro no grafo residual.
		Integer caminhoAumentante[] = new Integer[V];

		Integer fluxo_maximo = 0;

		while (bfs(GrafoF, s, t, caminhoAumentante)) {
			
			// Compararemos o fluxo do caminho aumentante e escolheremos o mínimo entre
			// ele e capacidadeResidualDoCaminho
			Integer capacidadeResidualDoCaminho = Integer.MAX_VALUE;
			
			// Passando por todos os vértices do caminhoAumentante.
			// Encontra a capacidade residual mínima de cada aresta.
			for (v = t; v != s; v = caminhoAumentante[v]) {
				
				u = caminhoAumentante[v];

				capacidadeResidualDoCaminho = Math.min(capacidadeResidualDoCaminho, GrafoF[u][v]);
				
			}
			

			// Atualiza as capacidades residuais e inverte as arestas do caminhoAumentante.
			for (v = t; v != s; v = caminhoAumentante[v]) {
				u = caminhoAumentante[v];
				GrafoF[u][v] -= capacidadeResidualDoCaminho;
				GrafoF[v][u] += capacidadeResidualDoCaminho;
			}

			fluxo_maximo += capacidadeResidualDoCaminho;
		}

		return fluxo_maximo;
	}

	public Integer getV() {
		return V;
	}

	public void setV(Integer v) {
		V = v;
	}

	public static void main(String[] args) throws java.lang.Exception {

		Integer grafo[][] = new Integer[][] { {0, 5, 5, 0 },
											  {0, 0, 2, 3 },
											  {0, 2, 0, 10 },
											  {0, 0, 0, 0} };
											  
		FluxoMaximo m = new FluxoMaximo(4);

		System.out.println("O fluxo máximo é igual a " + m.fordFulkerson(grafo));

	}
}
