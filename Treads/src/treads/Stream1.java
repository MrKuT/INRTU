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
class Stream1 extends Thread {

    Streams streams;

    Stream1(Streams streams) {
        this.streams = streams;
    }

    @Override
    public void run() {        
        for (int i = 0; i < 100; i++) {
            streams.putInBuf1();            
        }        
    }

}
