package pl.edu.agh.kis.pz1;

import java.io.PipedOutputStream;
import java.util.logging.Logger;

public class Writer extends Thread { // seems to work well
    private final Logger LOGGER;
    private final Object writeLock;
    private final ReadingRoom readingRoom;

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
        System.out.println(Thread.currentThread().getName() + " wants to write.");
        synchronized (writeLock){
            try {
                readingRoom.waitingWriteCount.getAndIncrement();
                writeLock.wait();
                readingRoom.waitingWriteCount.getAndDecrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeStart();
            writeEnd();
        }
    }

    public void writeStart(){
        System.out.println(Thread.currentThread().getName() + " has started writing.");
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeEnd(){
        readingRoom.writeCount.getAndDecrement();
        System.out.println(Thread.currentThread().getName() + " has stopped writing.\n");
        try {
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
