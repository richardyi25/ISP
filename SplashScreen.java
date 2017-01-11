/*
Dec. 12 2016
Richard Yi
Mrs. Krasteva
ISP (Minesweeper)

A splash screen for the main program.
There is a single screen which is the splash screen itself.

Global Variables

		Name                    Type                                   Description
=========================================================================================================
		 c                     Console                  A console for input/output with the user
*/
import hsa.Console;
import java.awt.*;

public class SplashScreen extends Thread
{
    Console c;

    //Constructor: Initialized the c variable with the Console passed into the it
    public SplashScreen (Console con)
    {
	c = con;
    }


    /*
    Delays the current thread by a number of milliseconds specified

    Control Flow
    ============
    Exception Handler:
	Since Thread.sleep throws an InterruptedException, catch it
    */
    public void delay (int ms)
    {
	try
	{
	    sleep (ms);
	}
	catch (InterruptedException e)
	{
	}
    }


    /*
    Draws a flag at a specified x and y coordinate pair.

	    Name                    Type                                   Description
    ============================================================================================
	     i                      int                                 Counts from 0 to 4
	     i                      int                                 Counts from 5 to 19

    Control Flow
    ============
    Counted Loop #1:
	Draw a black vertical line 5 times, making the pole

    Counted Loop #2:
	Draw a red diagonal line 15 times, making the flag

    */
    private void drawFlag (int x, int y)
    {
	c.setColor (Color.black);
	for (int i = 0 ; i <= 5 ; i++)
	    c.drawLine (x + i, y, x + i, y + 25);

	c.setColor (Color.red);

	for (int i = 5 ; i < 20 ; i++)
	    c.drawLine (x + 5, y + 10, x + i, y);
    }


    /*
	Method that is called when the thread is started
	Draws the animation to the screen

		Name                    Type                                   Description
	===============================================================================================================
	       angle                   double                           Current angle of the "radar"
	     prevAngle                 double       10 degrees counter-clockwise from the radar, the place to be erased
		i                      double                          Loops from -95 to -90.1 by 0.1
		i                      double                          Loops from -89 to 274.9 by 0.1
		i                       int                                Loops from 0 to 14
		i                       int                                Loops from 0 to 14
		i                       int                               Loops from 15 to 799
		y                       int                               Loops from 0 to 649
		x                       int                              Loops from -500 to 249

	Control Flow
	============
	Counted Loop #1:
	    Initlially draw the radar

	Counted Loop #2:
	    Makes the radar move 360 degrees, drawing flags at certain points

	Counted Loop #3:
	    Draws the mine in a loop

	Counted Loop #4:
	    Makes the mine red in a loop

	Counted Loop #5:
	    Draw the mine exploding

	Counted Loop #6:
	    Erases the screen

	Counted Loop #7:
	    Draws "MINESWEEPER" going right coming from the left



	*/
    public void run ()
    {
	double angle, prevAngle;

	for (double i = -95 ; i < -90 ; i += 0.1)
	{
	    angle = i * Math.PI / 180;
	    c.setColor (Color.green);
	    c.drawLine (450, 300, (int) (450 + 250 * Math.cos (angle)), (int) (300 + 250 * Math.sin (angle)));
	}

	for (double i = -89 ; i < 275 ; i += 0.1)
	{
	    angle = i * Math.PI / 180;
	    prevAngle = (i - 10) * Math.PI / 180;

	    c.setColor (Color.white);
	    c.drawLine (450, 300, (int) (450 + 250 * Math.cos (prevAngle)), (int) (300 + 250 * Math.sin (prevAngle)));
	    c.setColor (Color.green);
	    c.drawLine (450, 300, (int) (450 + 250 * Math.cos (angle)), (int) (300 + 250 * Math.sin (angle)));

	    if (Math.abs (i - 2) < 0.0001) //comparing i to 2 (Java has awful floating-point precision)
		drawFlag (600, 200);

	    if (Math.abs (i - 127) < 0.0001) //comparing i to 127 (Java has awful floating-point precision)
		drawFlag (400, 400);

	    delay (1);
	}



	c.setColor (Color.orange);
	for (int i = 0 ; i < 15 ; i++)
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2); //mine

	delay (500);

	c.setColor (Color.red);
	for (int i = 0 ; i < 15 ; i++)
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2); //mine turns red

	delay (500);

	for (int i = 15 ; i < 800 ; i++)
	{
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2);  //mine explodes
	    delay (1);
	}

	c.setColor (Color.white);
	for (int y = 0 ; y < 650 ; y++)
	    c.drawLine (0, y, 910, y); //erase

	c.setFont (new Font ("Comic Sans MS", 0, 50));
	for (int x = -500 ; x < 250 ; x++)
	{
	    c.setColor (Color.white);
	    c.drawString ("MINESWEEPER", x - 1, 300); //erase

	    c.setColor (Color.black);
	    c.drawString ("MINESWEEPER", x, 300); //draw
	    delay (2);
	}

	delay (1000);
    }
}
