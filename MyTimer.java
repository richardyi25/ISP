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
	    c.fillRect (620, 0, 300, 50);
	    c.setColor (Color.black);

	    Font f = new Font ("Comic Sans MS", 0, 30);
	    c.drawString ("Elapsed Time: " + seconds, 620, 30);
	}
    }
}
