package pl.edu.agh.kis.pz1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ReadingRoomTest{
    ReadingRoom readingRoom;

    @Before
    public void init(){
        readingRoom = new ReadingRoom();
    }

    @Test
    public void shouldCreateReadingRoom(){
        assertNotNull("Reading room instance is not properly created", readingRoom);
    }

    @Test
    public void shouldLetWriterEnter(){
        readingRoom.setWriteCountValue(0);
        readingRoom.setReadCountValue(0);
        assertTrue("Reader should be allowed to enter Reading Room", readingRoom.canWriterEnter());
    }

    @Test
    public void shouldNotLetWriterEnter(){
        readingRoom.setWriteCountValue(1);
        readingRoom.setReadCountValue(0);
        assertFalse("Writer should not be allowed to enter Reading Room", readingRoom.canWriterEnter());
    }

    @Test
    public void shouldLetReaderEnter(){
        readingRoom.setWriteCountValue(0);
        readingRoom.setReadCountValue(4);
        readingRoom.setWaitingWriteCount(2);
        assertTrue("Reader should be allowed to enter Reading Room", readingRoom.canReaderEnter());
    }

    @Test
    public void shouldNotLetReaderEnter(){
        readingRoom.setWriteCountValue(0);
        readingRoom.setReadCountValue(4);
        readingRoom.setWaitingWriteCount(3);
        assertFalse("Reader should not be allowed to enter Reading Room", readingRoom.canReaderEnter());
    }

    @Test
    public void testIncrementReadCount(){
        int beforeValue = readingRoom.getReadCountValue();
        readingRoom.incrementReadCount();
        assertEquals("ReadCount should increment",
                beforeValue + 1,
                readingRoom.getReadCountValue());
    }

    @Test
    public void testDecrementReadCount(){
        int beforeValue = readingRoom.getReadCountValue();
        readingRoom.decrementReadCount();
        assertEquals("ReadCount should increment",
                beforeValue - 1,
                readingRoom.getReadCountValue());
    }

    @Test
    public void testSetReadCountValue(){
        readingRoom.setReadCountValue(5);
        assertEquals("ReadCount should be set to 5",
                5,
                readingRoom.getReadCountValue());
    }
}