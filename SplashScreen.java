import hsa.Console;

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


    public void run ()
    {
	delay (1000);
    }
}
