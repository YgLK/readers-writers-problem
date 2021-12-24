package pl.edu.agh.kis.pz1;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Przykładowy kod do zajęć laboratoryjnych 2, 3, 4 z przedmiotu: Programowanie zaawansowane 1
 * @author Paweł Skrzyński
 */
public class Main {
    public static void main(String[] args) throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out);
        Writer writer1 = new Writer(out, ReadingRoom.resource);
        Writer writer2 = new Writer(out, ReadingRoom.resource);
        Writer writer3 = new Writer(out, ReadingRoom.resource);
        Reader reader1 = new Reader(in, ReadingRoom.resource);
        Reader reader2 = new Reader(in, ReadingRoom.resource);
        Reader reader3 = new Reader(in, ReadingRoom.resource);
        Reader reader4 = new Reader(in, ReadingRoom.resource);
        Reader reader5 = new Reader(in, ReadingRoom.resource);
        Reader reader6 = new Reader(in, ReadingRoom.resource);
        Reader reader7 = new Reader(in, ReadingRoom.resource);
        Reader reader8 = new Reader(in, ReadingRoom.resource);
        Reader reader9 = new Reader(in, ReadingRoom.resource);
        Reader reader10 = new Reader(in, ReadingRoom.resource);
        // run writers
        writer1.start();
        writer2.start();
        writer3.start();
        // run readers
//        reader1.start();
//        reader2.start();
//        reader3.start();
//        reader4.start();
//        reader5.start();
//        reader6.start();
//        reader7.start();
//        reader8.start();
//        reader9.start();
//        reader10.start();
    }
}
