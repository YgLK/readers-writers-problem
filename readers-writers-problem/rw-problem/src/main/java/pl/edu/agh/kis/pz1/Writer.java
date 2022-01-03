package pl.edu.agh.kis.pz1;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class representation of the Writer
 * in the Readers-Writers Problem.
 *
 */
public class Writer extends Thread {
    /** announces logs */
    private final Logger logger;
    /** allows access for write-only operation */
    private final Object writeLock;
    /** Reader is going to read there */
    private final ReadingRoom readingRoom;
    private static boolean cannotEnter = false;
    /** replacement of System.out with shorter name */
    private final PrintStream out = System.out;


    /**
     * Writer constructor. Creates writer
     * connected with writeLock which will notify
     * him when Reading Room is available.
     *
     * @param writeLock Lock which monitors writers writing
     *                  and eager to write
     * @param readingRoom Reading Room in which Writer is going to read
     */
    public Writer (Object writeLock, ReadingRoom readingRoom) {
        this.logger = Logger.getLogger(Thread.currentThread().getName());
        this.writeLock = writeLock;
        this.readingRoom = readingRoom;
    }

    /**
     * Overridden run method in which Writer
     * continuously tries to write/writes in the
     * Reading Room.
     *
     */
    @Override
    public void run() {
        while(true){
            write();
        }
    }

    /**
     * The method used by the Writer to write. Writer
     * waits for the notification from the Reading Room.
     * If notified, he enters the Reading Room and
     * starts the writing session there.
     *
     */
    public void write(){
        out.println(Thread.currentThread().getName() + " wants to write.");
        synchronized (writeLock){
            try {
                // increment counter of waiting writers
                readingRoom.getWaitingWriteCount().getAndIncrement();
                boolean tmp = true;
                do{ // if WRITER can enter change tmp value
                    if(!cannotEnter){ tmp = false; }
                    /* wait for the notification that entering
                     Reading Room is available */
                    writeLock.wait();
                }while(tmp);
                // decrement counter of waiting writers after writer enters the Reading Room
                readingRoom.getWaitingWriteCount().getAndDecrement();
                // interrupt current thread if Interrupted Exception occurred
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); logger.log(Level.WARNING, "Interrupted exception occurred.", e); }
            // writing session
            writeStart(); writeEnd();
        }
    }

    /**
     * The method symbolises the beginning of the writing
     * session. In the method Writer is writing for a specified
     * amount of time.
     *
     */
    public void writeStart (){
        // inform that writer started writing
        out.println(
                Thread.currentThread().getName() +
                        " started writing.");
        // _role_: waiting/doing
        out.println(
                "Writers: " +
                        readingRoom.getWaitingWriteCount().get() +
                        "/" +
                        readingRoom.getWriteCount().get() +
                        " Readers: " +
                        readingRoom.getWaitingReadCount().get() +
                        "/" +
                        readingRoom.getReadCount().get());
        // writing time
        threadSleep((int)(1000 + (Math.random() * (3000 - 1000))));
    }

    /**
     * The method symbolises the end of the writing
     * session. In the method writer is sleeping for a while
     * in order to rest before the next writing session.
     *
     */
    public void writeEnd(){
        readingRoom.getWriteCount().getAndDecrement();
        // inform that writer stopped writing
        out.println(
                Thread.currentThread().getName() +
                " stopped writing.\n");
    }

    /**
     * The method used for putting Writer thread to
     * sleep for specified amount of time.
     *
     * @param sleepLength length of sleep
     *
     * @return true if sleep is complete successfully
     */
    public boolean threadSleep(int sleepLength){
        try {
            sleep(sleepLength);
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); logger.log( Level.WARNING, "Interrupted exception occurred.", e); }
        return true;
    }
}
