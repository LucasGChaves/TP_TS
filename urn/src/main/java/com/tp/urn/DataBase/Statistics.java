package com.tp.urn.DataBase;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    /**
     * Maps the president number to the accounted number of valid votes.
     */
    private HashMap<Integer, Integer> presidentMap;
    /**
     * Maps the congress person number to the accounted number of valid votes.
     */
    private HashMap<Integer, Integer> congressPersonMap;
    /**
     * Number of white votes.
     */
    private int numWhite;
    /**
     * Number of valid votes.
     */
    private int numValid;
    /**
     * Number of null votes.
     */
    private int numNull;

    // =========================================================================

    public Statistics(HashMap<Integer, Candidate> candidates) {
        this.presidentMap = new HashMap<Integer, Integer>();
        this.congressPersonMap = new HashMap<Integer, Integer>();
        this.numWhite = 0;
        this.numValid = 0;
        this.numNull = 0;
        // Inicialize o mapa de estatisticas
        candidates.forEach((k, c) -> {
            int num = c.getNumber();
            // President
            if (num < 100) {
                this.presidentMap.put(num, 0);
            }
            // CongressPerson
            else {
                this.congressPersonMap.put(num, 0);
            }
        });

    }

    /**
     * Function that log the statistics.
     * 
     */
    public void logStatistics(HashMap<Integer, Candidate> candidates) {
        // Sort the results
        ArrayList<Integer[]> presArr = new ArrayList<Integer[]>(), congressArr = new ArrayList<Integer[]>();

        this.presidentMap.forEach((key, value) -> {
            Integer tmp[] = { key, value };
            presArr.add(tmp);
        });

        this.congressPersonMap.forEach((key, value) -> {
            Integer tmp[] = { key, value };
            congressArr.add(tmp);
        });

        presArr.sort((c1, c2) -> c2[1] - c1[1]);
        congressArr.sort((c1, c2) -> c2[1] - c1[1]);

        // If the election was valid but there were no votes in one or more roles
        System.out.println("==== RESULTADO DA ELEIÇÃO ====");
        if (presArr.get(0)[1] == 0 || congressArr.get(0)[1] == 0) {

            System.out.println("==== Sessão eleitoral invalidada devido à falta de votos em um ou mais cargos.");
            return;
        }

        // Log the results of the election
        // President
        System.out.println("==== PRESIDENTE:");
        int index = 1;
        // If there was a tie
        if (presArr.get(0)[1] == presArr.get(1)[1]) {
            System.out.println("======== Empate:");
            System.out.println(String.format("============ [%d] %s: %d votos", presArr.get(0)[0],
                    candidates.get(presArr.get(0)[0]).getName(), presArr.get(0)[1]));
            // Search all the ties
            while (index < presArr.size() && presArr.get(0)[1] == presArr.get(index)[1]) {
                System.out.println(String.format("============ [%d] %s: %d votos", presArr.get(index)[0],
                        candidates.get(presArr.get(index)[0]).getName(), presArr.get(index)[1]));
                index++;
            }
        }
        // If some one was elected
        else {
            System.out.println("======== Eleito:");
            System.out.println(String.format("============ [%d] %s: %d votos", presArr.get(0)[0],
                    candidates.get(presArr.get(0)[0]).getName(), presArr.get(0)[1]));
        }
        System.out.println("======== Outros:");
        for (; index < presArr.size(); index++)
            System.out
                    .println(String.format("============ [%d] %s: %d votos", presArr.get(index)[0],
                            candidates.get(presArr.get(index)[0]).getName(), presArr.get(index)[1]));

        // Congress Person
        System.out.println("==== DEPUTADO:");

        index = 0;
        for (; index < 6; index++) {
            // If there are a tie with a candidate outisde the election zone
            if (congressArr.get(index)[1] == congressArr.get(6)[1]) {
                System.out.println("======== Empate:");
                int tied = congressArr.get(index)[1];
                while (index < congressArr.size() && congressArr.get(index)[1] == tied) {
                    System.out.println(
                            String.format("============ [%d] %s: %d votos", congressArr.get(index)[0],
                                    candidates.get(congressArr.get(index)[0]).getName(),
                                    congressArr.get(index)[1]));
                    index++;
                }
                break;
            }
            // If at least one was elected
            else if (index == 0) {
                System.out.println("======== Eleitos:");
            }
            System.out.println(
                    String.format("============ [%d] %s: %d votos", congressArr.get(index)[0],
                            candidates.get(congressArr.get(index)[0]).getName(),
                            congressArr.get(index)[1]));
        }
        System.out.println("======== Outros:");
        for (; index < congressArr.size(); index++)
            System.out.println(
                    String.format("============ [%d] %s: %d votos", congressArr.get(index)[0],
                            candidates.get(congressArr.get(index)[0]).getName(),
                            congressArr.get(index)[1]));

        System.out.println("==== MÉTRICAS:");
        System.out.println(
                String.format("============ Votos válidos: %d", this.numValid));
        System.out.println(
                String.format("============ Votos brancos: %d", this.numWhite));
        System.out.println(
                String.format("============ Votos nulos: %d", this.numNull));
        System.out.println("==============================");
    }

    /**
     * Function that compute a vote in the statistics.
     * 
     * @param candidateNum the candidate number.
     */
    public void insertVote(int candidateNum) {
        // If voted null
        if (candidateNum == -1)
            this.numNull++;
        // If voted white
        else if (candidateNum == -2)
            this.numWhite++;
        //
        else {
            //
            HashMap<Integer, Integer> map;
            if (candidateNum < 100)
                map = this.presidentMap;
            else
                map = this.congressPersonMap;

            //
            Integer thisVotes = map.get(candidateNum);
            map.put(candidateNum, thisVotes + 1);
            this.numValid++;
        }
    }
}
