package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {

    Pais pais;
    String alpha2;
    String nome;
    String regiao;

    float populacao;
    double latitude;
    double longitude;

    public String getNome() {
        return nome;
    }

    public int getPopulacao() {
        return (int)populacao;
    }

    public Cidade(String alpha2, String nome, String regiao, float populacao, double latitude, double longitude) {
        this.alpha2 = alpha2;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return nome+ " | "+alpha2.toUpperCase()+" | "+regiao+" | "+(int)populacao+" | "+"("+latitude+","+longitude+")";
    }
}
