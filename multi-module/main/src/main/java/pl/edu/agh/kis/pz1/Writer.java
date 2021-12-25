package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.logging.Logger;

public class Writer extends Thread {
    private final Logger LOGGER;
    PipedOutputStream out;
    private final Object writeLock;

    public Writer (PipedOutputStream o, Object writeLock) {
        this.out = o;
        this.LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.writeLock = writeLock;
    }

    @Override
    public void run() {
        while(true){
            write(this, LOGGER);
        }
    }



    public void write(Thread writer, Logger LOGGER){
        synchronized (writeLock){
            try {
                while(ReadingRoom.writeCount.get() >= 1 || ReadingRoom.readCount.get() >= 1) {}
                writeLock.notify();
                writeStart(writer, LOGGER);
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeEnd(writer, LOGGER);
        }
    }

    public void writeStart(Thread writer, Logger LOGGER){
        ReadingRoom.writeCount.getAndIncrement();
        System.out.println("Writers count:" + ReadingRoom.writeCount.get());
        System.out.println(Writer.currentThread().getName() + " has started writing.");
        try {
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeEnd(Thread writer, Logger LOGGER){
        synchronized (writeLock){
            ReadingRoom.writeCount.getAndDecrement();
            System.out.println(Writer.currentThread().getName() + " has stopped writing.\n");
            try {
                writeLock.wait();
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
