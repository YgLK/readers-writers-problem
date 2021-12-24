package pl.edu.agh.kis.pz1;

import java.io.PipedInputStream;
import java.util.logging.Logger;

public class Reader extends Thread{
    private final Logger LOGGER;
    PipedInputStream in;
    final Object obj;

    public Reader(PipedInputStream i, Object obj) {
        in = i;
        LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.obj = obj;
    }

    @Override
    public void run() {
        while(true){
            //                int c = in.read();
            //                System.out.println(Writer.currentThread().getName() + " has started reading.");
//                sleep(200*10);
//                System.out.println(Writer.currentThread().getName() + " has stopped reading.");

//            ReadingRoom.read(this, LOGGER);
            read(this, LOGGER);
        }
    }

    public void read(Thread reader, Logger LOGGER){
        readStart(reader, LOGGER);
        readEnd(reader, LOGGER);
    }

    public void readStart(Thread reader, Logger LOGGER){
        synchronized (obj){
            try {
    //            System.out.println("Readers: " + readersCount);
    //            while(writersCount > 0 || readersCount >= 5) {
                while(ReadingRoom.writeCount.get() > 0 || ReadingRoom.readCount.get() >= 5) {
//                    obj.wait();
                }
//                obj.notify();
                //            readersCount++;
                ReadingRoom.readCount.getAndIncrement();
                System.out.println("Readers count:" + ReadingRoom.readCount.get());
                System.out.println(Writer.currentThread().getName() + " has started reading.");
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void readEnd(Thread reader, Logger LOGGER){
        synchronized (obj) {
            // sleep after reading
            try {
                obj.notify();
//                obj.wait();
                System.out.println(Writer.currentThread().getName() + " has stopped reading.");
                ReadingRoom.readCount.getAndDecrement();
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //        readersCount--;
        }
    }
}
