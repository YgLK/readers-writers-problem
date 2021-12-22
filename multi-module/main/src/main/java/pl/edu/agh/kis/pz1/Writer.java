package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedOutputStream;

public class Writer extends Thread {
    PipedOutputStream out;
    public Writer (PipedOutputStream o) {
        out = o;
    }

    @Override
    public void run() {
        int i = 0;
        while(true){
            System.out.println("Produced " + i);
            i += 1;
            try {
                out.write(i);
                sleep(200);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
