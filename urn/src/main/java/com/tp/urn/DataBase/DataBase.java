package com.tp.urn.DataBase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

interface IDataBase {
    /**
     * Function that get a voter based on the voter title.
     * 
     * @param title the voter title.
     * @return the voter.
     */
    public Voter getVoter(String title);

    /**
     * Function that sets that the voter has already voted.
     * 
     * @param voter the voter that has already voted.
     */
    public void setHasVoted(String title);

    /**
     * Function that verifies whether a candidate exists in function of the his
     * number.
     * 
     * @param num candidate number.
     * @return whether the candidate exists or not.
     */
    public boolean candidateExists(int num);

    /**
     * Function that verifies if a starting password exists.
     * 
     * @param password the password to be verified.
     * @return boolean indicating whether the starting password exists.
     */
    public boolean startPwExists(String password);

    /**
     * Function that verifies if a ending password matches with a ending password.
     * 
     * @param startPassword the start password.
     * @param endPassword   the ending password.
     * @return boolean inidicating whether the ending password matches the start
     *         password.
     */
    public boolean endingPwMatches(String startPassword, String endPassword);

    /**
     * Function that compute the vote.
     * 
     * @param number the candidate number.
     */
    public void computeVote(int number);

    /**
     * Function that prints the statistics.
     * 
     */
    public void logStatistics();
}

public class DataBase implements IDataBase {
    private HashMap<String, Voter> voters;
    private HashMap<String, Credential> credentials;
    private HashMap<Integer, Candidate> candidates;
    private HashMap<Integer, Party> parties;
    private Statistics statistics;

    // =========================================================================
    public DataBase() {
        // Read the data base files (from .csv)
        FileReader fr;
        try {
            this.voters = new HashMap<String, Voter>();
            this.credentials = new HashMap<String, Credential>();
            this.candidates = new HashMap<Integer, Candidate>();
            this.parties = new HashMap<Integer, Party>();
            //
            String line;

            // Initialize the voters
            fr = new FileReader("src/main/java/com/tp/urn/DataBase/voters.csv");
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Voter voter = new Voter(values[0], values[1], values[2], values[3] == "1");
                this.voters.put(values[0], voter);
            }

            // Initialize the parties
            fr = new FileReader("src/main/java/com/tp/urn/DataBase/credentials.csv");
            br = new BufferedReader(fr);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Credential credential = new Credential(values[0], values[1]);
                this.credentials.put(values[0], credential);
            }

            // Initialize the parties
            fr = new FileReader("src/main/java/com/tp/urn/DataBase/candidates.csv");
            br = new BufferedReader(fr);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int number = Integer.parseInt(values[0]);
                Candidate candidate = new Candidate(number, values[1]);
                this.candidates.put(number, candidate);
            }

            // Initialize the parties
            fr = new FileReader("src/main/java/com/tp/urn/DataBase/parties.csv");
            br = new BufferedReader(fr);
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int number = Integer.parseInt(values[0]);
                Party party = new Party(number, values[1], values[2]);
                this.parties.put(number, party);
            }

            this.statistics = new Statistics(this.candidates);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        } catch (IOException ex) {
            System.out.println("IO exception.");
        }
    }

    @Override
    public Voter getVoter(String title) {
        return this.voters.get(title);
    }

    @Override
    public void setHasVoted(String title) {
        Voter voter = this.voters.get(title);
        if (voter != null) {
            voter.markAsVoted();
        }
    }

    @Override
    public boolean candidateExists(int num) {
        return this.candidates.containsKey(num);
    }

    @Override
    public boolean startPwExists(String password) {
        return this.credentials.containsKey(password);
    }

    @Override
    public boolean endingPwMatches(String startPassword, String endPassword) {
        var credential = this.credentials.get(startPassword);
        if (credential != null) {
            return credential.getEndPassword().equals(endPassword);
        }
        return false;
    }

    @Override
    public void computeVote(int number) {
        this.statistics.insertVote(number);
    }

    @Override
    public void logStatistics() {
        this.statistics.logStatistics(this.candidates);
    }
}