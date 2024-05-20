package com.tp.urn.DataBase;

public class Credential {
    /**
     * The session start password.
     */
    private String startPassword;
    /**
     * The session ending password that needs to match to a startPassword.
     */
    private String endPassword;

    // =========================================================================

    public Credential(String startPassword, String endPassword) {
        this.startPassword = startPassword;
        this.endPassword = endPassword;
    }

    /**
     * Get this credential start password.
     * 
     * @return a string representing this credentials start password.
     */
    public String getStartPassword() {
        return this.startPassword;
    }

    /**
     * Get this credential ending password.
     * 
     * @return a string representing this credentials ending password.
     */
    public String getEndPassword() {
        return this.endPassword;
    }
}
