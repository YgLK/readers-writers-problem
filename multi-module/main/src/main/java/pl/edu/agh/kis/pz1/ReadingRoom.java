package pl.edu.agh.kis.pz1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ReadingRoom {
//    volatile static int writersCount = 0;
    AtomicInteger writeCount = new AtomicInteger(0);
//    volatile static int readersCount = 0;
    AtomicInteger readCount = new AtomicInteger(0);
    Integer readersCount = 0;
    AtomicInteger waitingWriteCount = new AtomicInteger(0);
    final Object writeLock;
    final Object readLock;
    static String resource= "";

    public ReadingRoom(){
       writeLock = new Object();
       readLock = new Object();
       writeCount = new AtomicInteger(0);
       readCount = new AtomicInteger(0);
       readersCount = 0;
       waitingWriteCount = new AtomicInteger(0);
    }

    // notifies readers/writers when reading room is available
    public void guard(){
        while(true){
            // writers condition
            if(this.writeCount.get() < 1 && this.readCount.get() < 1) {
                // guard increases count of the writers
                synchronized (this.writeLock){
                    this.writeCount.getAndIncrement();
                    // let writer enter
                    writeLock.notify();
                }
                System.out.println("Writers count: " + this.writeCount);
            }
//            synchronized (readLock){
                // readers condition
            if(this.writeCount.get() < 1
                && this.readCount.get() < 5
//                        && ReadingRoom.waitingWriteCount.getAndDecrement() == 0
            ) {
                synchronized (this.readLock){
//                    this.readCount.incrementAndGet();
//                    readersCount++;
                    // guard increases count of readers
                    // let reader enter
                    readLock.notify();
                    System.out.println("Readers count: " + this.readCount);
                }
            }
        }
    }


    public void decrementReadCount(){
        synchronized (this.readLock) {
            this.readCount.decrementAndGet();
        }
    }

    public void incrementReadCount(){
        synchronized (this.readLock) {
            this.readCount.incrementAndGet();
        }
    }
}
