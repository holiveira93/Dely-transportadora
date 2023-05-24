
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DelyTransporte {

    public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String[] modalidades = {"Caminhão de pequeno porte","Caminhão de médio porte", "Caminhão de grande porte"};
		double[] valores = {4.87, 11.92, 27.44};
		List <Transporte> listaTransportesCadastrados = new ArrayList<>();
		Map<String, Map<String,String>> mapDistanciasCidades = new LinkedHashMap<>();
		
		//---------RECEBIMENTO DO CSV------------//
        String path = "DNIT-Distancias.csv";
		mapDistanciasCidades.putAll(carregarCsv(path));
		//---------FIM DO RECEBIMENTO DO CSV------------//


		//---------MENU------------//
		boolean finalizar = false;
		do{
			int menu = 0;
			try {
				System.out.println("");
				System.out.println("TRANSPORTADORA DELY - TRANSPORTE INSTERESTADUAL DE CARGAS");
				System.out.println("");
				System.out.println("---------- MENU PRINCIPAL ----------");
				System.out.println("Escolha a opção a seguir:");
				System.out.println("1 - Consultar Trecho x Modalidade.");
				System.out.println("2 - Cadastrar transporte.");
				System.out.println("3 - Dados Estatisticos.");
				System.out.println("4 - Finalizar e sair.");
				System.out.printf(">> ");
				menu = scan.nextInt();
				System.out.println("");

			} catch (Exception e) {
				System.out.println("");
				System.out.println("Entrada inválida! Por favor digite apenas o numero da opção desejada.");
				System.out.println("");
				scan.nextLine();
				continue;
			}
			scan.nextLine();

			switch(menu){
				case 1: // chama método consultar trecho x modalidade
					System.out.println("---------- CONSULTAR TRECHO X MODALIDADE ----------");
					consultarTrechosModalidades(mapDistanciasCidades, modalidades, valores);
					System.out.println("");
				break;

				case 2: // chama metodo cadastrar transporte
					boolean sair = false;
					do{
						System.out.println("---------- CADASTRAR TRANSPORTE ----------");
						Transporte transporte = cadastrarTransporte(mapDistanciasCidades, valores);
						System.out.println("");

						// opção para confirmar transporte cadastrado
						int escolha = 0;
						boolean sairEscolha = false;
						do{
							try {
								System.out.println("Escolha a opção a seguir:");
								System.out.println("1 - Confirmar e sair.");
								System.out.println("2 - Refazer o cadastro do transporte.");
								System.out.println("3 - Cancelar e sair.");
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

							switch(escolha){
								case 1: // adiciona transporte cadastrado na lista de transportes cadastrados
									listaTransportesCadastrados.add(transporte);
									sairEscolha = true;
									sair = true;
								break;
	
								case 2: // cadastra um novo transporte
								break;
						
								case 3: // cancela o transporte cadastrado
									sairEscolha = true;
									sair = true;
								break;
						
								default:
									System.out.println("Opção Inválida, por favor verifique e tente novamente");
									System.out.println("");
								break;
							}
						}while(!sairEscolha);
					}while(!sair);
				break;

				case 3: // chama metodo dados statisticos
					System.out.println("---------- DADOS ESTATISTICOS ----------");

					if(!listaTransportesCadastrados.isEmpty()){
						int i = 1;
						for (Transporte transporte : listaTransportesCadastrados) {
							System.out.println("Transporte nº" + i++);
							transporte.dadosEstatisticos();
							System.out.println("");
						}
					}else{
						System.out.println("Não há nenhum transporte cadastrado");
						System.out.println("");
					}
				break;

				case 4:
					finalizar = true;
				break;

				default:
					System.out.println("Opção Inválida, por favor verifique e tente novamente");
					System.out.println("");
				break;
			}
		}while(!finalizar);
		scan.close();
    }

	public static Map<String,Map<String, String>> carregarCsv(String path) {
		Map<String, Map<String,String>> mapDistanciasCidades = new LinkedHashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			String[] cidades = line.split(";");

            line = br.readLine();
            int i = 0;
			while (line != null) {
                Map<String, String> distancias = new LinkedHashMap<>();
				String[] arraySplit = line.split(";");
                for (int j = 0; j < arraySplit.length;j++) {
                    distancias.put(cidades[j], arraySplit[j]);
                }
				
                mapDistanciasCidades.put(cidades[i], distancias);
                i++;

				line = br.readLine();
			}

		} 
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return mapDistanciasCidades;
	}

	public static void consultarTrechosModalidades(Map<String, Map<String,String>> mapDistanciasCidades,  String[] modalidades, double[] valores){
		Scanner scan = new Scanner(System.in);

		System.out.println("Cidades disponiveis:"); // mostra a lista de cidade disponiveis
		for (String chave : mapDistanciasCidades.keySet()) {
			System.out.println(chave);
		}
		System.out.println("");

		String origem = "";
		do{
			System.out.println("Digite a cidade de ORIGEM:"); // cadastra a cidade de origem
			System.out.printf(">> ");
			origem = scan.nextLine().toUpperCase();
			System.out.println("");
			if(mapDistanciasCidades.containsKey(origem)){ // verifica se a cidade esta disponivel
				break;
			}else{
				System.out.println("Cidade não encontrada, por favor verifique e tente novamente.");
				System.out.println("");
			}
		}while(true);		
		
		String destino = "";
		do{
			System.out.println("Digite a cidade de DESTINO: "); // cadastra a cidade de destino
			System.out.printf(">> ");
			destino = scan.nextLine().toUpperCase();
			System.out.println("");

			if(mapDistanciasCidades.containsKey(destino)){ // verifica se a cidade esta disponivel
				if(!origem.equals(destino)){ // verifica se a cidade de origem é igual ao destino
					break;
				}else{
					System.out.println("Cidade de origem e destino são as mesmas, por favor verifique e tente novamente.");
					System.out.println("");
				}
			}else{
				System.out.println("Cidade não encontrada, por favor verifique e tente novamente.");
				System.out.println("");
			}
		}while(true);

		int escolhaModalidade = 0;
		String modalidadeEscolhida = "";
		double custoModalidade = 0;
		boolean sair = false;
		do{
			try {
				System.out.println("Escolha a modalidade: ");
				int i = 1;
				for (String modalidade : modalidades) {
					System.out.println(i++ + " - " + modalidade);
				}
				System.out.printf(">> ");
				escolhaModalidade = scan.nextInt();
				System.out.println("");

			} catch (Exception e) {
				System.out.println("");
				System.out.println("Por favor digite apenas o número referente a modalidade desejada!");
				System.out.println("");
				scan.nextLine();
				continue;
			}

			switch(escolhaModalidade){
				case 1:
					modalidadeEscolhida = modalidades[0];
					custoModalidade = valores[0];
					sair = true;
				break;
				
				case 2:
					modalidadeEscolhida = modalidades[1];
					custoModalidade = valores[1];
					sair = true;
				break;
				
				case 3: 
					modalidadeEscolhida = modalidades[2];
					custoModalidade = valores[2];
					sair = true;
				break;
				
				default:
					System.out.println("Opção inválida!");
					System.out.println("");
				break;
			}
		}while(!sair);
		

		int distancia = Integer.parseInt(mapDistanciasCidades.get(origem).get(destino));
		double total = distancia * custoModalidade;
		System.out.println("Resultado:");
		System.out.printf("Cidade Origem: %s\n" +
		 				"Cidade Destino: %s\n" +
						"Distancia: %d km\n" +
						"Modalidade: %s\n" +
						"Custo Modalidade: R$%.2f por km\n" +
						"Custo Total: R$%.2f.\n", origem, destino, distancia, modalidadeEscolhida, custoModalidade, total);
		System.out.println("--------------------");
		System.out.println("");
	}

	public static Transporte cadastrarTransporte(Map<String, Map<String,String>> mapDistanciasCidades, double[] valores) {
		
		Transporte transporte = new Transporte();
		System.out.println("");
		System.out.println("---------- Cadastrar itens ----------");
		transporte.cadastrarItensTotais();
		transporte.calcularPesoTotal();
		transporte.calcularModalidades();

		System.out.println("---------- Cadastrar rota ----------");
		transporte.cadastrarRota(mapDistanciasCidades, valores);

		transporte.calcularDistanciaTotal();
		transporte.calcularCustoTotal();

		int i = 1;
		System.out.println("---------- Informações gerais: ----------");
		for (Trecho trecho : transporte.getListaTrechos()) {
			System.out.println("TRECHO nº " + i);
			trecho.info();
			System.out.println("");
			i++;
		}
		System.out.println("---------- Totais: ----------");
		transporte.info();

		return transporte;	
	}
	
}


