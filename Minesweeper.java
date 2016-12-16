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

    int size, squareSize;
    int currentX, currentY;
    char menuChoice;

    public Minesweeper ()
    {
	//A character is 8 pixels wide and 20 pixels tall
	//Window is approx. 500 by 800 pixels
	c = new Console (27, 101, "Minesweeper");
    }


    private void title (String text, int x, int y)
    {
	c.clear ();
	c.setFont (new Font ("Comic Sans MS", 0, 40));
	c.drawString (text, x, y);
    }


    private void pauseProgram (int y)
    {
	c.setFont (new Font ("Comic Sans MS", 0, 30));
	c.drawString ("Press any key to continue...", 69, y);
	c.getChar ();
    }


    public void splashScreen () throws InterruptedException
    {
	SplashScreen s = new SplashScreen (c);
	s.start ();
    }


    public void mainMenu ()
    {
	title ("Minesweeper", 285, 50);

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
	title ("Instructions", 285, 50);

	pauseProgram (500);
    }


    public void highScores ()
    {
	title ("High Scores", 300, 50);
	pauseProgram (500);
    }


    public void goodbye ()
    {
	title ("Minesweeper", 285, 50);

	c.setFont (new Font ("Comic Sans MS", 0, 30));
	c.drawString ("Thank you for using my program", 69, 300);
	c.drawString ("Made by: Richard Yi", 69, 400);

	pauseProgram (500);
	c.close ();
    }


    private void result (boolean win)
    {
	title ("Results", 350, 50);

	if (win)
	    c.print ("you won lol");
	else
	    c.print ("wow git gud xD");

	pauseProgram (500);
    }


    private void render ()
    {
	c.clear ();
	for (int y = 0 ; y < size ; y++)
	{
	    for (int x = 0 ; x < size ; x++)
	    {

	    }
	}

	c.fillRect (currentX * 50, currentY * 50, 50, 50);
    }


    public void game ()
    {
	boolean exit = false;
	char input;
	render ();

	switch (menuChoice)
	{
	    case 1:
		break;
		//finish this later
	}

	while (!exit)
	{
	    input = c.getChar ();
	    switch (input)
	    {
		case 'W':
		case 'w':
		    --currentY;
		    break;
		case 'A':
		case 'a':
		    --currentX;
		    break;
		case 'S':
		case 's':
		    ++currentY;
		    break;
		case 'D':
		case 'd':
		    ++currentX;
		    break;
		case 'Q':
		case 'q':
		    exit = true;
	    }


	    render ();
	}

	result (false);
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
