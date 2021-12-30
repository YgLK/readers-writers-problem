package pl.edu.agh.kis.pz1;

import java.io.PipedOutputStream;
import java.util.logging.Logger;

public class Writer extends Thread { // seems to work well
    private final Logger LOGGER;
    PipedOutputStream out;
    private final Object writeLock;
    private final ReadingRoom readingRoom;

    public Writer (PipedOutputStream o, Object writeLock, ReadingRoom rr) {
        this.out = o;
        this.LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.writeLock = writeLock;
        this.readingRoom = rr;
    }

    @Override
    public void run() {
        while(true){
            write(this, LOGGER);
        }
    }

    public void write(Thread writer, Logger LOGGER){
        System.out.println(Writer.currentThread().getName() + " wants to write.");
        synchronized (writeLock){
            try {
                readingRoom.waitingWriteCount.getAndIncrement();
                writeLock.wait();
                readingRoom.waitingWriteCount.getAndDecrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeStart(writer, LOGGER);
            writeEnd(writer, LOGGER);
        }
    }

    public void writeStart(Thread writer, Logger LOGGER){
        System.out.println(Writer.currentThread().getName() + " has started writing.");
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeEnd(Thread writer, Logger LOGGER){
        readingRoom.writeCount.getAndDecrement();
        System.out.println(Writer.currentThread().getName() + " has stopped writing.\n");
        try {
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
