package pl.edu.agh.kis.pz1;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer extends Thread { // seems to work well
    private final Logger LOGGER;
    private final Object writeLock;
    private final ReadingRoom readingRoom;
    private static boolean cannotEnter = false;
    // replace System.out with shorter out name
    PrintStream out = System.out;


    public Writer (Object writeLock, ReadingRoom rr) {
        this.LOGGER = Logger.getLogger(Thread.currentThread().getName());
        this.writeLock = writeLock;
        this.readingRoom = rr;
    }

    @Override
    public void run() {
        while(true){
            write();
        }
    }

    public void write(){
        out.println(Thread.currentThread().getName() + " wants to write.");
        synchronized (writeLock){
            try {
                readingRoom.waitingWriteCount.getAndIncrement();
                boolean tmp = true;
                if(!cannotEnter){
                    tmp = false;
                }
                do{
                    writeLock.wait();
                }while(tmp);
                readingRoom.waitingWriteCount.getAndDecrement();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.WARNING, "Interrupted exception occurred.", e);
            }
            writeStart();
            writeEnd();
        }
    }

    public void writeStart (){
        out.println(Thread.currentThread().getName() + " has started writing.");
        // writing time
        threadSleep(1500);
    }

    public void writeEnd(){
        readingRoom.writeCount.getAndDecrement();
        out.println(Thread.currentThread().getName() + " has stopped writing.\n");
        // sleep after writing
        threadSleep(8000);
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
