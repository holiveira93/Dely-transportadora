

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trecho {
    private Scanner scan = new Scanner(System.in);
    private String cidadeOrigem;
    private String cidadeDestino;
    private int distancia;
    private String categoriaDestino;
    private List<Item> itensDescarregar = new ArrayList<>();
    private double pesoDescarregar;
    private int caminhaoPequeno, caminhaoMedio, caminhaoGrande;
    private double custoTrecho;
    private double custoP;
    private double custoM;
    private double custoG;

    

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

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getCategoriaDestino() {
        return categoriaDestino;
    }

    public void setCategoriaDestino(String categoriaDestino) {
        this.categoriaDestino = categoriaDestino;
    }

    public List<Item> getItensDescarregar() {
        return itensDescarregar;
    }

    public void setItensDescarregar(List<Item> itensDescarregar) {
        this.itensDescarregar = itensDescarregar;
    }

    public double getPesoDescarregar() {
        return pesoDescarregar;
    }

    public void setPesoDescarregar(double pesoDescarregar) {
        this.pesoDescarregar = pesoDescarregar;
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

    public double getCustoTrecho() {
        return custoTrecho;
    }

    public void setCustoTrecho(double custoTrecho) {
        this.custoTrecho = custoTrecho;
    }

    public double getCustoP() {
        return custoP;
    }

    public void setCustoP(double custoP) {
        this.custoP = custoP;
    }

    public double getCustoM() {
        return custoM;
    }

    public void setCustoM(double custoM) {
        this.custoM = custoM;
    }

    public double getCustoG() {
        return custoG;
    }

    public void setCustoG(double custoG) {
        this.custoG = custoG;
    }

    public void cadastrarItensEntrega(List<Item> listaItensTotal, String cidadeDestino) {

        boolean sairItens = false;
		System.out.println("---------- Cadastrar item para entrega em " + cidadeDestino + " ----------");
		do{
			System.out.println("Digite o NOME do ITEM:");
			System.out.printf(">> ");
			String nome = scan.nextLine().toUpperCase();
			System.out.println("");

			boolean contem = false;
			double peso = 0;
			for (Item item : listaItensTotal) {
				if(item.getNome().equals(nome)){
					contem = true;
					peso = item.getPesoUnidade();
					break;
				}
			}

			if(!contem){
	    		System.out.println("Item " + nome + " não esta cadastrado para entregas, verifique e tente novamente.");
				System.out.println("");
				continue;
			}
						
			int quantidade = 0;
			do{
				try {
					System.out.println("Digite a QUANTIDADE do item " + nome + ":");
					System.out.printf(">> ");
					quantidade = scan.nextInt();
					System.out.println("");
								
					for (Item item : listaItensTotal) {
						if(item.getNome().equals(nome)){
							if(quantidade > item.getQuantidade()) throw new Exception();
                            if(quantidade <= 0) throw new Exception();
						}
					}
			
				} catch (Exception e){
					System.out.println("ERRO, QUANTIDADE inválida ou maior que a restante.");
					System.out.println("");
                    scan.nextLine();
					continue;
				}
                scan.nextLine();
				break;
			}while(true);

			if(!itensDescarregar.isEmpty()){
			    for (Item item : itensDescarregar) {
		    	    if(item.getNome().equals(nome)){
				    quantidade += item.getQuantidade();
			        }
				}
			}

			itensDescarregar.add(new Item(nome, peso, quantidade));
			for (Item item : itensDescarregar) {
	    		item.calcularPesoQuantidade();
			}
						
			int escolha = 0;
            boolean sair = false;
			do {
				try {
					System.out.println("1 - Adicionar novos itens nesta entrega.");
					System.out.println("2 - Continuar");
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
                        sair = true;
                    break;
    
                    case 2:
                        calcularPesoDescarregar();
                        sair = true;
                        sairItens = true;
                    break;
    
                    default:
                        System.out.println("Opção Inválida, por favor verifique e tente novamente");
                        System.out.println("");
                    break;
                }
			}while(!sair);
        }while(!sairItens);
    }

    public void calcularPesoDescarregar() {
        double soma = 0d;
        for (Item item : itensDescarregar) {
            soma += item.getPesoTotal();
            setPesoDescarregar(soma);
        }
    }

    public void calcularModalidades(int pequeno, int medio, int grande, double pesoTotal, double pesoDescarregadoAcumulado){

        int p = pequeno, m = medio, g = grande;
        double pesoRestante = pesoDescarregadoAcumulado;
        if(pesoDescarregadoAcumulado != 0){
            if(pesoTotal > 1000){
                do{
                    if(pesoRestante >= 10000){
                        if(g > 0){
                            g--;
                            pesoRestante -= 10000;
                            continue;
                        }
                    }
                    if(pesoRestante >= 4000){
                        if(m > 0){
                            m--;
                            pesoRestante -= 4000;
                            continue;
                        }
                    }
                    if(pesoRestante >= 1000){
                        if(p > 0){
                            p--;
                            pesoRestante -= 1000;
                            continue;
                        }
                    }
                    break;
                }while(pesoRestante >= 0);
            }
            
            
        }

		setCaminhaoPequeno(p);
		setCaminhaoMedio(m);
		setCaminhaoGrande(g);
    }

    public void calcularCusto(double[] valores) {
        
		double custoP = getCaminhaoPequeno()* valores[0];
        setCustoP(custoP);
		double custoM = getCaminhaoMedio() * valores[1];
        setCustoM(custoM);
		double custoG = getCaminhaoGrande() * valores[2];
        setCustoG(custoG);

		double custoTrecho = (custoP + custoM + custoG)*getDistancia();
		setCustoTrecho(custoTrecho);
    }

    public void info() {

        String itens = "";
        for (Item item : getItensDescarregar()) {
            itens += item.toString() + "\n";
        }

        System.out.printf("Cidade Origem: %s\n" +
                        "Cidade Destino: %s \n" +
                        "Distancia: %dkm\n" +
                        "Categoria do destino: %s\n\n" +
                        "Itens a descarregar:\n" +
                        "%s" +
                        "Peso a descarregar: %.2f\n" +
                        "Modalidade necessaria:\n" +
                        "%d caminhão pequeno\n" +
                        "%d caminhão médio\n" + 
                        "%d caminhão grande.\n" +
                        "Custo do trecho: R$%.2f\n\n", cidadeOrigem, cidadeDestino, distancia, categoriaDestino,
                        itens, pesoDescarregar, caminhaoPequeno, caminhaoMedio, caminhaoGrande, custoTrecho);

    }

}
