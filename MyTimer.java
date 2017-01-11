/*
Dec. 12 2016
Richard Yi
Mrs. Krasteva
ISP (Minesweeper)

Utility class for timing and displaying elapsed time in the game
There are no screens (an instance is initialized and run in the game method of Minesweeper.java)

Global Variables

		    Name                Type                                 Description
===============================================================================================================
		     c               hsa.Console                 Console with user input/output
		  seconds               int     Keeps track of how many seconds has elapsed in the game so far
*/
import hsa.Console;
import java.awt.*;

public class MyTimer extends Thread
{
    Console c;
    int seconds;

    public MyTimer (Console con)
    {
	c = con;
    }


    /*
    Method that runs when Thread starts

    Control Flow
    ============

    Conditional Loop #1:
	Keeps looping forever (keeps counting)

	Exception Handler:
	    Sleeps current thread for 1 second
	    Since sleeping thread throws InterruptedException, catch it
    */
    public void run ()
    {
	seconds = 0;

	while (true)
	{
	    try
	    {
		sleep (1000);
	    }
	    catch (InterruptedException e)
	    {

	    }

	    ++seconds;

	    c.setColor (Color.white);
	    c.fillRect (620, 0, 300, 50); //erase
	    c.setColor (Color.black);

	    Font f = new Font ("Comic Sans MS", 0, 30);
	    c.drawString ("Elapsed Time: " + seconds, 620, 30); //draw
	}
    }
}
