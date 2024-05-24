package pt.ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nomeFicheiro;
    int linhasOK;
    int linhasNok;
    int primeiraLinhaNok;

    public InputInvalido(String nomeFicheiro, int linhasOK, int linhasNok, int primeiraLinhaNok) {
        this.nomeFicheiro = nomeFicheiro;
        this.linhasOK = linhasOK;
        this.linhasNok = linhasNok;
        this.primeiraLinhaNok = primeiraLinhaNok;
    }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasOK + " | " + linhasNok + " | " + primeiraLinhaNok;
    }
}
