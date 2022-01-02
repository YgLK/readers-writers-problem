package pl.edu.agh.kis.pz1;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class representing the Reader
 * in the Readers-Writers Problem.
 *
 */
public class Reader extends Thread{
    /** announces logs */
    private final Logger logger;
    /** allows access for read-only operation*/
    private final Object readLock;
    /** Reader is going to read there */
    private final ReadingRoom readingRoom;
    private static boolean cannotEnter = false;
    /** replacement of System.out with shorter name */
    private final PrintStream out = System.out;

    /**
     * Reader constructor. Creates reader
     * connected to readLock which will notify
     * him when Reading Room is available.
     *
     * @param readLock Lock monitoring waiting/reading
     *                 Readers
     * @param readingRoom Reading Room in which Reader is going to read
     */
    public Reader(Object readLock, ReadingRoom readingRoom) {
        logger = Logger.getLogger(Thread.currentThread().getName());
        this.readLock = readLock;
        this.readingRoom = readingRoom;
    }

    /**
     * Overridden run method in which Reader
     * continuously tries to read/reads in the
     * Reading Room.
     *
     */
    @Override
    public void run() {
        while(true){
            read();
        }
    }

    /**
     * The method used by the Reader to read. Reader
     * waits for the notification from the Reading Room.
     * If notified, he enters the Reading Room and
     * starts the reading session there.
     *
     */
    public void read(){
        try {
            out.println(Thread.currentThread().getName() + " wants to read.");
            synchronized (readLock){
                boolean tmp = true;
                do{
                    // if READER can enter change tmp value
                    if(!cannotEnter){
                    tmp = false;
                    }
                    /* wait for the notification that entering
                     Reading Room is available */
                    readLock.wait();
                }while(tmp);
            }
            // reading session
            readStart();
            readEnd();
        } catch (InterruptedException e) {
            // interrupt current thread if Interrupted Exception occurred
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Interrupted exception occurred.", e);
        }
    }

    /**
     * The method symbolises the beginning of the reading
     * session. In the method Reader is reading for a specified
     * amount of time.
     *
     */
    public void readStart(){
        // inform that reader started reading
        out.println(Thread.currentThread().getName() + " started reading.");
        // reading time
        threadSleep(1133);
    }

    /**
     * The method symbolises the end of the reading
     * session. In the method reader is sleeping for a while
     * in order to rest before the next reading session.
     *
     */
    public void readEnd(){
            // inform that reader stopped reading
            out.println(Thread.currentThread().getName() + " stopped reading.");
            // rest after reading
            threadSleep(1500);
            readingRoom.decrementReadCount();
    }

    /**
     * The method used for putting Reader thread
     * to sleep for specified amount of time.
     *
     * @param sleepLength length of sleep
     *
     * @return true if sleep is complete successfully
     */
    public boolean threadSleep(int sleepLength){
        try {
            sleep(sleepLength);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Interrupted exception occurred.", e);
        }
        return true;
    }
}
