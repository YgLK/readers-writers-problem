package pl.edu.agh.kis.pz1;

import java.io.PipedInputStream;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    private final Object readLock;
    private final ReadingRoom readingRoom;

    public Reader(Object readLock, ReadingRoom rr) {
        LOGGER = Logger.getLogger(Thread.currentThread().getName());
        this.readLock = readLock;
        this.readingRoom = rr;
    }

    @Override
    public void run() {
        while(true){
            read();
        }
    }

    public void read(){
        try {
            System.out.println(Thread.currentThread().getName() + " wants to read.");
            synchronized (readLock){
                readLock.wait();
            }
            readStart();
            readEnd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readStart(){
        System.out.println(Thread.currentThread().getName() + " has started reading.");
        try{
            sleep(3400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readEnd(){
            System.out.println(Thread.currentThread().getName() + " has stopped reading.");
            try {
                // rest after reading
                sleep(10000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            readingRoom.decrementReadCount();
    }
}
