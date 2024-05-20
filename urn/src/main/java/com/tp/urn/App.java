package com.tp.urn;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Urn urn = new Urn();

        // While the urn hasn't started
        urn.tryToStart();

        // Autenticate the users while the session is not finished
        String title;
        while ((title = urn.autenticateVoter()) != "-1") {
            if (title.length() != 0) {
                urn.readVoterCandidates(title);
            }
        }

        // Try to finish the session
        urn.tryToEnd();

        // Clean up some variables and release resources
        urn.destroy();
    }
}
