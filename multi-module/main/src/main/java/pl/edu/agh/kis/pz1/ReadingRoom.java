package pl.edu.agh.kis.pz1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class representation of the Reading Room
 * in the Readers-Writers Problem. Represents
 * place where Readers and Writers come to read/write.
 *
 * Reading Room's maximum capacity is
 * 1 Writer or 5 Readers at a time.
 * Writers can't enter Reading Room when
 * Readers are inside, and similarly when
 * Writers are inside, no Readers can enter.
 */
public class ReadingRoom {
    /** how many writers are in the room */
    AtomicInteger writeCount;
    /** how many readers are in the room */
    AtomicInteger readCount;
    /** how many writers wait to enter the room */
    AtomicInteger waitingWriteCount;
    /** allows access for write-only operation*/
    final Object writeLock;
    /** allows access for read-only operation*/
    final Object readLock;
    /** informs if Guard can start */
    boolean startTheGuard;
    /** primitive resource */
    static String resource= "";

    /**
     * Reading Room constructor.
     * Initializes writing lock, reading lock
     * and counters.
     *
     */
    public ReadingRoom(){
       writeLock = new Object();
       readLock = new Object();
       writeCount = new AtomicInteger(0);
       readCount = new AtomicInteger(0);
       waitingWriteCount = new AtomicInteger(0);
       startTheGuard = true;
    }

    /**
     * The method symbolise Guard staying in front of the Reading Room.
     * He let Readers or Writers to enter Reading Room on condition that
     * there is available space for them.
     *
     * Guard notifies readers/writers when reading room is available and let them enter.
     *
     */
    public void guard(){
        while(startTheGuard){
            // check if writer can enter the room
            if(canWriterEnter()) {
                synchronized (this.writeLock){
                    // guard increases count of writers
                    this.writeCount.getAndIncrement();
                    // let the writer enter by notifying thread associated with write lock
                    // exclude it from SONAR, because notifying single thread is on purpose in this particular case
                    writeLock.notify(); // NOSONAR
                }
            }
            // check if reader can enter the room
            if(canReaderEnter()) {
                synchronized (this.readLock){
                    // guard increases count of readers
                    this.readCount.incrementAndGet();
                    // let the reader enter by notifying thread associated with read lock
                    // exclude it from SONAR, because notifying single thread is on purpose in this particular case
                    readLock.notify(); // NOSONAR
                }
            }
        }
    }

    /**
     * The method used for checking if Writer
     * can enter the Reading Room.
     *
     * Writer can enter Reading Room if:
     *  - there are no Writers currently
     *      and
     *  - there are no Readers currently
     *
     * @return boolean
     *          true:  Writer can enter Reading Room
     *          false: Writer can't enter Reading Room
     */
    public boolean canWriterEnter(){
        return this.writeCount.get() < 1
                && this.readCount.get() < 1;
    }

    /**
     * The method used for checking if Reader
     * can enter the Reading Room.
     *
     * Reader can enter Reading Room if:
     *  - there are no Writers currently
     *      and
     *  - there are less than 5 Readers currently
     *
     * @return boolean
     *          true:  Reader can enter Reading Room
     *          false: Reader can't enter Reading Room
     */
    public boolean canReaderEnter(){
        return this.writeCount.get() < 1
                && this.readCount.get() < 5
                // if less than 3 writers waits for their turn let readers enter
                && this.waitingWriteCount.get() <= 2;
    }

    /**
     * The method used for decrementing the actual
     * number of reading Readers.
     */
    public void decrementReadCount(){
        synchronized (this.readLock) {
            this.readCount.decrementAndGet();
        }
    }

    /**
     * The method used for decrementing the actual
     * number of reading Readers.
     */
    public void incrementReadCount(){
        synchronized (this.readLock) {
            this.readCount.incrementAndGet();
        }
    }

    /**
     * @return current number of Readers in the Reading Room
     */
    public int getReadCountValue(){
        return this.readCount.get();
    }

    /**
     * @param value to set current number of Readers in the Reading Room
     */
    public void setReadCountValue(int value){
        this.readCount.set(value);
    }

    /**
     * @param value to set current number of Writers in the Reading Room
     */
    public void setWriteCountValue(int value){
        this.writeCount.set(value);
    }

    /**
     * @param value to set current number of Writers waiting to enter the Reading Room
     */
    public void setWaitingWriteCount(int value){
        this.waitingWriteCount.set(value);
    }

}
