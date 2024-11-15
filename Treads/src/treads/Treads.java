/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treads;

/**
 *
 * @author stud
 */
public class Treads {

    public static void main(String[] args) {
        Streams streams = new Streams();
        Stream1 stream1 = new Stream1(streams);
        Stream2 stream2 = new Stream2(streams);
        Stream3 stream3 = new Stream3(streams);        
        stream1.start();
        stream2.start();
        stream3.start();
        try {
            stream1.join();
            stream2.join();
            stream3.join();       
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
