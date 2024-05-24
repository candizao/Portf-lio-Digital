package pt.ulusofona.aed.deisiworldmeter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Pais {
    int linha;
    int id;

    HashMap<Integer,Populacao> populacaos = new HashMap();
    ArrayList<Cidade> cidades = new ArrayList<>();
    String alpha2;
    String alpha3;
    String nome;
    int idRep;

    public Pais(int id, String alpha2, String alpha3, String nome) {
        this.id = id;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.nome = nome;
    }

    public Pais( int id, String alpha2, String alpha3, String nome, int linha) {
        this.linha = linha;
        this.id = id;
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.nome = nome;

    }

    @Override
    public String toString() {
        if (id>700){
            return nome+ " | "+id+" | "+alpha2.toUpperCase()+" | "+alpha3.toUpperCase()+" | "+idRep;
        }
        return nome+ " | "+id+" | "+alpha2.toUpperCase()+" | "+alpha3.toUpperCase();
    }
}
