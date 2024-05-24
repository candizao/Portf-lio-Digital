package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {

int id;
int ano;
int populacaoM;
int populacaoF;
float densidade;


    public Populacao(int id, int ano, int populacaoM, int populacaoF, float densidade) {
        this.id = id;
        this.ano = ano;
        this.populacaoM = populacaoM;
        this.populacaoF = populacaoF;
        this.densidade = densidade;
    }

    public double getCountriesGenderGap() {
        double diferencaAbsoluta = Math.abs((double) (populacaoM - populacaoF));
        double somaPopulacoes = (double) (populacaoM + populacaoF);
        double percentual = (diferencaAbsoluta / somaPopulacoes) * 100;
        return percentual;

    }


}
