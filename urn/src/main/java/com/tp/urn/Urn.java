package com.tp.urn;

import java.util.Scanner;

import com.tp.urn.DataBase.DataBase;
import com.tp.urn.DataBase.Voter;

public class Urn {
    // The password used by the user to start the electoral process
    private String startPasswordUsed;
    // An interface to the internal data base of the urn
    private DataBase db;
    // An user input scanner
    private Scanner sc;

    // =========================================================================
    public Urn() {
        System.out.print("Bem vindo, esta é a urna eletrônica do STE.\r\n\r\n");
        //
        this.startPasswordUsed = "";
        this.db = new DataBase();
        this.sc = new Scanner(System.in);
    }

    /**
     * Destroys the urn.
     */
    public void destroy() {
        sc.close();
    }

    /**
     * Function that try to start the election session. This function try to
     * read an valid start password.
     * 
     * @return returns true whether the start password is valid.
     */
    public void tryToStart() {
        System.out.print("Insira a senha de início de votação: ");

        // Read the input and try to get the possible user password
        String userPassword;
        while (this.sc.hasNextLine() && !((userPassword = this.sc.nextLine()).equals(""))) {
            // If the start password is valid
            if (this.db.startPwExists(userPassword)) {
                this.startPasswordUsed = userPassword;
                return;
            }
            // If the password is invalid
            System.out.println("Senha inválida!");
            System.out.print("Insira a senha de início de votação: ");
        }
    }

    /**
     * Function that reads the voter electoral title and verifies if it's valid.
     * If the user writes -1, then it will return -1 to denotate that it's
     * wanted to finish the session.
     * 
     * @return -1 if it's wanted to finish the session, empty string if the
     *         title is invalid or the written title if it's valid.
     */
    public String autenticateVoter() {
        System.out.print("Insira seu título de eleitor: ");

        // Read the input and try to get the possible user password
        String title;
        while (!this.sc.hasNextLine() || ((title = this.sc.nextLine()).equals(""))) {
        }

        // If the user wants to try to end the session
        if (title.equals("-1"))
            return "-1";

        // Autenticate the voter
        Voter voter = this.db.getVoter(title);
        if (voter != null) {
            // If the voter has already voted
            if (voter.hasVoted()) {
                System.out.print("Eleitor já votou!\r\n");
                return "";
            }
            return title;
        }

        // If the title is invalid
        System.out.print("Título inválido!\r\n");
        return "";
    }

    /**
     * Function that computes the voter's votes.
     * 
     */
    public void readVoterCandidates(String title) {
        Voter voter = this.db.getVoter(title);
        // Mark voter as voted
        voter.markAsVoted();

        // Read the president number
        String presNum = "";
        int n = 0;
        do {
            System.out.print("\tInsira voto para presidente (2 dígitos ou escreva branco/nulo): ");
            // Waits until any input
            while (!this.sc.hasNextLine() || ((presNum = this.sc.nextLine()).equals(""))) {
            }
            // If voted null or white
            if (presNum.equals("nulo")) {
                presNum = "-1";
                break;
            }
            if (presNum.equals("branco")) {
                presNum = "-2";
                break;
            }
            // If the string inserted is not a number
            if (!presNum.matches("\\w*\\d+\\w*")) {
                System.out.println("\tInsira um número.");
            }
            // The number doesn't have 2 digits
            else if (presNum.length() != 2) {
                System.out.println("\tInsira um número com 2 dígitos.");
            } else {
                // Casts the number
                n = Integer.parseInt(presNum);
                if (!this.db.candidateExists(n)) {
                    System.out.println("\tCandidato não existe. Voto nulo.");
                    presNum = "-1";
                    break;
                }
            }
        } while (!presNum.matches("\\w*\\d+\\w*") || (presNum.length() != 2));

        // Read the president number
        String congressPersons[] = { new String(), new String() };
        for (int i = 0; i < 2; i++) {
            n = 0;
            do {
                System.out.print("\tInsira voto para deputado (4 dígitos ou escreva branco/nulo): ");
                // Waits until any input
                while (!this.sc.hasNextLine() || ((congressPersons[i] = this.sc.nextLine()).equals(""))) {
                }
                // If voted null or white
                if (congressPersons[i].equals("nulo")) {
                    congressPersons[i] = "-1";
                    break;
                }
                if (congressPersons[i].equals("branco")) {
                    congressPersons[i] = "-2";
                    break;
                }
                // If the string inserted is not a number
                if (!congressPersons[i].matches("\\w*\\d+\\w*")) {
                    System.out.println("\tInsira um número.");
                }
                // The number doesn't have 4 digits
                else if (congressPersons[i].length() != 4) {
                    System.out.println("\tInsira um número com 4 dígitos.");
                }
                // If the 2nd vote is equal to the first one
                else if (i == 1 && congressPersons[1].equals(congressPersons[0])) {
                    System.out.println("\tNão é possível votar 2 vezes em um mesmo candidato.");
                } else {
                    // Casts the number
                    n = Integer.parseInt(congressPersons[i]);
                    if (!this.db.candidateExists(n)) {
                        System.out.println("\tCandidato não existe. Voto nulo.");
                        congressPersons[i] = "-1";
                        break;
                    }
                }
            } while (congressPersons[i].length() != 4 || !congressPersons[i].matches("\\w*\\d+\\w*")
                    || !this.db.candidateExists(n)
                    // Make sure 2 valid votes are equal
                    || (i == 1 && congressPersons[1] == congressPersons[0]));
        }

        // Account the votes
        this.db.computeVote(Integer.parseInt(presNum));
        this.db.computeVote(Integer.parseInt(congressPersons[0]));
        this.db.computeVote(Integer.parseInt(congressPersons[1]));
        //
        System.out.println("");
    }

    /**
     * Function that tries to end the electoral session. If the user doesn't insert
     * the corrent ending password in 3 tries, then the electoral session is aborted
     * and deleted.
     * 
     */
    
    //Comentário aleatório
    
    public void tryToEnd() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Insira senha para finalizar sessão: ");
            String endPassword;
            while (!this.sc.hasNextLine() || ((endPassword = this.sc.nextLine()).equals(""))) {
            }

            // If the ending password matches the used start password
            if (this.db.endingPwMatches(this.startPasswordUsed, endPassword)) {
                System.out.println("Sessão encerrada com sucesso!");
                this.db.logStatistics();
                return;
            }
        }
        System.out.println("Sessão finalizada sem sucesso (estouro de 3 tentativas para finalizar).");
    }
}
