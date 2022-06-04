/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordCounter;

/**
 *
 * @author USER
 */
public class Filter {
    
    public static final String POISON_PILL = new String("Poison");

    Pipe inPipe;
    Pipe outPipe;

    public Filter(Pipe in, Pipe out){
        inPipe = in;
        outPipe = out;
    }

    public String getData(){
        return inPipe.out();
    }

    public void sendData( String toSend ){
        outPipe.in(toSend);
    }
    
}
