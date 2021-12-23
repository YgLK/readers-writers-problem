package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.logging.Logger;

public class Writer extends Thread {
    private final Logger LOGGER;
    PipedOutputStream out;

    public Writer (PipedOutputStream o) {
        out = o;
        LOGGER = Logger.getLogger(Writer.currentThread().getName());
    }

    @Override
    public void run() {
//        int i = 0;
        while(true){
//            i += 1;
            //                System.out.println(Writer.currentThread().getName() + " has started writing.");
//                sleep(200 * 10);
//                System.out.println(Writer.currentThread().getName() + " has finished writing.");
            //                out.write(i);
            ReadingRoom.write(this, LOGGER);
        }
    }
}
