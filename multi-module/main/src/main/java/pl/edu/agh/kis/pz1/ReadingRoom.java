package pl.edu.agh.kis.pz1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ReadingRoom {
    volatile static int writersCount = 0;
    static AtomicInteger writeCount = new AtomicInteger(0);
    volatile static int readersCount = 0;
    static AtomicInteger readCount = new AtomicInteger(0);
    static String resource= "";


    private ReadingRoom(){}


    public static void read(Thread reader, Logger LOGGER){
        readStart(reader, LOGGER);
        readEnd(reader, LOGGER);
    }

    public static synchronized void readStart(Thread reader, Logger LOGGER){
        try {
//            System.out.println("Readers: " + readersCount);
//            while(writersCount > 0 || readersCount >= 5) {
            while(writeCount.get() > 0 || readCount.get() >= 5) {
                    reader.wait();
            }
//            readersCount++;
            readCount.getAndIncrement();
            System.out.println(Writer.currentThread().getName() + " has started reading.");
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void readEnd(Thread reader, Logger LOGGER){
//        readersCount--;
        readCount.getAndDecrement();
        System.out.println(Writer.currentThread().getName() + " has stopped reading.");
        reader.notify();
        // sleep after reading
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void write(Thread writer, Logger LOGGER){
//        System.out.println("Writers: " + readersCount);
        try {
            while(writersCount > 0 || readersCount > 0) {
                writer.wait();
            }
            writeStart(writer, LOGGER);
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeEnd(writer, LOGGER);
    }

    public static synchronized void writeStart(Thread writer, Logger LOGGER){
//        writersCount++;
        writeCount.getAndIncrement();
        System.out.println(Writer.currentThread().getName() + " has started writing.");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void writeEnd(Thread writer, Logger LOGGER){
//        writersCount--;
        writeCount.getAndDecrement();
        System.out.println(Writer.currentThread().getName() + " has stopped writing.");
//        synchronized (writer){
            // notify more than one thread (even 5 readers are allowed)
//            writer.notify();
//        }
        // sleep after reading
        try {
            synchronized (writer){
                writer.wait();
            }
            writer.notify();
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkIfReadingRoomAvailable(){
        return writersCount == 0 && readersCount < 1;
    }









//    public static void read(Thread reader, Logger LOGGER){
//        try {
//            if(checkIfReadingRoomAvailable()){
//                readersCount++;
//                LOGGER.info(Writer.currentThread().getName() + " has started reading.");
//                sleep(200*10);
//                LOGGER.info(Writer.currentThread().getName() + " has stopped reading.");
//                readersCount--;
//            }else{
//                reader.wait();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void write(Thread writer, Logger LOGGER){
//        try{
//            if(checkIfReadingRoomAvailable()){
//                System.out.println("Writers: " + writersCount);
//                writersCount++;
//                LOGGER.info(Writer.currentThread().getName() + " has started writing.");
//                sleep(200*10);
//                LOGGER.info(Writer.currentThread().getName() + " has stopped writing.");
//                writersCount--;
//            } else{
//                writer.wait();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}
