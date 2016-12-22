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
	for (int i = 0 ; i < 10 ; i++)
	    c.drawLine (x + i, y, x + i, y + 20);

	c.fillRect (x, y, 5, 20);

	c.setColor (Color.red);
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

	for (double i = -89 ; i < 270 ; i += 0.1)
	{
	    angle = i * Math.PI / 180;
	    prevAngle = (i - 10) * Math.PI / 180;

	    c.setColor (Color.white);
	    c.drawLine (450, 300, (int) (450 + 250 * Math.cos (prevAngle)), (int) (300 + 250 * Math.sin (prevAngle)));
	    c.setColor (Color.green);
	    c.drawLine (450, 300, (int) (450 + 250 * Math.cos (angle)), (int) (300 + 250 * Math.sin (angle)));
	    delay (1);

	}

	drawFlag (100, 200);
	delay(1000);

    }
}
