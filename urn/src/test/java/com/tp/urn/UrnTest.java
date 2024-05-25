package com.tp.urn;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrnTest {
    String pathToTestFiles = "src/test/java/TestsIO/";

    
    public static boolean compareByMemoryMappedFiles(Path path1, Path path2) throws IOException {
        try (RandomAccessFile randomAccessFile1 = new RandomAccessFile(path1.toFile(), "r");
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(path2.toFile(), "r")) {
        	
            FileChannel ch1 = randomAccessFile1.getChannel();
            FileChannel ch2 = randomAccessFile2.getChannel();
            
            if (ch1.size() != ch2.size()) {
                return false;
            }
            long size = ch1.size();
            MappedByteBuffer m1 = ch1.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            MappedByteBuffer m2 = ch2.map(FileChannel.MapMode.READ_ONLY, 0L, size);
            return m1.equals(m2);
        }
    }

    @Test
    public void StartOfVotingSession_CorrectPassword_Success() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "tryToStartInput.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "tryToStartOutput.txt"));
        
        Urn urn = new Urn();
        urn.tryToStart();

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "tryToStartOutput.txt"),
                Paths.get(pathToTestFiles + "tryToStartExpectedOutput.txt")));
    }

    @Test
    public void StartOfVotingSession_WrongPassword_Failure() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "tryToStartInput2.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "tryToStartOutput2.txt"));

        Urn urn = new Urn();
        urn.tryToStart();

        assertFalse(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "tryToStartOutput2.txt"),
                Paths.get(pathToTestFiles + "tryToStartExpectedOutput.txt")));
    }

    @Test
    public void AuthenticateVoter_CorrectTitle_Success() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "authVoterOutput.txt"));
        System.setIn(new FileInputStream(new File(pathToTestFiles + "authVoterInput.txt")));
        Urn urn = new Urn();
        String result = urn.autenticateVoter();
        assertEquals("606605162846", result);
    }

    @Test
    public void AuthenticateVoter_WrongTitle_Failure() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "authVoterOutput2.txt"));
        System.setIn(new FileInputStream(pathToTestFiles + "authVoterInput2.txt"));
        Urn urn = new Urn();
        String result = urn.autenticateVoter();
        assertNotEquals("606605162846", result);
    }

    @Test
    public void ReadVoterCandidates_CorrectVotes_Success() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "readVoterTitleOutput.txt"));
        System.setIn(new FileInputStream(pathToTestFiles + "readVoterTitleInput.txt"));

        Urn urn = new Urn();
        urn.readVoterCandidates("606605162846");
        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "readVoterTitleOutput.txt"),
                Paths.get(pathToTestFiles + "readVoterTitleExpectedOutput.txt")));
    }

    @Test
    public void ReadVoterCandidates_WrongVotes_Failure() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "readVoterTitleOutput2.txt"));
        System.setIn(new FileInputStream(pathToTestFiles + "readVoterTitleInput2.txt"));

        Urn urn = new Urn();
        urn.readVoterCandidates("606605162846");
        assertFalse(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "readVoterTitleOutput2.txt"),
                Paths.get(pathToTestFiles + "readVoterTitleExpectedOutput.txt")));
    }

    @Test
    public void TryToEnd_CorrectPassword_Success() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "tryToEndOutput.txt"));
        System.setIn(new FileInputStream(pathToTestFiles + "tryToEndInput.txt"));

        Urn urn = new Urn();
        urn.tryToStart();
        urn.tryToEnd();
        
        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "tryToEndOutput.txt"),
                Paths.get(pathToTestFiles + "tryToEndExpectedOutput.txt")));
    }

    @Test
    public void TryToEnd_WrongPassword_Failure() throws IOException {
        System.setOut(new PrintStream(pathToTestFiles + "tryToEndOutput2.txt"));
        System.setIn(new FileInputStream(pathToTestFiles + "tryToEndInput2.txt"));

        Urn urn = new Urn();
        urn.tryToStart();
        urn.tryToEnd();
        assertFalse(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "tryToEndOutput2.txt"),
                Paths.get(pathToTestFiles + "tryToEndExpectedOutput.txt")));
    }

    @Test
    public void Main_110Inputs_RunsBefore1Sec() throws FileNotFoundException {
        System.setIn(new FileInputStream(new File(pathToTestFiles + "performanceTest.txt")));
        long start = System.currentTimeMillis();

        // Main method
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
        //

        long end = System.currentTimeMillis();

        assertTrue(end - start < 1000);
    }

}
