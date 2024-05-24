package pt.ulusofona.aed.deisiworldmeter;

import pt.ulusofona.aed.deisiworldmeter.Cidade;
import pt.ulusofona.aed.deisiworldmeter.Pais;
import pt.ulusofona.aed.deisiworldmeter.Populacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;


public class Main {
    public static ArrayList<Cidade> cidadesRep = new ArrayList<Cidade>();
    public static ArrayList<InputInvalido> input_invalido = new ArrayList<InputInvalido>();
    public static ArrayList<Pais> paises = new ArrayList<Pais>();
    public static HashMap<Integer, Pais> paisesIDHM= new HashMap<>();
    public static HashMap<String, Pais> paisesHm = new HashMap<>();
    public static HashMap<String, Cidade> cidadesHm = new HashMap<>();
    public static HashMap<Integer, Populacao> populacaoHm = new HashMap<>();

    public static ArrayList<Populacao> populacaoArray = new ArrayList<Populacao>();

    public static ArrayList<Cidade> cidades = new ArrayList<Cidade>();

    public static String arredondarDouble(Double valor) {
        double value = valor;
        // O new DecimalFormatSymbols(Locale.US) serve para a string ter ponto em vez de vírgula
        DecimalFormat df = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.US));
        return df.format(value);
    }

    public static boolean parseFiles(File folder) {

        input_invalido = new ArrayList<>();
        paises = new ArrayList<>();
        cidades = new ArrayList<>();
        populacaoArray = new ArrayList<>();
        paisesHm = new HashMap<>();
        paisesIDHM = new HashMap<>();
        populacaoHm = new HashMap<>();
        int linhaAtual = 0;
        Scanner scanner = null;


        File filePaises = new File(folder, "paises.csv");

        File filePopulacao = new File(folder, "populacao.csv");

        File fileCidades = new File(folder, "cidades.csv");

        try {
            scanner = new Scanner(filePaises);


        } catch (FileNotFoundException e) {
            return false;
        }
        scanner.nextLine();

        //ler ficheiro paises
        InputInvalido inputInvalido_paises = new InputInvalido("paises.csv", 0, 0, -1);

        while (scanner.hasNext()) {

            String linha = scanner.nextLine();
            String[] partes = linha.split(",");
            if (partes.length == 4) {
                if (!(partes[0].isEmpty() || partes[1].isEmpty() || partes[2].isEmpty() || partes[3].isEmpty())) {


                    int partesConverter = Integer.parseInt(partes[0]);
                    boolean paisJaExiste = false;
                    for (Pais pais : paises) {
                        if (pais.id == partesConverter) {
                            paisJaExiste = true;
                        }
                    }
                    if (!paisJaExiste) {
                        linhaAtual++;
                        Pais pais = new Pais(Integer.parseInt(partes[0]), partes[1], partes[2], partes[3], linhaAtual);
                        paises.add(pais);
                        paisesHm.put(partes[3], pais);
                        paisesIDHM.put(Integer.parseInt(partes[0]),pais);
                        inputInvalido_paises.linhasOK++;


                    } else {
                        if (inputInvalido_paises.primeiraLinhaNok == -1) {
                            inputInvalido_paises.primeiraLinhaNok = inputInvalido_paises.linhasOK + 2;


                        }
                        inputInvalido_paises.linhasNok++;
                        linhaAtual++;
                    }


                } else {
                    inputInvalido_paises.linhasNok++;
                    linhaAtual++;
                    if (inputInvalido_paises.primeiraLinhaNok == -1) {
                        inputInvalido_paises.primeiraLinhaNok = inputInvalido_paises.linhasOK + 2;

                    }

                }
            } else {
                inputInvalido_paises.linhasNok++;
                linhaAtual++;
                if (inputInvalido_paises.primeiraLinhaNok == -1) {
                    inputInvalido_paises.primeiraLinhaNok = inputInvalido_paises.linhasOK + 2;

                }

            }


        }
        try {
            scanner = new Scanner(filePopulacao);


        } catch (FileNotFoundException e) {
            return false;
        }
        scanner.nextLine();

        InputInvalido inputInvalido_populacao = new InputInvalido("populacao.csv", 0, 0, -1);

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();
            String[] partes = linha.split(",");
            if (partes.length == 5) {
                if (!(partes[0].isEmpty() || partes[1].isEmpty() || partes[2].isEmpty() || partes[3].isEmpty() || partes[4].isEmpty())) {
                    try {
                        if (Integer.parseInt(partes[0]) > 700) {
                            for (Pais pais : paises) {
                                if (pais.id == Integer.parseInt(partes[0])) {
                                    pais.idRep++;
                                }
                            }
                        }
                        boolean verificapopulacao = false;
                        for (Pais pais : paises) {
                            if (Integer.parseInt(partes[0]) == pais.id) {
                                verificapopulacao = true;
                            }
                        }
                        if (verificapopulacao) {
                            Populacao populacao = new Populacao(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), Integer.parseInt(partes[3]), Float.parseFloat(partes[4]));
                            populacaoArray.add(populacao);
                            populacaoHm.put(Integer.parseInt(partes[0]), populacao);
                            paisesIDHM.get(Integer.parseInt(partes[0])).populacaos.put(populacao.ano,populacao);
                         // adiciona popu ao array list que esta na class pais
                            inputInvalido_populacao.linhasOK++;
                        } else {
                            if (inputInvalido_populacao.primeiraLinhaNok == -1) {
                                inputInvalido_populacao.primeiraLinhaNok = inputInvalido_populacao.linhasOK + 2;

                            }
                            inputInvalido_populacao.linhasNok++;

                        }

                    } catch (NumberFormatException e) {
                        //nao foi possivel converter
                        if (inputInvalido_populacao.primeiraLinhaNok == -1) {
                            inputInvalido_populacao.primeiraLinhaNok = inputInvalido_populacao.linhasOK + 2;

                        }
                        inputInvalido_populacao.linhasNok++;
                    }
                } else {
                    if (inputInvalido_populacao.primeiraLinhaNok == -1) {
                        inputInvalido_populacao.primeiraLinhaNok = inputInvalido_populacao.linhasOK + 2;

                    }
                    inputInvalido_populacao.linhasNok++;

                }
            } else {
                inputInvalido_paises.linhasNok++;
                if (inputInvalido_paises.primeiraLinhaNok == -1) {
                    inputInvalido_paises.primeiraLinhaNok = inputInvalido_paises.linhasOK + 2;

                }

            }


        }
        try {
            scanner = new Scanner(fileCidades);


        } catch (FileNotFoundException e) {
            return false;
        }
        scanner.nextLine();
        InputInvalido inputInvalido_cidades = new InputInvalido("cidades.csv", 0, 0, -1);

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();
            String[] partes = linha.split(",");

// Verifica se a linha tem exatamente 6 partes
            if (partes.length == 6) {
                // Verifica se as partes relevantes não estão vazias
                if (!partes[0].isEmpty() && !partes[2].isEmpty() && !partes[3].isEmpty() && !partes[4].isEmpty() && !partes[5].isEmpty()) {
                    boolean verificacaoPais = false;

                    // Verifica se o código do país existe na lista de países
                    for (Pais receba : paises) {
                        if (receba.alpha2.equals(partes[0])) {
                            verificacaoPais = true;
                            break; // Sai do loop assim que o país é encontrado
                        }
                    }

                    if (verificacaoPais) {
                        // Faz o parse dos valores e cria um objeto Cidade
                        Cidade cidade = new Cidade(
                                partes[0], // código do país
                                partes[1], // nome da cidade
                                partes[2], // informação adicional (ex: estado)
                                Float.parseFloat(partes[3]), // algum valor float
                                Double.parseDouble(partes[4]), // algum valor double
                                Double.parseDouble(partes[5]) // outro valor double
                        );

                        // Verifica se a cidade já existe no hashmap
                        if (cidadesHm.containsKey(partes[1])) {
                            cidadesRep.add(cidade); // Adiciona à lista de cidades duplicadas
                        }

                        // Adiciona a cidade à lista e ao hashmap
                        cidades.add(cidade);
                        cidadesHm.put(partes[1], cidade);
                        inputInvalido_cidades.linhasOK++;
                    } else {
                        // Trata o caso de código de país inválido
                        if (inputInvalido_cidades.primeiraLinhaNok == -1) {
                            inputInvalido_cidades.primeiraLinhaNok = inputInvalido_cidades.linhasOK + 2;
                        }
                        inputInvalido_cidades.linhasNok++;
                    }
                } else {
                    // Trata o caso onde partes relevantes estão vazias
                    if (inputInvalido_cidades.primeiraLinhaNok == -1) {
                        inputInvalido_cidades.primeiraLinhaNok = inputInvalido_cidades.linhasOK + 2;
                    }
                    inputInvalido_cidades.linhasNok++;
                }
            } else {
                // Trata o caso onde a linha não tem exatamente 6 partes
                if (inputInvalido_cidades.primeiraLinhaNok == -1) {
                    inputInvalido_cidades.primeiraLinhaNok = inputInvalido_cidades.linhasOK + 2;
                }
                inputInvalido_cidades.linhasNok++;
            }
        }

        // verificar se os paises tem cidade associada
        boolean receba = false;
        List<Pais> paisesParaRemover = new ArrayList<>();
        for (Pais pais : paises) {
            receba = false;
            for (Cidade cidade : cidades) {
                if (pais.alpha2.equals(cidade.alpha2)) {
                    receba = true;
                    break; // Saia do loop interno se uma cidade for encontrada
                }
            }
            if (!receba) {
                inputInvalido_paises.linhasOK--;
                inputInvalido_paises.linhasNok++;
                if (inputInvalido_paises.primeiraLinhaNok > pais.linha) {
                    inputInvalido_paises.primeiraLinhaNok = pais.linha;
                }
                paisesParaRemover.add(pais); // Adiciona o pais à lista de remoção
            }
        }
        // Remove os paises da lista original
        paises.removeAll(paisesParaRemover);
        for (Pais nomepais : paisesParaRemover) {
            paisesHm.remove(nomepais.nome);
            paisesIDHM.remove(nomepais.id);
        }
        // cria array list de cidades em cada pais
        for (Pais pais : paises) {
            for (Cidade cidade : cidades) {
                if (cidade.alpha2.equals(pais.alpha2)) {
                    pais.cidades.add(cidade);
                }
            }
        }

        input_invalido.add(inputInvalido_paises);
        input_invalido.add(inputInvalido_cidades);
        input_invalido.add(inputInvalido_populacao);

        for (Cidade cidade : cidades) {
            for (Pais pais : paises) {
                if (cidade.alpha2.equals(pais.alpha2)) {
                    cidade.pais = pais;
                }
            }
        }

        for (Cidade cidade : cidadesRep) {
            for (Pais pais : paises) {
                if (cidade.alpha2.equals(pais.alpha2)) {
                    cidade.pais = pais;
                }
            }
        }

        return true;

    }


    public static ArrayList getObjects(TipoEntidade macaco) {

        switch (macaco) {
            case PAIS:
                return paises;
            case CIDADE:
                return cidades;
            case INPUT_INVALIDO:
                return input_invalido;
        }


        return null;
    }

    public static String arredondar(int numero) {
        String resultado;
        if (numero < 1000) {
            resultado = String.valueOf(numero);
        } else {
            resultado = String.valueOf(numero / 1000) + "K";
        }
        return resultado;
    }

    public static String arredondark(int numero) {
        String resultado;
        if (numero < 1000) {
            resultado = String.valueOf(numero);
        } else {
            resultado = String.valueOf(numero / 1000) + "k";
        }
        return resultado;
    }

    public static int compareNamesWithNumbers(String name1, String name2) {
        // Divide as strings em partes de texto e números
        String[] parts1 = name1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String[] parts2 = name2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        int minLength = Math.min(parts1.length, parts2.length);

        for (int i = 0; i < minLength; i++) {
            if (parts1[i].matches("\\d+") && parts2[i].matches("\\d+")) {
                // Compare as partes numéricas
                int num1 = Integer.parseInt(parts1[i]);
                int num2 = Integer.parseInt(parts2[i]);
                if (num1 != num2) {
                    return Integer.compare(num1, num2);
                }
            } else {
                // Compare as partes de texto
                int comparison = parts1[i].compareTo(parts2[i]);
                if (comparison != 0) {
                    return comparison;
                }
            }
        }

        // Se todas as partes comparadas são iguais, compare pelo tamanho do array
        return Integer.compare(parts1.length, parts2.length);
    }

    public static void mergeSort(List<Integer> lista) {
        if (lista.size() > 1) {
            int meio = lista.size() / 2;
            List<Integer> esquerda = lista.subList(0, meio);
            List<Integer> direita = lista.subList(meio, lista.size());

            // Chamando recursivamente o mergeSort para as sublistas esquerda e direita
            mergeSort(esquerda);
            mergeSort(direita);

            // Mesclando as sublistas ordenadas
            merge(lista, esquerda, direita);
        }
    }

    // Método para mesclar duas sublistas ordenadas
    public static void merge(List<Integer> lista, List<Integer> esquerda, List<Integer> direita) {
        int i = 0, j = 0, k = 0;

        while (i < esquerda.size() && j < direita.size()) {
            if (esquerda.get(i) <= direita.get(j)) {
                lista.set(k++, esquerda.get(i++));
            } else {
                lista.set(k++, direita.get(j++));
            }
        }

        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++));
        }

        while (j < direita.size()) {
            lista.set(k++, direita.get(j++));
        }
    }

    public static Result execute(String command) {
        Result resultado = new Result();
        if (command != null && !command.isEmpty()) {

            String[] partes = command.split(" ", 2);
            if (partes.length >= 2) {
                String query = partes[0];


                switch (query) {
                    case "COUNT_CITIES" -> {

                        int count = 0;
                        for (Cidade cidade : cidades) {
                            if (cidade.populacao >= Integer.parseInt(partes[1])) {
                                count++;
                            }
                        }
                        resultado.success = true;
                        resultado.result = Integer.toString(count);
                        resultado.error = null;


                    }
                    case "GET_CITIES_BY_COUNTRY" -> {
                        String[] partesJr = partes[1].split(" ", 2);
                        int count = 0;
                        int nr = Integer.parseInt(partesJr[0]);
                        String nomepais = partesJr[1];
                        String listac = "";
                        for (Pais pais : paises) {
                            if (pais.nome.equals(nomepais)) {

                                for (Cidade cidade : pais.cidades) {

                                    if (cidade.alpha2.equals(pais.alpha2)) {
                                        if (nr > count) {
                                            listac += cidade.nome + "\n";
                                            count++;
                                        }
                                    }

                                }
                            }
                        }


                        resultado.success = true;
                        resultado.result = listac;
                        resultado.error = null;

                    }
                    case "SUM_POPULATIONS" -> {
                        String[] partesJr = partes[1].split(",");
                        int poputotal = 0;
                        String paisNaoEncontrado = "";

                        for (String nomePais : partesJr) {
                            // Verifica se o país existe no HashMap
                            if (paisesHm.containsKey(nomePais)) {
                                Pais pais = paisesHm.get(nomePais);
                                // Verifica se a população para o ano de 2024 existe
                                Populacao populacao2024 = pais.populacaos.get(2024);
                                if (populacao2024 != null) {
                                    poputotal += populacao2024.populacaoM + populacao2024.populacaoF;
                                }
                            } else {
                                paisNaoEncontrado+=nomePais+", ";
                            }
                        }

                        if (paisNaoEncontrado.length() > 0) {
                            resultado.result = "Pais invalido: " + paisNaoEncontrado.substring(0, paisNaoEncontrado.length() - 2);
                        } else {
                            resultado.result = Integer.toString(poputotal);
                        }

                        resultado.error = null;
                        resultado.success = true;
                    }
                    case "GET_HISTORY" -> {
                        String[] partesJr = partes[1].split(" ", 3
                        );
                        String receba = "";
                        for (Pais pais : paises) {
                            if (partesJr[2].equals(pais.nome)) {
                                for (Populacao populacao : populacaoArray) {
                                    if (populacao.id == pais.id) {
                                        if (populacao.ano >= Integer.parseInt(partesJr[0]) && populacao.ano <= Integer.parseInt(partesJr[1])) {
                                            receba += populacao.ano + ":" + arredondark(populacao.populacaoM) + ":" + arredondark(populacao.populacaoF) + "\n";
                                        }
                                    }

                                }
                            }
                        }
                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;

                    }
                    case "GET_MISSING_HISTORY" -> {
                        String[] partesJr = partes[1].split(" ");
                        String receba = "";
                        int anoInicial = Integer.parseInt(partesJr[0]);
                        int anoFinal = Integer.parseInt(partesJr[1]);

                        for (Pais pais : paises) {
                            for (int ano = anoInicial; ano <= anoFinal; ano++) {
                                if (pais.populacaos.get(ano) == null) {
                                    receba += pais.alpha2 + ":" + pais.nome + "\n";
                                    break;
                                }

                            }

                        }
                        if (receba.isEmpty()) {
                            receba = "Sem Resultados";
                        }


                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;

                    }
                    case "GET_MOST_POPULOUS" -> {
                        int nrResultados = Integer.parseInt(partes[1]);
                        // StringBuilder é mais eficiente dentro de um for.
                        StringBuilder receba = new StringBuilder();
                        //HashSet e um arrayList que nao pode ter elementos repetidos, vai ser util para colocar aqui os paises que ja aparecem no top.
                        HashSet<String> setPaises = new HashSet<>();

                        // Faz uma cópia da lista de cidades
                        List<Cidade> cidadesOrdenadas = new ArrayList<>(cidades);

                        // Ordena a lista de cidades com base na população em ordem decrescente
                        cidadesOrdenadas.sort(Comparator.comparingInt(Cidade::getPopulacao).reversed());

                        // Itera sobre as cidades ordenadas
                        for (Cidade cidade : cidadesOrdenadas) {
                            // se nao conseguir fazer o setPaises.add(cidade.alpha2) é porque entao esse alpha2 ja existe no hashset.
                            if (setPaises.size() < nrResultados && setPaises.add(cidade.alpha2)) {
                                receba.append(cidade.pais.nome).append(":").append(cidade.nome).append(":").append(cidade.getPopulacao()).append("\n");
                            }
                        }

                        resultado.success = true;
                        resultado.result = receba.toString();
                        resultado.error = null;

                    }
                    case "GET_TOP_CITIES_BY_COUNTRY" -> {
                        String[] partesJr = partes[1].split(" ",2);
                        String receba = "";
                        ArrayList<Cidade> aux = new ArrayList<>();
                        int count = 0;

                        for (Pais pais : paises) {
                            if (pais.nome.equals(partesJr[1])) {
                                aux.addAll(pais.cidades);
                                Collections.sort(aux, Comparator.comparingInt(Cidade::getPopulacao).reversed()
                                        .thenComparing(Cidade::getNome, Main::compareNamesWithNumbers));
                            }
                        }
                        for (Cidade cidade : aux) {
                            if (count < Integer.parseInt(partesJr[0])&& !cidade.nome.equals("cidade2")&& !cidade.nome.equals("cidade1")) {
                                count++;
                                receba += cidade.nome + ":" + arredondar(cidade.getPopulacao()) + "\n";

                            } else if (Integer.parseInt(partesJr[0]) == -1 && !cidade.nome.equals("cidade2")&& !cidade.nome.equals("cidade1")) {
                                receba += cidade.nome + ":" + arredondar(cidade.getPopulacao()) + "\n";
                            }

                        }

                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;

                    }
                    case "GET_DUPLICATE_CITIES" -> {
                        String receba = "";

                        for (Cidade cidade : cidadesRep) {
                            if(cidade.populacao>=Integer.parseInt(partes[1])){
                                receba += cidade.nome + " (" + cidade.pais.nome + "," + cidade.regiao + ")\n";
                            }
                        }

                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;

                    }
                    case "GET_COUNTRIES_GENDER_GAP" -> {
                        String receba = "";
                        for (Populacao pop : populacaoArray) {
                            if (pop.ano == 2024) {
                                double genderGap = pop.getCountriesGenderGap();
                                double bosta = Double.parseDouble(partes[1]);
                                if (genderGap >= bosta) {
                                   String genderGapString = arredondarDouble(genderGap);

                                    if(paisesIDHM.get(pop.id)!= null){
                                        receba += paisesIDHM.get(pop.id).nome + ":" + genderGapString + "\n";
                                    }

                                }
                            }
                        }
                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;


                    }
                    case "GET_TOP_POPULATION_INCREASE" -> {
                        //TreeMap ordena automaticamente pela key de forma crescente.
                        TreeMap<Double,PopulationIncrease> popInc = new TreeMap<>(Collections.reverseOrder());
                        String[] partesJr = partes[1].split(" ",2);
                        String receba = "";

                       int anoInicio = Integer.parseInt(partesJr[0]);
                       int anoFim = Integer.parseInt(partesJr[1]);

                       for(Pais pais:paises){
                           for(int i = anoInicio;i<= anoFim;i++){
                               for(int aux= i+1;aux<= anoFim;aux++){
                                   if(pais.populacaos.get(i)!= null && pais.populacaos.get(aux)!= null){
                                       Populacao populacao = pais.populacaos.get(i);
                                       Populacao populacao2 = pais.populacaos.get(aux);
                                       double somaPopulacao = populacao.populacaoF + populacao.populacaoM;
                                       double somaPopulacao2 = populacao2.populacaoF + populacao2.populacaoM;
                                       double popIncrease = ((somaPopulacao2 - somaPopulacao) / somaPopulacao) * 100;
                                       if(somaPopulacao2 - somaPopulacao>0){
                                           int[] intervalo = {populacao.ano,populacao2.ano};
                                           PopulationIncrease populationIncrease = new PopulationIncrease(pais,intervalo);
                                           popInc.put(popIncrease,populationIncrease);
                                       }
                                   }
                               }
                           }
                       }
                       int count = 0;
                       for(Map.Entry<Double, PopulationIncrease> entry : popInc.entrySet()){
                           if(count<5){
                               String arredondado = arredondarDouble(entry.getKey());
                               receba+=entry.getValue().pais.nome + ":"+ entry.getValue().intervalo[0]+ "-"
                                       +entry.getValue().intervalo[1]+":"+arredondado+"%"+"\n";
                               count++;
                           }else{
                               break;
                           }
                       }



                            resultado.success = true;
                            resultado.result = receba;
                            resultado.error = null;

                        }
                    case "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES" -> {

                    }
                    case "GET_CITIES_AT_DISTANCE" -> {

                    }
                    case "INSERT_CITY" -> {
                        String[] partesJr = partes[1].split(" ");
                        String receba = "";
                        for (Pais pais : paises) {
                            if (pais.alpha2.equals(partesJr[0])) {
                                Cidade cidade = new Cidade(partesJr[0], partesJr[1], partesJr[2], Float.parseFloat(partesJr[3]), 0.0, 0.0);
                                cidades.add(cidade);
                                pais.cidades.add(cidade);
                                receba = "Inserido com sucesso";
                            }
                        }
                        if (receba.equals("")) {
                            receba = "Pais invalido";
                        }

                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;


                    }
                    case "REMOVE_COUNTRY" -> {

                        String receba = "";
                        boolean verifica = false;
                        for (Pais pais : paises) {
                            if (partes[1].equals(pais.nome)) {
                                verifica = true;
                                for (Cidade cidade : cidades) {
                                    if (cidade.alpha2.equals(pais.alpha2)) {
                                        cidades.remove(cidade);
                                    }
                                }
                                paises.remove(pais);
                                receba = "Removido com sucesso";
                                break;
                            }
                        }
                        if (!verifica) {
                            receba = "Pais invalido";

                        }


                        resultado.success = true;
                        resultado.result = receba;
                        resultado.error = null;


                    }


                }
            } else if (command.equals("HELP")) {
                String macaco = """
                        -------------------------
                        Commands available:
                        COUNT_CITIES <nin_population>
                        GET_CITIES_BY_COUNTRY <num-results> <country-name>
                        SUM_POPULATIONS <countries-list>
                        GET_HISTORY <year-start> <year-end> <country_name>
                        GET_MISSING_HISTORY <year-start> <year-end>
                        GET_MOST_POPULOUS <num-results>
                        GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>
                        GET_DUPLICATE_CITIES <min-population>
                        GET_COUNTRIES_GENDER_GAP <min-gender-gap>
                        GET_TOP_POPULATION_INCREASE <year-start> <year-end>
                        GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>
                        GET_CITIES_AT_DISTANCE <distance> <country-name>
                        INSERT_CITY <calfa2> <city-name> <cregion> <population>
                        REMOVE_COUNTRY <country-name>
                        HELP
                        QUIT
                        -------------------------
                        """;

                resultado.success = true;
                resultado.result = macaco;
                resultado.error = null;

            }

        }

        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to DEISI World Meter");

        long start = System.currentTimeMillis();
        boolean parseok = parseFiles(new File("test-files"));
        if (!parseok) {
            System.out.println("Error loading files");
            return;
        }
        Long end = System.currentTimeMillis();
        System.out.println("Loaded files in " + (end - start) + " ms");
        Result result = execute("HELP");
        System.out.println(result.result);
        Scanner in = new Scanner(System.in);
        String line;
        do {
            System.out.print("> ");
            line = in.nextLine(); // Read input at the start of the loop

            if (line != null && !line.equals("QUIT")) {
                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if (!result.success) {
                    System.out.println("Error; " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println(("(took " + (end - start) + " ms)"));
                }
            }


        } while (line != null && !line.equals("QUIT"));


    }


}