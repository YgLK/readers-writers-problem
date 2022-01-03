package pl.edu.agh.kis.pz1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class representing the Reading Room
 * in the Readers-Writers Problem. It's
 * place where Readers and Writers come to read/write.
 *
 * Reading Room's maximum capacity is
 * 1 Writer or 5 Readers at a time.
 * Writers can't enter Reading Room when
 * Readers are inside, and similarly when
 * Writer is inside, no Readers can enter.
 */
public class ReadingRoom {
    /** how many writers are in the room */
    private AtomicInteger writeCount;
    /** how many readers are in the room */
    private AtomicInteger readCount;
    /** how many readers wait to enter the room */
    private AtomicInteger waitingReadCount;
    /** how many writers wait to enter the room */
    private AtomicInteger waitingWriteCount;
    /** allows access for write-only operation */
    private final Object writeLock;
    /** allows access for read-only operation */
    private final Object readLock;
    /** informs if Guard can start */
    private boolean startTheGuard;
    /** primitive resource */
    private String resource= "";

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
        waitingReadCount = new AtomicInteger(0);
        startTheGuard = true;
    }

    /**
     * The method symbolise Guard staying in front of the Reading Room
     * who prevents coming writers/readers from breaking the problem's rules.
     *
     */
    public void guard(){
        while(startTheGuard){
            readingRoomRulesValidation();
        }
    }

    /**
     * The method used by the Guard to let Readers or Writers
     * enter Reading Room on condition that there is available
     * space for them.
     *
     * Guard notifies readers/writers when reading room is available and lets them enter in.
     */
    public boolean readingRoomRulesValidation(){
        // check if writer can enter the room
        if(canWriterEnter()) {
            synchronized (this.writeLock){
                // guard increases count of writers
                this.writeCount.getAndIncrement();
                // let the writer enter by notifying a thread associated with write lock
                writeLock.notify();
            }
        }
        // check if reader can enter the room
        if(canReaderEnter()) {
            synchronized (this.readLock){
                // guard increases count of readers
                this.readCount.incrementAndGet();
                // let the reader enter by notifying a thread associated with read lock
                readLock.notify();
            }
        }
        return true;
    }

    /**
     * The method used for checking if Writer
     * can enter the Reading Room.
     *
     * Writer can enter Reading Room if:
     *  - there are no Writers currently
     *  in the Reading Room
     *      and
     *  - there are no Readers currently
     * in the Reading Room
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
     *  in the Reading Room
     *      and
     *  - there are less than 5 Readers currently
     * in the Reading Room
     *     and
     * - there are less than 3 writers waiting for
     * their turn (condition added to avoid writers'
     * starvation)
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
     * The method used for incrementing the actual
     * number of reading Readers.
     */
    public void incrementReadCount(){
        synchronized (this.readLock) {
            this.readCount.incrementAndGet();
        }
    }

    /** @return current number of Readers in the Reading Room */
    public int getReadCountValue(){
        return this.readCount.get();
    }

    /** @param value to set current number of Readers in the Reading Room */
    public void setReadCountValue(int value){
        this.readCount.set(value);
    }

    /** @return counter of Readers in the Reading Room */
    public AtomicInteger getReadCount(){
        return this.readCount;
    }

    /** @param value to set current number of Writers in the Reading Room */
    public void setWriteCountValue(int value){
        this.writeCount.set(value);
    }

    /** @return counter of Writers in the Reading Room */
    public AtomicInteger getWriteCount(){
        return this.writeCount;
    }

    /** @param value to set current number of Writers waiting to enter the Reading Room */
    public void setWaitingWriteCount(int value){
        this.waitingWriteCount.set(value);
    }

    /** @return counter of Writers waiting to enter the Reading Room */
    public AtomicInteger getWaitingWriteCount(){
        return this.waitingWriteCount;
    }

    /** @return counter of Writers waiting to enter the Reading Room */
    public AtomicInteger getWaitingReadCount(){
        return this.waitingReadCount;
    }

    /** @return writeLock connected with the current ReadingRoom */
    public Object getWriteLock() {
        return writeLock;
    }

    /** @return readLock connected with the current ReadingRoom */
    public Object getReadLock() {
        return readLock;
    }

    /** @return used problem resource  */
    public String getResource(){
        return resource;
    }
}
