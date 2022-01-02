package pl.edu.agh.kis.pz1;

import static junit.framework.TestCase.assertNotNull;
import org.junit.Test;

/**
 * Class used for testing Main
 * methods and functionalities.
 */
public class MainTest {


    /**
     * Test for the construction of Main and the 
     * main method being called
     *
     */
    @Test
    public void shouldCreateMainObject(){
        Main main = new Main();
        assertNotNull("Main method called.", main);
    }


//    @Test
//    public void shouldReadPrintStartEndMessages(){
//        Main.main(null);
//
//    }

}


