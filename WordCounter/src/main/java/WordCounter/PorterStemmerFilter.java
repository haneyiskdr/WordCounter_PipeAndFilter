package WordCounter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed to change words into their base forms ie. remove all tense changes
 * Utilizes the Stemmer.java class
 */
public class PorterStemmerFilter extends Filter implements Runnable {

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public PorterStemmerFilter(Pipe in, Pipe out) {
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
        if (times.size() == 0) {
            return 0;
        } else {
            return (sum / times.size()) / 100;
        }
    }

    @Override
    public void run() {
        Instant totalStart = Instant.now();

        while (true) {

            String word = getData();

            if (word == null ? Filter.POISON_PILL != null : !word.equals(Filter.POISON_PILL)) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    Stemmer stemmer = new Stemmer();
                    for (int i = 0; i < word.length(); i++){
                        stemmer.add(word.charAt(i));
                    }
                    stemmer.stem();
                    sendData(stemmer.toString());
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("PorterStemmerFilter finished");
    }
}
