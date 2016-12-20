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


    public void run ()
    {
	Font f = new Font ("Arial", 0, 30);
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
	    c.fillRect (650, 150, 150, 150);
	    c.setColor (Color.black);
	    c.drawString (String.valueOf (seconds), 700, 200);
	}
    }
}
