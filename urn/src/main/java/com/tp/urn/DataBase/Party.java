package com.tp.urn.DataBase;

public class Party {
    /**
     * The number that represents this party.
     */
    private int number;
    /**
     * Acronym that represents this party.
     */
    private String acronym;
    /**
     * Name of this party.
     */
    private String name;

    // =========================================================================

    public Party(int number, String acronym, String name) {
        this.number = number;
        this.acronym = acronym;
        this.name = name;
    }

    /**
     * Function that gets the party number.
     * 
     * @return party's number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Function that gets the party's acronym.
     * 
     * @return party's acronym.
     */
    public String getAcronym() {
        return this.acronym;
    }

    /**
     * Function that gets the party's name.
     * 
     * @return party's name.
     */
    public String getName() {
        return this.name;
    }
}
