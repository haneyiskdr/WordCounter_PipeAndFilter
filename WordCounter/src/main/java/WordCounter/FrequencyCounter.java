/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author syafiqah-violet
 */
public class FrequencyCounter extends Filter implements Runnable{
    
    HashMap<String,Integer> frequencies = new HashMap();
    private List<Long> times = new ArrayList<>();
    private Long totalTime;
    private UserInterface UI;

    public FrequencyCounter(Pipe in, Pipe out) {
        super(in, out);
        this.UI = new UserInterface();
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

    private List<Pair> getTopFrequencies(){
        List<Pair> words = new ArrayList<>();
        for (String key : frequencies.keySet()){
            words.add(new Pair(key,frequencies.get(key)));
        }
        words.sort(new FrequencySorter());
        return words;
    }

    private void printOutResults(List<Pair> top){
        UI.output += "Top words for this text:\n\n";
        for (int i = 0; i < top.size(); i++){
            Pair candidate = top.get(i);
            UI.output += String.format("%d)   %s : %d",(i+1),candidate.getWord(),candidate.getFrequency()) + "\n";
            if (i == 9) {
                break;
            }
        }
    }

    @Override
    public void run() {
        Instant totalStart = Instant.now();

        //Add words to the frequency map
        while (true) {

            String word = getData();

            if (word != Filter.POISON_PILL) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    if (frequencies.containsKey(word)) {
                        frequencies.put(word, frequencies.get(word) + 1);
                    } else {
                        frequencies.put(word,1);
                    }
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();

        //Determine frequencies
        List<Pair> top = getTopFrequencies();
        if (top.size() == 0) {
            UI.output += "Please enter a text at least 10 words";
        } else {
            printOutResults(top);
        }
    }
}
