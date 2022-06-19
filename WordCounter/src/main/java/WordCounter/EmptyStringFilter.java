/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is to filter out empty strings from the words read from the file
 * Breaks the single string into separate words
 * Empty strings are not passed down the pipe
 */
public class EmptyStringFilter extends Filter implements Runnable {

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public EmptyStringFilter(Pipe in, Pipe out) {
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

    //Remove empty strings from the words read from the file
    @Override
    public void run() {
        Instant totalStart = Instant.now();

        while (true) {
            String unfiltered = getData();

            if (unfiltered != Filter.POISON_PILL) {
                if (unfiltered != null) {
                    Instant actionStart = Instant.now();
                    String[] rawStrings = unfiltered.split("\\s");
                    for (String string : rawStrings) {
                        if (!string.equals("")) {
                            sendData(string);
                        }
                    }
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("EmptyStringFilter finished");
    }

}
