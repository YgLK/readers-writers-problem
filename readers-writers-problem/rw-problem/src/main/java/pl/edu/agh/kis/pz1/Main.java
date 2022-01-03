package pl.edu.agh.kis.pz1;


/**
 * Main class in which the
 * Readers-Writers Problem is run.
 *
 * @author  Jakub Szpunar
 * @version 1.0
 * @since   2022-01-02
 */
public class Main {
    /**
     * Main method creates 3 writers
     * and 10 readers. Also, ReadingRoom
     * is created in which writers and readers
     * do their jobs.
     *
     * @param args
     */
    public static void main(String[] args){
        // create Reading Room
        ReadingRoom readingRoom = new ReadingRoom();
        // create 3 Writers
        Writer writer1 = new Writer(readingRoom.getWriteLock(), readingRoom);
        Writer writer2 = new Writer(readingRoom.getWriteLock(), readingRoom);
        Writer writer3 = new Writer(readingRoom.getWriteLock(), readingRoom);
        // create 10 Readers
        Reader reader1 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader2 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader3 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader4 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader5 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader6 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader7 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader8 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader9 = new Reader(readingRoom.getReadLock(), readingRoom);
        Reader reader10 = new Reader(readingRoom.getReadLock(), readingRoom);
        // run Writers
        writer1.start(); writer2.start(); writer3.start();
        // run Readers
        reader1.start(); reader2.start(); reader3.start(); reader4.start(); reader10.start();
        reader5.start(); reader6.start(); reader7.start(); reader8.start(); reader9.start();
        // invoke Guard which takes care of following the rules of the problem
        readingRoom.guard();
    }
}
