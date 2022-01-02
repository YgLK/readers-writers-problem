package pl.edu.agh.kis.pz1;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Class used for testing Reader
 * methods and functionalities.
 */
public class ReaderTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    ReadingRoom readingRoom;

    @Before
    public void init(){
        this.readingRoom = new ReadingRoom();
        // set up streams
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams(){
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldCreateWriter(){
        Reader reader = new Reader(readingRoom.readLock, readingRoom);
        assertNotNull("Writer instance is not properly created", reader);
    }

    @Test
    public void shouldSleepReturnTrue(){
        Reader reader = new Reader(readingRoom.readLock, readingRoom);
        boolean testBool = reader.threadSleep(1);
        assertTrue("threadSleep method should return true if run successfully", testBool);
    }

    @Test
    public void shouldPrintStartWritingMessage(){
        Reader reader = new Reader(readingRoom.readLock, readingRoom);
        reader.readStart();
        assertTrue("startWrite method should print specified message.",
                outContent.toString().contains(" has started reading."));
    }

    @Test
    public void shouldPrintEndWritingMessage(){
        Reader reader = new Reader(readingRoom.readLock, readingRoom);
        reader.readEnd();
        assertTrue("startWrite method should print specified message.",
                outContent.toString().contains(" has stopped reading."));
    }
}
