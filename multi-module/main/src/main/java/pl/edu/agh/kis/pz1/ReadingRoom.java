package pl.edu.agh.kis.pz1;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadingRoom {
    AtomicInteger writeCount;
    AtomicInteger readCount;
    AtomicInteger waitingWriteCount;
    final Object writeLock;
    final Object readLock;
    boolean startTheGuard;
    static String resource= "";

    public ReadingRoom(){
       writeLock = new Object();
       readLock = new Object();
       writeCount = new AtomicInteger(0);
       readCount = new AtomicInteger(0);
       waitingWriteCount = new AtomicInteger(0);
       startTheGuard = true;
    }

    // notifies readers/writers when reading room is available and let them enter
    public void guard(){
        while(startTheGuard){
            // writers condition
            if(canWriterEnter()) {
                synchronized (this.writeLock){
                    // guard increases count of writers
                    this.writeCount.getAndIncrement();
                    // let writer enter
                    writeLock.notify();
                }
            }
                // readers condition
            if(canReaderEnter()) {
                synchronized (this.readLock){
                    // guard increases count of readers
                    this.readCount.incrementAndGet();
                    // let reader enter the reading room
                    readLock.notify();
                }
            }
        }
    }

    public boolean canWriterEnter(){
        return this.writeCount.get() < 1
                && this.readCount.get() < 1;
    }

    public boolean canReaderEnter(){
        return this.writeCount.get() < 1
                && this.readCount.get() < 5
                // if less than 3 writers waits for their turn let readers enter
                && this.waitingWriteCount.get() <= 2;
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

    public int getReadCountValue(){
        return this.readCount.get();
    }


    public void setReadCountValue(int value){
        this.readCount.set(value);
    }


    public void setWriteCountValue(int value){
        this.writeCount.set(value);
    }

    public void setWaitingWriteCount(int value){
        this.waitingWriteCount.set(value);
    }

}
