package pl.edu.agh.kis.pz1;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Class used for testing Writer
 * methods and functionalities.
 */
public class WriterTest {
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
        Writer writer = new Writer(readingRoom.getWriteLock(), readingRoom);
        assertNotNull("Writer instance is not properly created", writer);
    }

    @Test
    public void shouldSleepReturnTrue(){
        Writer writer = new Writer(readingRoom.getWriteLock(), readingRoom);
        boolean testBool = writer.threadSleep(1);
        assertTrue("threadSleep method should return true if run successfully", testBool);
    }

    @Test
    public void shouldPrintStartWritingMessage(){
        Writer writer = new Writer(readingRoom.getWriteLock(), readingRoom);
        writer.writeStart();
        assertTrue("startWrite method should print specified message.",
                outContent.toString().contains(" has started writing."));
    }

    @Test
    public void shouldPrintEndWritingMessage(){
        Writer writer = new Writer(readingRoom.getWriteLock(), readingRoom);
        writer.writeEnd();
        assertTrue("startWrite method should print specified message.",
                outContent.toString().contains(" has stopped writing."));
    }
}
