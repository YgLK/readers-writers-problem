package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    PipedInputStream in;

    public Reader(PipedInputStream i) {
        in = i;
        LOGGER = Logger.getLogger(Writer.currentThread().getName());
    }

    @Override
    public void run() {
        while(true){
            //                int c = in.read();
            //                System.out.println(Writer.currentThread().getName() + " has started reading.");
//                sleep(200*10);
//                System.out.println(Writer.currentThread().getName() + " has stopped reading.");
            ReadingRoom.read(this, LOGGER);
        }
    }
}
