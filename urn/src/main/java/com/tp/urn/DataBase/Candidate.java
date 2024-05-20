package com.tp.urn.DataBase;

public class Candidate {
    /**
     * Candidate electoral number.
     */
    private int number;
    /**
     * Candidate name.
     */
    private String name;

    // =========================================================================

    public Candidate(int number, String name) {
        this.number = number;
        this.name = name;
    }

    /**
     * Function that get the candidate number.
     * 
     * @return candidate's number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Function that get the candidate name.
     * 
     * @return candidate's name.
     */
    public String getName() {
        return this.name;
    }
}