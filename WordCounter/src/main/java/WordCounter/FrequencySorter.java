/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

import java.util.Comparator;

/**
 *
 * @author syafiqah-violet
 * 
 *  Comparator class for sorting words by their frequency, and then by name if there is a tie
 */
public class FrequencySorter implements Comparator<Pair> {
    
   @Override
    public int compare(Pair o1, Pair o2) {
        int compareVal =  o2.getFrequency().compareTo(o1.getFrequency());
        if (compareVal != 0){
            return compareVal;
        }
        return o1.getWord().compareTo(o2.getWord());
    }
    
}
