package grafo;

import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import grafo.*;

public class Client {

	public static void main(String[] args) {
		try {
			TTransport transport = new TSocket("localhost", 9090);
			transport.open();
 
			TProtocol protocol = new  TBinaryProtocol(transport);
			grafo.Client client = new grafo.Client(protocol);
			
			Scanner sc = null;
			String result = "";

			try {
				sc = new Scanner(System.in);
				//GrafoHandler handler = new GrafoHandler();
				//handler.cargaInicialParaTeste();
				int opcaoInicial = -1;

				//Variaveis auxiliares para entrada de dados
				int nomeVertice, corVertice, nomeVertice1, nomeVertice2;
				String descricao;
				double peso;
				boolean isBiDirecional;
				
				while (opcaoInicial != Acao.FINALIZAR) {
					try {

						// Operacao de vertice
						System.out.println("==================================");
						System.out.println("Escolha uma das opcoes: ");
						System.out.println("==================================\n");
						System.out.println("1 - Criar vertice");
						System.out.println("2 - Alterar cor do vertice");
						System.out.println("3 - Alterar descricao do vertice");
						System.out.println("4 - Alterar peso do vertice");
						System.out.println("5 - Consultar vertice");
						System.out.println("6 - Deletar vertice");

						// Operacoes de aresta
						System.out.println("7 - Criar aresta");
						System.out.println("8 - Alterar peso da aresta");
						System.out.println("9 - Alterar descricao da aresta");
						System.out.println("10 - Consultar aresta");
						System.out.println("11 - Deletar aresta");

						// Operacoes genericas
						System.out.println("12 - Listar vertices");
						System.out.println("13 - Listar arestas");
						System.out.println("14 - Listar vertices de uma aresta");
						System.out.println("15 - Listar arestas de um vertice");
						System.out.println("16 - Listar vertices vizinhos de um vertice");

						System.out.println("0 - Sair");

						opcaoInicial = sc.nextInt();

						switch (opcaoInicial) {
						case Acao.CRIAR_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							System.out.print("Informe a cor do vertice (codigo): ");
							corVertice = sc.nextInt();
							sc.nextLine();
							
							System.out.print("Informe a descricao do vertice: ");
							descricao = sc.nextLine();
							
							System.out.print("Informe o peso do vertice: ");
							peso = sc.nextDouble();
							sc.nextLine();
							
							result = client.criarVertice(nomeVertice, corVertice, descricao, peso);
							break;

						case Acao.ALTERAR_COR_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							System.out.print("Informe a nova cor do vertice (codigo): ");
							corVertice = sc.nextInt();
							
							result = client.alterarCorVertice(nomeVertice, corVertice);
							break;

						case Acao.ALTERAR_DESCRICAO_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							sc.nextLine();
							
							System.out.print("Informe a nova descricao do vertice: ");
							descricao = sc.nextLine();
							
							result = client.alterarDescricaoVertice(nomeVertice, descricao);
							break;

						case Acao.ALTERAR_PESO_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							System.out.print("Informe o novo peso do vertice: ");
							peso = sc.nextDouble();
							
							result = client.alterarPesoVertice(nomeVertice, peso);
							break;

						case Acao.CONSULTAR_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							result = client.consultarVertice(nomeVertice);
							break;
							
						case Acao.DELETAR_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							result = client.deletarVertice(nomeVertice);
							break;

						case Acao.CRIAR_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							
							System.out.print("Informe o peso da aresta: ");
							peso = sc.nextDouble();
							sc.nextLine();
							
							System.out.print("Informe se a aresta e bidirecional ('S' ou 'N'): ");
							isBiDirecional = "S".equals(sc.nextLine());
							
							System.out.print("Informe a descricao da aresta: ");
							descricao = sc.nextLine();
							
							result = client.criarAresta(nomeVertice1, nomeVertice2, peso, isBiDirecional, descricao);
							break;

						case Acao.ALTERAR_PESO_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							
							System.out.print("Informe o novo peso da aresta: ");
							peso = sc.nextDouble();
							
							result = client.alterarPesoAresta(nomeVertice1, nomeVertice2, peso);
							break;

						case Acao.ALTERAR_DESCRICAO_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							sc.nextLine();
							
							System.out.print("Informe a nova descricao da aresta: ");
							descricao = sc.nextLine();
							
							result = client.alterarDescricaoAresta(nomeVertice1, nomeVertice2, descricao);
							break;

						case Acao.CONSULTAR_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							
							result = client.consultarAresta(nomeVertice1, nomeVertice2);
							break;
							
						case Acao.DELETAR_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							
							result = client.deletarAresta(nomeVertice1, nomeVertice2);
							break;

						case Acao.LISTAR_VERTICES:
							result = client.listarVertices();
							break;

						case Acao.LISTAR_ARESTAS:
							result = client.listarArestas();
							break;

						case Acao.LISTAR_VERTICES_DE_UMA_ARESTA:
							System.out.print("Informe o nome do vertice 1 (codigo): ");
							nomeVertice1 = sc.nextInt();
							
							System.out.print("Informe o nome do vertice 2 (codigo): ");
							nomeVertice2 = sc.nextInt();
							
							result = client.listarVerticesDeUmaAresta(nomeVertice1, nomeVertice2);
							break;

						case Acao.LISTAR_ARESTAS_DE_UM_VERTICE:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							result = client.listarArestasDeUmVertice(nomeVertice);
							break;

						case Acao.LISTAR_VERTICES_VIZINHOS:
							System.out.print("Informe o nome do vertice (codigo): ");
							nomeVertice = sc.nextInt();
							
							result = client.listarVerticesVizinhos(nomeVertice);
							break;

						default:
							if (opcaoInicial != Acao.FINALIZAR) {
								System.out.println("Opcao invalida!");
							}
							break;
						}
						System.out.println(result);
						System.out.println("");
					} catch (Exception e) {
						System.out.println("Ocorreu um erro inesperado. Tente novamente!\n");
						sc.nextLine();
					}
				}
			} finally {
				if (sc != null) {
					sc.close();
				}
				System.out.println("Fim do programa!");
			}
			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
	}
}
