/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sitisarahaqilah
 */
public class FileReaderFilter extends Filter implements Runnable {
    
    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public FileReaderFilter(Pipe in, Pipe out) {
        super(in, out);
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public long getAvgTime(){
        long sum = 0;
        for (Long time: times){
            sum += time;
        }
        if (times.isEmpty()) {
            return 0;
        } else {
            return (sum / times.size()) / 100;
        }
    }

    @Override
    public void run() {
        Instant totalStart = Instant.now();

        while (true) {
            String filename = getData();

            if (!filename.equals(Filter.POISON_PILL)) {

                if (filename != null) {
                    Instant actionStart = Instant.now();
                    String in = null;

                    //Read in from a file, throw an error if the file cannot be read
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/java/WordCounter/TextFile/" + filename)));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sendData(line.toLowerCase());
                        }
                        Instant actionEnd = Instant.now();
                        times.add(Duration.between(actionStart,actionEnd).toMillis());
                    } catch (IOException fe) {
                        System.out.println("Unable to read from file: " + filename);
                        fe.printStackTrace();
                    }
                } else continue;
            } else break;

        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("FileReaderFilter finished");
    }
}
