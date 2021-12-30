package pl.edu.agh.kis.pz1;

import java.io.PipedInputStream;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    PipedInputStream in;
    private final Object readLock;
    private final ReadingRoom readingRoom;

    public Reader(PipedInputStream i, Object readLock, ReadingRoom rr) {
        in = i;
        LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.readLock = readLock;
        this.readingRoom = rr;
    }

    @Override
    public void run() {
        while(true){
            read(this, LOGGER);
        }
    }

    public void read(Thread reader, Logger LOGGER){
        try {
            System.out.println(Writer.currentThread().getName() + " wants to read.");
            synchronized (readLock){
                readLock.wait();
            }
//            readingRoom.incrementReadCount();
            readStart(reader, LOGGER);
            readEnd(reader, LOGGER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readStart(Thread reader, Logger LOGGER){
        System.out.println(Writer.currentThread().getName() + " has started reading.");
        try{
            sleep(3400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        readingRoom.decrementReadCount();
    }

//    public synchronized void readEnd(Thread reader, Logger LOGGER){
    public void readEnd(Thread reader, Logger LOGGER){
            System.out.println(Writer.currentThread().getName() + " has stopped reading.");
            try {
//                readingRoom.decrementReadCount();
                // sleep after reading
                sleep(10000);
//                --readingRoom.readersCount;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            readingRoom.decrementReadCount();
    }
}
