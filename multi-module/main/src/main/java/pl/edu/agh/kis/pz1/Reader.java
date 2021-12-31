package pl.edu.agh.kis.pz1;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    private final Object readLock;
    private final ReadingRoom readingRoom;
    private static boolean cannotEnter = false;
    // replace System.out with shorter out name
    PrintStream out = System.out;

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
            out.println(Thread.currentThread().getName() + " wants to read.");
            synchronized (readLock){
                boolean tmp = true;
                if(!cannotEnter){
                    tmp = false;
                }
                do{
                    readLock.wait();
                }while(tmp);
            }
            readStart();
            readEnd();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "Interrupted exception occurred.", e);
        }
    }

    public void readStart(){
        out.println(Thread.currentThread().getName() + " has started reading.");
        // reading time
        threadSleep(3400);
    }

    public void readEnd(){
            out.println(Thread.currentThread().getName() + " has stopped reading.");
            // rest after reading
            threadSleep(10000);
            readingRoom.decrementReadCount();
    }

    public boolean threadSleep(int sleepLength){
        try {
            sleep(sleepLength);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.log(Level.WARNING, "Interrupted exception occurred.", e);
        }
        return true;
    }
}
