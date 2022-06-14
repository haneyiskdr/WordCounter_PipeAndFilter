/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package WordCounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marsyaaa
 * 
 * To filter out stop words from input text files
 * 
 */
public class StopWordFilter extends Filter implements Runnable {
    
    private List<String> stopWords = new ArrayList();
    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public StopWordFilter(Pipe in, Pipe out) {
        super(in, out);
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public long getAvgTime() {
        long total = 0;
        
        for (Long time: times) {
            total += time;
        }
        
        return (total / times.size()) / 100;
    }

    // To construct the stopwords list
    public void setUp(String filename) {

        // Try to read file, if cannot then throw exception
        try {
            //try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/WordCounter/TextFile/"+filename)));
            List<String> words = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                words.add(line);
            }

            //List<String> words = Files.readAllLines(Paths.get(getClass().getClassLoader().getResourceAsStream(filename)));
            for (String word : words) {
                String cleanedWord = word.replaceAll("[\\n\\t]", "");
                stopWords.add(cleanedWord);
            }
                /*
            } catch (URISyntaxException ue){
                System.out.println("Cannot read file");
                ue.printStackTrace();
            }*/
        } catch (IOException fe) {
            System.out.println("Unable to read from stopwords.txt.");
            fe.printStackTrace();
        }

        System.out.println("Stop word filter setup has been completed.");
        
    }

    // To filter out stop words from the input text file
    @Override
    public void run() {
        
        // System.out.println("Stop word filter has started.");

        // Call stop word filter setup
        if (stopWords.isEmpty()) {
            this.setUp("stopwords.txt");
        }

         Instant totalStart = Instant.now();

        while (true) {
            String word = getData();

            if (word != Filter.POISON_PILL) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    if (!stopWords.contains(word)) {
                        sendData(word);
                    }
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        
        System.out.println("Stop word filtering has been completed.");
    }
}