import hsa.Console;
import java.awt.*;

public class SplashScreen extends Thread
{
    Console c;

    public SplashScreen (Console con)
    {
	c = con;
    }


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


    private void drawFlag (int x, int y)
    {
	c.setColor (Color.black);
	for (int i = 0 ; i <= 5 ; i++)
	    c.drawLine (x + i, y, x + i, y + 25);

	c.fillRect (x, y, 5, 20);

	c.setColor (Color.red);

	for (int i = 5 ; i < 20 ; i++)
	    c.drawLine (x + 5, y + 10, x + i, y);
    }


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

	    if (Math.abs (i - 2) < 0.0001)
		drawFlag (600, 200);

	    if (Math.abs (i - 127) < 0.0001)
		drawFlag (400, 400);

	    delay (1);
	}



	c.setColor (Color.orange);
	for (int i = 0 ; i < 15 ; i++)
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2);

	delay (500);

	c.setColor (Color.red);
	for (int i = 0 ; i < 15 ; i++)
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2);

	delay (500);

	for (int i = 15 ; i < 800 ; i++)
	{
	    c.drawOval (410 - i, 100 - i, i * 2, i * 2);
	    delay (1);
	}

	c.setColor (Color.white);
	for (int y = 0 ; y < 650 ; y++)
	    c.drawLine (0, y, 910, y);

	c.setFont (new Font ("Comic Sans MS", 0, 50));
	for (int x = -500 ; x < 250 ; x++)
	{
	    c.setColor (Color.white);
	    c.drawString ("MINESWEEPER", x - 1, 300);

	    c.setColor (Color.black);
	    c.drawString ("MINESWEEPER", x, 300);
	    delay (2);
	}

	delay (1000);
    }
}
