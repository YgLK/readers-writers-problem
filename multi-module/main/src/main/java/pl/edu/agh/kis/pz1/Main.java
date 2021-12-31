package pl.edu.agh.kis.pz1;
import java.io.IOException;


/**
 * Przykładowy kod do zajęć laboratoryjnych 2, 3, 4 z przedmiotu: Programowanie zaawansowane 1
 * @author Paweł Skrzyński
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ReadingRoom readingRoom = new ReadingRoom();


        Writer writer1 = new Writer(readingRoom.writeLock, readingRoom);
        Writer writer2 = new Writer(readingRoom.writeLock, readingRoom);
        Writer writer3 = new Writer(readingRoom.writeLock, readingRoom);
        Reader reader1 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader2 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader3 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader4 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader5 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader6 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader7 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader8 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader9 = new Reader(readingRoom.readLock, readingRoom);
        Reader reader10 = new Reader(readingRoom.readLock, readingRoom);
        // run writers
        writer1.start();
        writer2.start();
        writer3.start();
        // run readers
        reader1.start();
        reader2.start();
        reader3.start();
        reader4.start();
        reader5.start();
        reader6.start();
        reader7.start();
        reader8.start();
        reader9.start();
        reader10.start();

        readingRoom.guard();
    }
}
