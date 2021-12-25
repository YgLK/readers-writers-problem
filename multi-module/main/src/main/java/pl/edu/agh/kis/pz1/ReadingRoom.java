package pl.edu.agh.kis.pz1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ReadingRoom {
//    volatile static int writersCount = 0;
    static AtomicInteger writeCount = new AtomicInteger(0);
//    volatile static int readersCount = 0;
    static AtomicInteger readCount = new AtomicInteger(0);
    static Object writeLock = new Object();
    static Object readLock = new Object();
    static String resource= "";


    private ReadingRoom(){}
}
