package grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grafo.*;

public class GrafoHandler implements grafo.Iface {
	Map<Integer, Vertice> mapVertices = new HashMap<Integer, Vertice>();
	Map<String, Aresta> mapArestas = new HashMap<String, Aresta>();
	
	Map<Integer, List<Integer>> mapArestasPorVertice = new HashMap<Integer, List<Integer>>();
	
	boolean isModoApresentacao = true; //Variavel utilizada para que as operacao demorem um certo tempo, para a apresentacao e o professor 
	
	public GrafoHandler() {
	}

	//VERTICES
	public synchronized String criarVertice(int nome, int cor, String descricao, double peso) {
		sleep();
		
		String result = "";
		if (!existeVertice(nome)) {
			Vertice vertice = new Vertice(nome, cor, descricao, peso);
			mapVertices.put(nome, vertice);

			result = "Vertice criado com sucesso.";
		} else {
			result = "O vertice ja existe!";
		}
		
		return result;
	}
	
	private boolean existeVertice(int nome) {
		return mapVertices.containsKey(nome);
	}

	
	public synchronized String alterarCorVertice(int nome, int cor) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			mapVertices.get(nome).setCor(cor);
			result = "Cor alterada com sucesso!";
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String alterarDescricaoVertice(int nome, String descricao) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			mapVertices.get(nome).setDescricao(descricao);
			result = "Descricao alterada com sucesso!";
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String alterarPesoVertice(int nome, double peso) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			mapVertices.get(nome).setPeso(peso);
			result = "Peso alterado com sucesso!";
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String consultarVertice(int nome) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			Vertice vertice = mapVertices.get(nome); 
			result = printarDadosVertice(vertice);
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String deletarVertice(int nome) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			//Antes de remover o vertice, removo as arestas daquele vertice
			deletarArestaPeloVertice(nome);

			mapVertices.remove(nome);
			result = "Vertice deletado com sucesso.";
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}

	//ARESTAS
	public synchronized String criarAresta(int vertice1, int vertice2, double peso, boolean isBiDirecional, String descricao) {
		sleep();
		
		String result = "";
		if (existeVertice(vertice1) && existeVertice(vertice2)) {
			if (!existeAresta(vertice1, vertice2)) {
				if (isBiDirecional) {
					String key1 = vertice1 + "-" + vertice2;
					Aresta aresta1 = new Aresta(vertice1, vertice2, peso, isBiDirecional, descricao);

					String key2 = vertice2 + "-" + vertice1;
					Aresta aresta2 = new Aresta(vertice2, vertice1, peso, isBiDirecional, descricao);
					
					mapArestas.put(key1, aresta1);
					mapArestas.put(key2, aresta2);
					
					adicionarArestaPeloVertice(vertice1, vertice2);
					adicionarArestaPeloVertice(vertice2, vertice1);
				} else {
					String key = vertice1 + "-" + vertice2;
					Aresta aresta = new Aresta(vertice1, vertice2, peso, isBiDirecional, descricao);

					mapArestas.put(key, aresta);
					
					adicionarArestaPeloVertice(vertice1, vertice2);
					adicionarArestaPeloVertice(vertice2, vertice1);
				}
				
				result = "Aresta criada com sucesso!";
			} else {
				result = "A aresta ja existe!";
			}
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}

	private boolean existeAresta(int vertice1, int vertice2) {
		String key1 = vertice1 + "-" + vertice2;
		String key2 = vertice2 + "-" + vertice1;
		return mapArestas.containsKey(key1) || mapArestas.containsKey(key2);
	}
	
	/**Utilizada quando eu quero fazer consulta de arestas, nesse caso verifico somente a aresta com chave vertice1 + vertice2
	 * e nao o contrario como vertice2 + vertice1
	 * */
	private boolean existeArestaUnica(int vertice1, int vertice2) {
		String key1 = vertice1 + "-" + vertice2;
		return mapArestas.containsKey(key1);
	}

	/**
	 * Cada vertice tera uma lista dos outros vertices com os quais existem arestas, assim quando for adicionado uma aresta, tenho
	 * que adicionar esses vertices na mapArestasPorVertice
	 * */
	private void adicionarArestaPeloVertice(int verticeOrigem, int verticeDestino) {
		List<Integer> listVerticesDestino = null;
		
		if (mapArestasPorVertice.containsKey(verticeOrigem)) {
			listVerticesDestino = mapArestasPorVertice.get(verticeOrigem);
		} else {
			listVerticesDestino = new ArrayList<Integer>();
		}
		
		listVerticesDestino.add(verticeDestino);
		mapArestasPorVertice.put(verticeOrigem, listVerticesDestino);
	}
	
	public synchronized String alterarPesoAresta(int vertice1, int vertice2, double peso) {
		sleep();
		
		String result = "";
		if (existeAresta(vertice1, vertice2)) {
			String key1 = vertice1 + "-" + vertice2;
			String key2 = vertice2 + "-" + vertice1;
			
			if (mapArestas.containsKey(key1)) {
				mapArestas.get(key1).setPeso(peso);
			}
			
			if (mapArestas.containsKey(key2)) {
				mapArestas.get(key2).setPeso(peso);
			}
			
			result = "Peso alterado com sucesso!";
		} else {
			result = "A aresta nao existe!";
		}
		
		return result;
	}
	
	public synchronized String alterarDescricaoAresta(int vertice1, int vertice2, String descricao) {
		sleep();
		
		String result = "";
		if (existeAresta(vertice1, vertice2)) {
			String key1 = vertice1 + "-" + vertice2;
			String key2 = vertice2 + "-" + vertice1;
			
			if (mapArestas.containsKey(key1)) {
				mapArestas.get(key1).setDescricao(descricao);
			}
			
			if (mapArestas.containsKey(key2)) {
				mapArestas.get(key2).setDescricao(descricao);
			}
			
			result = "Descricao alterada com sucesso!";
		} else {
			result = "A aresta nao existe!";
		}
		
		return result;
	}
	
	public synchronized String consultarAresta(int vertice1, int vertice2) {
		sleep();
		
		String result = "";
		if (existeArestaUnica(vertice1, vertice2)) {
			String key1 = vertice1 + "-" + vertice2;
			Aresta aresta = mapArestas.get(key1);
			result = printarDadosAresta(aresta);
		} else {
			result = "A aresta nao existe!";
		}
		
		return result;
	}
	
	/**
	 * Metodo responsavel por deletar as arestar a partir de um vertice
	 * Exemplo: se eu tenho o vertice 1 e ele tem ligacao com o vertice 2, entao devo deletar a aresta 1-2
	 * 
	 * Alem diso devo deletar tambem os vertices destino ao qual esse vertice esta ligado.
	 * */
	private void deletarArestaPeloVertice(int vertice) {
		if (mapArestasPorVertice.containsKey(vertice)) {
			List<Integer> listVerticesDestino = mapArestasPorVertice.get(vertice);
			
			for (Integer verticeDestino : listVerticesDestino) {
				String key1 = vertice + "-" + verticeDestino;
				String key2 = verticeDestino + "-" + vertice;
				
				int cont = 0;
				
				//Remove as arestas do vertice (se for bidirecional ou nao)
				if (mapArestas.containsKey(key1)) {
					mapArestas.remove(key1);
					cont++;
				} 
				
				if (mapArestas.containsKey(key2)) {
					mapArestas.remove(key2);
					cont++;
				}
				
				//Remove a ligacao dos vertices na map destino na que possuem arestas bidirecionais
				//EX: suponda que existe uma aresta 1-2, 1-3 e 2-1. Nessa caso quando eu executar o comando 
				//mapArestasPorVertice.remove(vertice) a ligacao 1-2 e 1-3 ja sera deletada, contudo eu preciso pegar a lista de
				//vertices ligados ao vertice 2, para remover a ligacao 2-1
				if (cont == 2) {
					mapArestasPorVertice.get(verticeDestino).remove((Integer) vertice);
					
					if (mapArestasPorVertice.get(verticeDestino).isEmpty()) {
						mapArestasPorVertice.remove(verticeDestino);
					}
				}
			}
			
			mapArestasPorVertice.remove(vertice);
		}
		
	}
	
	public synchronized String deletarAresta(int vertice1, int vertice2) {
		sleep();
		
		String result = "";
		if (existeArestaUnica(vertice1, vertice2)) {
			String key1 = vertice1 + "-" + vertice2;
			String key2 = vertice2 + "-" + vertice1;
			
			if (mapArestas.containsKey(key1)) {
				mapArestas.remove(key1);
			}
			
			if (mapArestas.containsKey(key2)) {
				mapArestas.remove(key2);
			}
			
			//Deletando da map que contem as ligacoes entre os vertices aqueles ligados as arestas 
			//que estao sendo deletadas
			if (mapArestasPorVertice.containsKey(vertice1)) {
				mapArestasPorVertice.get(vertice1).remove((Integer) vertice2);
				
				if (mapArestasPorVertice.get(vertice1).isEmpty()) {
					mapArestasPorVertice.remove(vertice1);
				}
			}
			
			if (mapArestasPorVertice.containsKey(vertice2)) {
				mapArestasPorVertice.get(vertice2).remove((Integer) vertice1);
				
				if (mapArestasPorVertice.get(vertice2).isEmpty()) {
					mapArestasPorVertice.remove(vertice2);
				}
			}
			
			result = "Aresta deletada com sucesso!";
		} else {
			result = "A aresta nao existe!";
		}
		
		return result;
	}
	
	//GERAIS
	public synchronized String listarVertices() {
		sleep();
		
		String result = "";
		if (mapVertices.isEmpty()) {
			result = "Nao existem vertices cadastrados!";
		} else {
			result += "==================================\n";
			result += "Vertices cadastrados\n";
			result += "==================================\n";

			for (Map.Entry<Integer, Vertice> entry : mapVertices.entrySet()) {
				Vertice vertice = entry.getValue();
				result += printarDadosVertice(vertice);
			}
		}
		
		return result;
	}

	public synchronized String listarArestas() {
		sleep();
		
		String result = "";
		if (mapArestas.isEmpty()) {
			result = "Nao existem arestas cadastradas!";
		} else {
			result += "==================================\n";
			result += "Arestas cadastrados\n";
			result += "==================================\n";

			for (Map.Entry<String, Aresta> entry : mapArestas.entrySet()) {
				Aresta aresta = entry.getValue();
				result += printarDadosAresta(aresta);
			}
		}
		
		return result;
	}

	public synchronized String listarVerticesDeUmaAresta(int nomeVertice1, int nomeVertice2) {
		sleep();
		
		String result = "";
		if (existeVertice(nomeVertice1) && existeVertice(nomeVertice2)) {
			if (existeArestaUnica(nomeVertice1, nomeVertice2)) {
				Vertice vertice1 = mapVertices.get(nomeVertice1);
				result += printarDadosVertice(vertice1);
				
				Vertice vertice2 = mapVertices.get(nomeVertice2);
				result += printarDadosVertice(vertice2);
				
			} else {
				result = "A aresta nao existe!";
			}
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String listarArestasDeUmVertice(int nomeVertice1) {
		sleep();
		
		String result = "";
		if (existeVertice(nomeVertice1)) {
			if (mapArestasPorVertice.containsKey(nomeVertice1)) {
				List<Integer> listVerticesDestino = mapArestasPorVertice.get(nomeVertice1);
				
				for (Integer verticeDestino : listVerticesDestino) {
					String key1 = nomeVertice1 + "-" + verticeDestino;
					String key2 = verticeDestino + "-" + nomeVertice1;
					
					if (mapArestas.containsKey(key1)) {
						result += printarDadosAresta(mapArestas.get(key1));
					} 
					
					if (mapArestas.containsKey(key2)) {
						result += printarDadosAresta(mapArestas.get(key2));
					}
				}
			} else {
				result = "O vertice nao possui nenhuma aresta!";
			}
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}
	
	public synchronized String listarVerticesVizinhos(int nome) {
		sleep();
		
		String result = "";
		if (existeVertice(nome)) {
			if (mapArestasPorVertice.containsKey(nome)) {
				List<Integer> listVerticesDestino = mapArestasPorVertice.get(nome);
				
				for (Integer verticeDestino : listVerticesDestino) {
					result += printarDadosVertice(mapVertices.get(verticeDestino));
				}
			} else {
				result = "O vertice nao possui vertices vizinhos!";
			}
		} else {
			result = "O vertice nao existe!";
		}
		
		return result;
	}

	private String printarDadosVertice(Vertice vertice) {
		StringBuilder builder = new StringBuilder();
		builder.append("Nome do vertice: " + vertice.getNome() + "\n");
		builder.append("Cor do vertice: " + vertice.getCor() + "\n");
		builder.append("Descricao do vertice: " + vertice.getDescricao() + "\n");
		builder.append("Peso do vertice: " + vertice.getPeso() + "\n");
		
		return builder.toString() + "\n";
	}
	
	private String printarDadosAresta(Aresta aresta) {
		StringBuilder builder = new StringBuilder();
		builder.append("Nome do vertice 1: " + aresta.getVertice1() + "\n");
		builder.append("Nome do vertice 2: " + aresta.getVertice2() + "\n");
		builder.append("Peso da aresta: " + aresta.getPeso() + "\n");
		builder.append("E aresta bidirecional: " + (aresta.isBiDirecional() ? "S" : "N") + "\n");
		builder.append("Descricao da aresta: " + aresta.getDescricao() + "\n");
		
		return builder.toString() + "\n";
	}
	
	private void sleep() {
		try {
			if (isModoApresentacao) {
				Thread.sleep(10000);
			}
		} catch (InterruptedException ie) {
			sleep();
		}
		
	}
}
