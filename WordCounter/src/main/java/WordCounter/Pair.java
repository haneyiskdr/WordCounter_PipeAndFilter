/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

/**
 *
 * @author syafiqah-violet
 * 
 * A utility class for determining frequencies of words
 */
public class Pair {
    
    private String word;
    private Integer frequency;

    public Pair(String word, Integer frequency){
        this.word = word;
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getWord() {
        return word;
    }   
}
