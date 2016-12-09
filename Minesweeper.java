import hsa.*;
import java.awt.*;
import java.io.*;

public class Minesweeper
{
    Console c;
    final int SIZE = 25;
    boolean mines[] [] = new boolean [25] [25];
    int adj[] [] = new int [25] [25];
    int grid[] [] = new int [25] [25];
    int size;
    char menuChoice;


    public Minesweeper ()
    {
	//A character is 8 pixels wide and 20 pixels tall
	//Window is approx. 500 by 800 pixels
	c = new Console (27, 101, "Minesweeper");
    }


    public void mainMenu ()
    {
	c.clear ();

	c.setFont (new Font ("Comic Sans MS", 0, 40));
	c.drawString ("Minesweeper", 285, 50);

	c.setFont (new Font ("Comic Sans MS", 0, 20));

	c.drawString ("Press 1 to play easy mode", 10, 100);
	c.drawString ("Press 1 to play easy mode", 10, 150);
	c.drawString ("Press 1 to play easy mode", 10, 200);
	c.drawString ("Press 1 to play easy mode", 10, 250);
	c.drawString ("Press 1 to play easy mode", 10, 300);
	c.drawString ("Press 1 to play easy mode", 10, 350);
    }


    public void splashScreen () throws InterruptedException
    {
	SplashScreen s = new SplashScreen (c);

	s.start ();


	public static void main (String[] args) throws IOException
	{
	    Minesweeper m = new Minesweeper ();


	    m.mainMenu ();
	    /*
		    while (true)
		    {
			m.mainMenu ();

			if (m.menuChoice == '1' || m.menuChoice == '2' || m.menuChoice == '3')
			    m.game ();
			else if (m.menuChoice == 'Q' || m.menuChoice == 'q')
			    m.instructions ();
			else if (m.menuChoice == 'W' || m.menuChoice == 'w')
			    m.highScores ();
			else if (m.menuChoice == 'E' || m.menuChoice == 'e')
			    break;
		    }
	    */

	    //m.goodbye ();
	}
    }
}
