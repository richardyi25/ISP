import hsa.*;
import java.awt.*;
import java.io.*;

public class Minesweeper
{
    static Console c;

    public Minesweeper ()
    {
	//A character is 8 pixels wide and 20 pixels tall
	//Window is approx. 500 by 800 pixels
	c = new Console (27, 101, "Minesweeper");
    }


    public static void main (String[] args) throws IOException, InterruptedException
    {
	Minesweeper m = new Minesweeper ();

	SplashScreen s = new SplashScreen (c);
	s.start ();
	s.join ();

	c.print ("foo");
    }
}
