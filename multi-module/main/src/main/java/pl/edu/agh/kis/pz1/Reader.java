package pl.edu.agh.kis.pz1;

import java.io.PipedInputStream;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    PipedInputStream in;
    private final Object readLock;


    public Reader(PipedInputStream i, Object readLock) {
        in = i;
        LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.readLock = readLock;
    }

    @Override
    public void run() {
        while(true){
            read(this, LOGGER);
        }
    }

    public void read(Thread reader, Logger LOGGER){
        try {
            synchronized (readLock){
                while(ReadingRoom.writeCount.get() > 0 ||
                        ReadingRoom.readCount.get() >= 5) {
//                    System.out.println("Reader count v0: " + ReadingRoom.readCount.get());
                }
                readLock.notify();
            }
            synchronized (readLock){
                if(ReadingRoom.readCount.get() != 0){
                    readLock.notify();
                }
            }
            readStart(reader, LOGGER);
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readEnd(reader, LOGGER);
    }

    public void readStart(Thread reader, Logger LOGGER){
        ReadingRoom.readCount.getAndIncrement();
        System.out.println("Readers count:" + ReadingRoom.readCount.get());
        System.out.println(Writer.currentThread().getName() + " has started reading.");
        try{
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readEnd(Thread reader, Logger LOGGER){
            System.out.println(Writer.currentThread().getName() + " has stopped reading.");
            try {
                // sleep after reading
                ReadingRoom.readCount.getAndDecrement();
                synchronized (readLock) {
//                    readLock.notify();
                    readLock.wait();
                }
                System.out.println("Readers count: " + ReadingRoom.readCount);
                sleep(15000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
    }
}
