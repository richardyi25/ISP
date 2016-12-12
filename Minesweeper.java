/*
Dec. 12 2016
Richard Yi
Mrs. Krasteva
ISP (Minesweeper)

This program is a minesweeper game.
*/

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


    private void title (String text)
    {
	c.clear ();
    }


    private void pauseProgram (int y)
    {
	c.setFont (new Font ("Comic Sans MS", 0, 30));
	c.drawString ("Press any key to continue...", 50, y);
	c.getChar ();
    }


    public void splashScreen () throws InterruptedException
    {
	SplashScreen s = new SplashScreen (c);
	s.start ();
    }


    public void mainMenu ()
    {
	title ("Minesweeper");

	c.drawString ("Minesweeper", 285, 50);

	c.setFont (new Font ("Comic Sans MS", 0, 20));

	c.drawString ("Press 1 to play easy mode", 10, 100);
	c.drawString ("Press 2 to play medium mode", 10, 150);
	c.drawString ("Press 3 to play hard mode", 10, 200);
	c.drawString ("Press Q for help and instructions", 10, 250);
	c.drawString ("Press W to see high scores", 10, 300);
	c.drawString ("Press E to quit", 10, 350);
	//change to button format if you have time

	while (true)
	{
	    menuChoice = c.getChar ();

	    switch (menuChoice)
	    {
		case '1':
		case '2':
		case '3':
		case 'q':
		case 'w':
		case 'e':
		case 'Q':
		case 'W':
		case 'E':
		    return;
	    }
	}
    }


    public void instructions ()
    {
	c.clear ();

	//c.setFont (titleFont);
	c.drawString ("Instructions", 285, 50);

	c.getChar ();
    }


    public void highScores ()
    {

    }


    public void goodbye ()
    {
	c.setFont (new Font ("Comic Sans MS", 0, 30));
	c.drawString ("Thank you for using my program", 69, 300);
	c.drawString ("Made by: Richard Yi", 69, 400);

	pauseProgram (500);
	c.close ();
    }


    public void game ()
    {

    }


    public static void main (String[] args) throws IOException, InterruptedException
    {
	Minesweeper m = new Minesweeper ();
	m.splashScreen ();

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

	m.goodbye ();
    }
}

