package com.tp.urn.DataBase;

public class Voter {
    /**
     * 12-size char array only composed by digits.
     */
    private String titleID;
    /**
     * 11-size char array only composed by digits.
     */
    private String cpf;
    /**
     * Voter name.
     */
    private String name;
    /**
     * Boolean denoting whether the user has voted.
     */
    private boolean hasVoted;

    // =========================================================================

    public Voter(String titleID, String cpf, String name, boolean hasVoted) {
        this.titleID = titleID;
        this.cpf = cpf;
        this.name = name;
        this.hasVoted = hasVoted;
    }

    /**
     * Function that returns true if the voter has already voted.
     * 
     * @return boolean indicating if the voter has already voted.
     */
    public boolean hasVoted() {
        return hasVoted;
    }

    /**
     * Function that set the hasVoted flag as true.
     */
    public void markAsVoted() {
        this.hasVoted = true;
    }

    /**
     * Function that gets the voter's title.
     * 
     * @return voter's title.
     */
    public String getTitle() {
        return this.titleID;
    }

    /**
     * Function that returns the voter's cpf.
     * 
     * @return voter's cpf.
     */
    public String getCPF() {
        return this.cpf;
    }

    /**
     * Function that returns the voter's name.
     * 
     * @return voter's name.
     */
    public String getName() {
        return this.name;
    }
}
