
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Transporte {
	Scanner scan = new Scanner(System.in);
	private String cidadeOrigem;
	private String cidadeDestino;
	private List<Item> listaItensTotal = new ArrayList<>();
	private double pesoTotal;
	private List<Trecho> listaTrechos = new ArrayList<>();
	private int caminhaoPequeno, caminhaoMedio, caminhaoGrande;
	private int distanciaTotal;
	private List<Item> itensDescarregados = new ArrayList<>();
	private double custoTotalP;
    private double custoTotalM;
    private double custoTotalG;
	private double custoTotalGeral;


	public String getCidadeOrigem() {
		return cidadeOrigem;
	}

	public void setCidadeOrigem(String cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;
	}

	public String getCidadeDestino() {
		return cidadeDestino;
	}

	public void setCidadeDestino(String cidadeDestino) {
		this.cidadeDestino = cidadeDestino;
	}

	public List<Item> getListaItensTotal() {
		return listaItensTotal;
	}

	public void setListaItensTotal(List<Item> listaItensTotal) {
		this.listaItensTotal = listaItensTotal;
	}

	public double getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(double pesoTotal) {
		this.pesoTotal = pesoTotal;
	}

	public List<Trecho> getListaTrechos() {
		return listaTrechos;
	}

	public void setListaTrechos(List<Trecho> listaTrechos) {
		this.listaTrechos = listaTrechos;
	}

	public int getCaminhaoPequeno() {
		return caminhaoPequeno;
	}

	public void setCaminhaoPequeno(int caminhaoPequeno) {
		this.caminhaoPequeno = caminhaoPequeno;
	}

	public int getCaminhaoMedio() {
		return caminhaoMedio;
	}

	public void setCaminhaoMedio(int caminhaoMedio) {
		this.caminhaoMedio = caminhaoMedio;
	}

	public int getCaminhaoGrande() {
		return caminhaoGrande;
	}

	public void setCaminhaoGrande(int caminhaoGrande) {
		this.caminhaoGrande = caminhaoGrande;
	}

	public int getDistanciaTotal() {
		return distanciaTotal;
	}

	public void setDistanciaTotal(int distanciaTotal) {
		this.distanciaTotal = distanciaTotal;
	}

	public List<Item> getItensDescarregados() {
		return itensDescarregados;
	}

	public void setItensDescarregados(List<Item> itensDescarregados) {
		this.itensDescarregados = itensDescarregados;
	}

	public double getCustoTotalP() {
		return custoTotalP;
	}

	public void setCustoTotalP(double custoTotalP) {
		this.custoTotalP = custoTotalP;
	}

	public double getCustoTotalM() {
		return custoTotalM;
	}

	public void setCustoTotalM(double custoTotalM) {
		this.custoTotalM = custoTotalM;
	}

	public double getCustoTotalG() {
		return custoTotalG;
	}

	public void setCustoTotalG(double custoTotalG) {
		this.custoTotalG = custoTotalG;
	}

	public double getCustoTotalGeral() {
		return custoTotalGeral;
	}

	public void setCustoTotalGeral(double custoTotalGeral) {
		this.custoTotalGeral = custoTotalGeral;
	}

	public void cadastrarItensTotais() {
		boolean sairFinal = false;
		
		do{
			System.out.println("Digite o NOME do ITEM:");
			System.out.printf(">> ");
			String nome = scan.nextLine().toUpperCase();
			System.out.println("");

			double peso = 0;
			do{
				try {
					System.out.println("Digite o PESO (kg) unitário do item " + nome + ":");
					System.out.printf(">> ");
            		peso = scan.nextDouble();
					System.out.println("");

					if(peso <= 0) throw new Exception();

				} catch (Exception e){
					System.out.println("ERRO, por favor informe um PESO válido");
					System.out.println("");
					scan.nextLine();
					continue;
				}
				scan.nextLine();
				break;
			}while(true);

			int quantidade = 0;
			do{
				try {
					System.out.println("Digite a QUANTIDADE do item " + nome + ":");
					System.out.printf(">> ");
            		quantidade = scan.nextInt();
					System.out.println("");

					if(quantidade <= 0) throw new Exception();

				} catch (Exception e){
					System.out.println("ERRO, por favor informe uma QUANTIDADE válida");
					System.out.println("");
					scan.nextLine();
					continue;
				}
				scan.nextLine();
				break;
			}while(true);

			boolean contem = false;
			if(!listaItensTotal.isEmpty()){ // verifica se a lista contem o item cadastrado e soma a quantidade
				for (Item item : getListaItensTotal()) {
					if(item.getNome().equals(nome)){
						contem = true;
						int quantidadeAntiga = item.getQuantidade();
						int quantidadeNova = quantidadeAntiga + quantidade;
						item.setQuantidade(quantidadeNova);
					break;
					}
				}
			}
			
			if(!contem){ // se item novo, cadastra o item
				Item novoItem = new Item(nome, peso, quantidade);
				getListaItensTotal().add(novoItem);
			}
			boolean sairEscolha = false;
			int escolha;
			do{
				try {
					System.out.println("Escolha a opção a seguir:");
					System.out.println("1 - Adicionar novo item.");
					System.out.println("2 - Continuar.");
					System.out.printf(">> ");
					escolha = scan.nextInt();
					System.out.println("");

				} catch (Exception e) {
					
					System.out.println("Entrada inválida! Por favor digite apenas o número da opção desejada.");
					System.out.println("");
					scan.nextLine();
					continue;
				}
				scan.nextLine();
				
				switch(escolha){
					case 1:
						sairEscolha = true;
					break;
	
					case 2:
						sairEscolha = true;
						sairFinal = true;
					break;
	
					default:
						System.out.println("Opção Inválida, por favor verifique e tente novamente");
						System.out.println("");
					break;
				}
			}while(!sairEscolha);
		}while(!sairFinal);	
	}

    public void cadastrarRota(Map<String, Map<String,String>> mapDistanciasCidades, double[] valores) {
	
		double pesoDescarregadoAcumulado = 0;
		
        System.out.println("Cidades disponiveis:");
		for (String key : mapDistanciasCidades.keySet()){
            System.out.println(key);
        }
		System.out.println("");

		String origem = "";
		do{
			System.out.println("Digite a cidade de ORIGEM:");
			System.out.printf(">> ");
			origem = scan.nextLine().toUpperCase();
			System.out.println("");

			if(mapDistanciasCidades.containsKey(origem)){
				break;
			}else{
				System.out.println("Cidade " + origem + " não encontrada, por favor verifique e tente novamente.");
				System.out.println("");
			}

		}while(true);
		setCidadeOrigem(origem);
		
		do{
		Trecho trecho = new Trecho();
		trecho = cadastrarTrecho(mapDistanciasCidades, origem); //cadastra novo trecho
		
			if(trecho.getCategoriaDestino().equals("Parada Intermediaria")){ // se for parada intermediaria, cadastra os itens a descarregar
				
				trecho.cadastrarItensEntrega(listaItensTotal, trecho.getCidadeDestino());
				trecho.calcularModalidades(caminhaoPequeno, caminhaoMedio, caminhaoGrande, pesoTotal, pesoDescarregadoAcumulado);
				trecho.calcularCusto(valores);
				listaTrechos.add(trecho);
				
				origem = trecho.getCidadeDestino();
				pesoDescarregadoAcumulado += trecho.getPesoDescarregar();

				if(!itensDescarregados.isEmpty()){
					for (Item item : getItensDescarregados()) {
						for (Item i : trecho.getItensDescarregar()) {
							if(i.getNome().equals(item.getNome())){
								item.setQuantidade(item.getQuantidade()- i.getQuantidade()); 
							}
						}
					}
				}else{
					itensDescarregados.addAll(trecho.getItensDescarregar());
				}
				
			}

			if(trecho.getCategoriaDestino().equals("Destino Final")){ // se for destino final, recebe o restante dos itens.
				List<Item> itensRestantes = new ArrayList<>();

				for (Item item : getListaItensTotal()) {
					Item novoItem = new Item(item.getNome(), item.getPesoUnidade(), item.getQuantidade());
					
					for (Item item2 : getItensDescarregados()) {
						if(item2.getNome().equals(novoItem.getNome())){
							novoItem.setQuantidade(novoItem.getQuantidade()-item2.getQuantidade());
						}
					}
					novoItem.calcularPesoQuantidade();
					itensRestantes.add(novoItem);
				}
				
				trecho.setItensDescarregar(itensRestantes);
				trecho.calcularPesoDescarregar();
				trecho.calcularModalidades(caminhaoPequeno, caminhaoMedio, caminhaoGrande, pesoTotal, pesoDescarregadoAcumulado);
				trecho.calcularCusto(valores);
				listaTrechos.add(trecho);
				setCidadeDestino(trecho.getCidadeDestino());
				break;
			}
		}while(true);
    }

	public Trecho cadastrarTrecho(Map<String, Map<String,String>> mapDistanciasCidades, String origem){

		String destino = "";

		Trecho trecho = new Trecho();
		trecho.setCidadeOrigem(origem);

		do {
			System.out.println("Digite a cidade de DESTINO: ");
			System.out.printf(">> ");
			destino = scan.nextLine().toUpperCase();
			System.out.println("");

			if(mapDistanciasCidades.containsKey(destino)){
				if(origem.equals(destino)){
					System.out.println("Cidade de origem e destino não podem ser iguais, por favor verifique e tente novamente.");
					System.out.println("");
				}else{
					break;
				}
			}else{
				System.out.println("Cidade " + destino + " não encontrada, por favor verifique e tente novamente.");
				System.out.println("");
			}
		} while (true);

		trecho.setCidadeDestino(destino);
		int distancia = Integer.parseInt(mapDistanciasCidades.get(origem).get(destino));
		trecho.setDistancia(distancia);
		
		int escolha = 0;
		String categoriaDestino = "";
		boolean sair = false;
		do{
			try {
				System.out.println("Escolha a categoria do destino e digite o número da opção: ");
				System.out.println("1 - Parada intermediária.");
				System.out.println("2 - Final.");
				System.out.printf(">> ");
				escolha = scan.nextInt();
				System.out.println("");

			} catch (Exception e) {
				System.out.println("Entrada inválida! Por favor digite apenas o numero da opção desejada.");
				System.out.println("");
				scan.nextLine();
				continue;
			}
			scan.nextLine();

			switch (escolha) {
				case 1:
					categoriaDestino = "Parada Intermediaria";
					trecho.setCategoriaDestino(categoriaDestino);
					sair = true;
				break;

				case 2:
					categoriaDestino = "Destino Final";
					trecho.setCategoriaDestino(categoriaDestino);
					sair = true;
				break;

				default:
					System.out.println("Entrada inválida! Por favor digite apenas o numero da opção desejada.");
					System.out.println();
				break;
			}
		}while(!sair);

		return trecho;
	}

	public void calcularPesoTotal(){
		double pesoCarga = 0d;
		for (Item item : listaItensTotal) {
			item.calcularPesoQuantidade();
			pesoCarga += item.getPesoTotal();
			}
			setPesoTotal(pesoCarga);
	}

	public void calcularModalidades(){
		double pesoRestante = getPesoTotal();
		int caminhaoPequeno = 0, caminhaoMedio = 0, caminhaoGrande = 0;

		do{
			if(pesoRestante <= 1000){
				caminhaoPequeno += 1;
				pesoRestante -= 1000;

			}else if(pesoRestante <= 2000){
				caminhaoPequeno += 2;
				pesoRestante -= 2000;

			}else if(pesoRestante <= 4000){
				caminhaoMedio += 1;
				pesoRestante -= 4000;

			}else if(pesoRestante <= 5000){
				caminhaoMedio += 1;
				caminhaoPequeno += 1;
				pesoRestante -= 5000;

			}else if(pesoRestante <= 6000){
				caminhaoMedio += 1;
				caminhaoPequeno += 2;
				pesoRestante -= 6000;

			}else if(pesoRestante <= 8000){
				caminhaoMedio += 2;
				pesoRestante -= 8000;

			}else if(pesoRestante <= 9000){
				caminhaoMedio += 1;
				caminhaoPequeno += 1;
				pesoRestante -= 9000;

			}else{
				caminhaoGrande += 1;
				pesoRestante -= 10000;
					 
			}
		}while(pesoRestante>=0);
			
		setCaminhaoPequeno(caminhaoPequeno);
		setCaminhaoMedio(caminhaoMedio);
		setCaminhaoGrande(caminhaoGrande);
	}

	public void calcularDistanciaTotal() {
		int soma = 0;
		for (Trecho trecho : listaTrechos) {
			soma += trecho.getDistancia();
		}
		setDistanciaTotal(soma);
	}

	public void calcularCustoTotal() {

		double custoTotalGeral = 0, custoTotalP = 0, custoTotalM = 0, custoTotalG = 0;
		for (Trecho trecho : listaTrechos) {

			custoTotalP += trecho.getCustoP();
			custoTotalM += trecho.getCustoM();
			custoTotalG += trecho.getCustoG();
			custoTotalGeral += trecho.getCustoTrecho();
		}
		setCustoTotalP(custoTotalP);
		setCustoTotalM(custoTotalM);
		setCustoTotalG(custoTotalG);
		setCustoTotalGeral(custoTotalGeral);
	}

	public void info() {
        String itens = "";
        for (Item item : listaItensTotal) {
            itens += item.toString() + "\n";
        }

        System.out.printf("Cidade Origem: %s\n" +
                        "Cidade Destino: %s \n" +
                        "Distancia Total: %d km\n\n" +
						"Itens totais:\n" +
                        "%s" +
						"Peso Total: %.2f\n\n" +
						"Modalidade necessária:\n" +
						"%d caminhão pequeno, %d caminhão medio, %d caminhão grande \n\n" +
                        "Custo Total: R$%.2f\n", cidadeOrigem, cidadeDestino, distanciaTotal, itens,
						 pesoTotal, caminhaoPequeno, caminhaoMedio, caminhaoGrande, custoTotalGeral);
	}

	public void dadosEstatisticos() {

		System.out.printf("Custo Total: %.2f. \n", custoTotalGeral);
		System.out.printf("Custo por Trecho:\n");
		int i = 1;
		for (Trecho trecho : getListaTrechos()) {
			System.out.printf("%d - Origem: %s, Destino: %s, Custo: %.2f\n", i++, trecho.getCidadeOrigem(), trecho.getCidadeDestino(), trecho.getCustoTrecho());
		}
		System.out.printf("Custo médio por Km: %.2f \n", getCustoTotalGeral()/getDistanciaTotal());
		int totalItem =0;
		for (Item item : listaItensTotal) {
			totalItem += item.getQuantidade();
			System.out.printf("Custo médio por transporte do produto %s: - %.2f\n", item.getNome(), custoTotalG / item.getQuantidade());
		}
		System.out.printf("Modalidade Caminhão pequeno:\nVeiculos enviados - %d, Custo total - %.2f\n", getCaminhaoPequeno(), getCustoTotalP());
		System.out.printf("Modalidade Caminhão medio:\nVeiculos enviados - %d, Custo total - %.2f\n", getCaminhaoMedio(), getCustoTotalM());
		System.out.printf("Modalidade Caminhão grande:\nVeiculos enviados - %d, Custo total - %.2f\n", getCaminhaoGrande(), getCustoTotalG());
		System.out.printf("Total de veiculos enviados: \n",getCaminhaoGrande() + getCaminhaoMedio() + getCaminhaoPequeno());
		System.out.printf("Numero total de itens transportados: %d \n", totalItem);
	}
}