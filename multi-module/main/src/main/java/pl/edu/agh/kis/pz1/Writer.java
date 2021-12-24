package pl.edu.agh.kis.pz1;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.logging.Logger;

public class Writer extends Thread {
    private final Logger LOGGER;
    PipedOutputStream out;
     final Object obj;

    public Writer (PipedOutputStream o, Object obj) {
        this.out = o;
        this.LOGGER = Logger.getLogger(Writer.currentThread().getName());
        this.obj = obj;
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

//            ReadingRoom.write(this, LOGGER);
            write(this, LOGGER);
        }
    }



    public void write(Thread writer, Logger LOGGER){
//        System.out.println("Writers: " + readersCount);
        synchronized (obj){
            try {
                while(ReadingRoom.writeCount.get() >= 1 || ReadingRoom.readCount.get() >= 1) {
//                    writer.wait();
//                    obj.wait();
                }
                writeStart(writer, LOGGER);
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeEnd(writer, LOGGER);
        }
    }

    public void writeStart(Thread writer, Logger LOGGER){
//        writersCount++;
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
        synchronized (obj){
            //        writersCount--;
            ReadingRoom.writeCount.getAndDecrement();
            System.out.println(Writer.currentThread().getName() + " has stopped writing.");
//        synchronized (writer){
            // notify more than one thread (even 5 readers are allowed)
//            writer.notify();
//        }
            // sleep after reading
            try {
                obj.notify();
                obj.wait();
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
