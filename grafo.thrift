namespace java grafo
namespace py grafo

service grafo {
	string criarVertice(1:i32 nome, 2:i32 cor, 3:string descricao, 4:double peso),
	string alterarCorVertice(1:i32 nome, 2:i32 cor),
	string alterarDescricaoVertice(1:i32 nome, 2:string descricao),
	string alterarPesoVertice(1:i32 nome, 2:double peso),
	string consultarVertice(1:i32 nome),
	string deletarVertice(1:i32 nome),
	string criarAresta(1:i32 vertice1, 2:i32 vertice2, 3:double peso, 4:bool isBiDirecional, 5:string descricao),
	string alterarPesoAresta(1:i32 vertice1, 2:i32 vertice2, 3:double peso),
	string alterarDescricaoAresta(1:i32 vertice1, 2:i32 vertice2, 3:string descricao),
	string consultarAresta(1:i32 vertice1, 2:i32 vertice2),
	string deletarAresta(1:i32 vertice1, 2:i32 vertice2),
	string listarVertices(),
	string listarArestas(),
	string listarVerticesDeUmaAresta(1:i32 nomeVertice1, 2:i32 nomeVertice2),
	string listarArestasDeUmVertice(1:i32 nomeVertice1),
	string listarVerticesVizinhos(1:i32 nome)
}
