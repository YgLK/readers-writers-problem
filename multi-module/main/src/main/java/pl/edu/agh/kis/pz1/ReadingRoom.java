package pl.edu.agh.kis.pz1;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadingRoom {
    AtomicInteger writeCount;
    AtomicInteger readCount;
    AtomicInteger waitingWriteCount;
    final Object writeLock;
    final Object readLock;
    static String resource= "";

    public ReadingRoom(){
       writeLock = new Object();
       readLock = new Object();
       writeCount = new AtomicInteger(0);
       readCount = new AtomicInteger(0);
       waitingWriteCount = new AtomicInteger(0);
    }

    // notifies readers/writers when reading room is available and let them enter
    public void guard(){
        while(true){
            // writers condition
            if(this.writeCount.get() < 1
                    && this.readCount.get() < 1) {
                synchronized (this.writeLock){
                    // guard increases count of writers
                    this.writeCount.getAndIncrement();
                    // let writer enter
                    writeLock.notify();
                }
//                System.out.println("Writers count: " + this.writeCount);
            }
                // readers condition
            if(this.writeCount.get() < 1
                    && this.readCount.get() < 5
                    // if less than 2 writers waits for their turn let readers enter
                    && this.waitingWriteCount.get() <= 1
            ) {
                synchronized (this.readLock){
                    // guard increases count of readers
                    this.readCount.incrementAndGet();
                    // let reader enter the reading room
                    readLock.notify();
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
