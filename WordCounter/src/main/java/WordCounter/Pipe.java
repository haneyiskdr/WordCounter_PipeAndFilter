/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author haneyiskdr
 */
public class Pipe {
    
    Queue<String> data = new LinkedList<>();

    public synchronized void in (String in){
        data.add(in);
    }

    public synchronized String out (){
        return data.poll();
    }
    
}
