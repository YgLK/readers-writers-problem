package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedInputStream;

public class Reader extends Thread{
    PipedInputStream in;
    public Reader(PipedInputStream i) {
        in = i;
    }

    @Override
    public void run() {
        while(true){
            try {
                    int c = in.read();
                    System.out.println("Consumer " +Thread.currentThread() +
                            " consumed " + c);
                    sleep(300);
            }
            catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
