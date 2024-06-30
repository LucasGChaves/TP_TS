package com.tp.urn;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

//import org.junit.jupiter.api.Test;

public class SystemTest {

	 String pathToTestFiles = "src/test/java/SystemTestIO/";

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
    public void votacao_sem_problemas() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "inputSystemTestAllCorrect.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "outputSystemTestAllCorrect.txt"));
        
        Urn urn = new Urn();
        
        // Simulando o processo completo de votação
        urn.tryToStart();
        
        String voterTitle;
        while (!(voterTitle = urn.autenticateVoter()).equals("-1")) {
            if (!voterTitle.isEmpty()) {
                urn.readVoterCandidates(voterTitle);
            }
        }
        
        urn.tryToEnd();
        
        urn.destroy();  // Fechar recursos

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "outputSystemTestAllCorrect.txt"),
                Paths.get(pathToTestFiles + "ExpectedOutputSystemTestAllCorrect.txt")));
    }
    
    @Test
    public void votacao_com_titulos_errados() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "inputSystemTestWrongTitles.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "outputSystemTestWrongTitles.txt"));
        
        Urn urn = new Urn();
        
        // Simulando o processo completo de votação
        urn.tryToStart();
        
        String voterTitle;
        while (!(voterTitle = urn.autenticateVoter()).equals("-1")) {
            if (!voterTitle.isEmpty()) {
                urn.readVoterCandidates(voterTitle);
            }
        }
        
        urn.tryToEnd();
        
        urn.destroy();  // Fechar recursos

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "outputSystemTestWrongTitles.txt"),
                Paths.get(pathToTestFiles + "ExpectedOutputSystemTestWrongTitles.txt")));
    }

    @Test
    public void votacao_apenas_votos_invalidos() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "inputSystemTestInvalidVotes.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "outputSystemTestInvalidVotes.txt"));
        
        Urn urn = new Urn();
        
        // Simulando o processo completo de votação
        urn.tryToStart();
        
        String voterTitle;
        while (!(voterTitle = urn.autenticateVoter()).equals("-1")) {
            if (!voterTitle.isEmpty()) {
                urn.readVoterCandidates(voterTitle);
            }
        }
        
        urn.tryToEnd();
        
        urn.destroy();  // Fechar recursos

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "outputSystemTestInvalidVotes.txt"),
                Paths.get(pathToTestFiles + "ExpectedOutputSystemTestInvalidVotes.txt")));
    }
    
    @Test
    public void invalidar_urna_se_tres_erros() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "inputSystemTestInvalidSession.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "outputSystemTestInvalidSession.txt"));
        
        Urn urn = new Urn();
        
        // Simulando o processo completo de votação
        urn.tryToStart();
        
        String voterTitle;
        while (!(voterTitle = urn.autenticateVoter()).equals("-1")) {
            if (!voterTitle.isEmpty()) {
                urn.readVoterCandidates(voterTitle);
            }
        }
        
        urn.tryToEnd();
        
        urn.destroy();  // Fechar recursos

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "outputSystemTestInvalidSession.txt"),
                Paths.get(pathToTestFiles + "ExpectedOutputSystemTestInvalidSession.txt")));
    }
    
    @Test
    public void urna_fechada_sem_votos() throws IOException {
        System.setIn(new FileInputStream(pathToTestFiles + "inputSystemTestWithoutVotes.txt"));
        System.setOut(new PrintStream(pathToTestFiles + "outputSystemTestWithoutVotes.txt"));
        
        Urn urn = new Urn();
        
        // Simulando o processo completo de votação
        urn.tryToStart();
        
        String voterTitle;
        while (!(voterTitle = urn.autenticateVoter()).equals("-1")) {
            if (!voterTitle.isEmpty()) {
                urn.readVoterCandidates(voterTitle);
            }
        }
        
        urn.tryToEnd();
        
        urn.destroy();  // Fechar recursos

        assertTrue(compareByMemoryMappedFiles(Paths.get(pathToTestFiles + "outputSystemTestWithoutVotes.txt"),
                Paths.get(pathToTestFiles + "ExpectedOutputSystemTestWithoutVotes.txt")));
    }
    
}
