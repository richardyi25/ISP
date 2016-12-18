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
    boolean isMine[] [] = new boolean [25] [25];
    int adj[] [] = new int [25] [25];
    int grid[] [] = new int [25] [25];

    int gridSize, squareSize, mines;
    int currentX, currentY;
    char menuChoice;

    public Minesweeper ()
    {
	//A character is 8 pixels wide and 20 pixels tall
	//Window is approx. 600 by 900 pixels
	c = new Console (32, 113, "Minesweeper");
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


    /*
    private void delay (int ms)
    {
	try
	{
	    Thread.sleep (ms);
	}
	catch (InterruptedException e)
	{
	}
    }
    */

    private void init ()
    {
	c.clear ();

	switch (menuChoice)
	{
	    case '1':
		gridSize = 10;
		squareSize = 60;
		mines = 20;
		break;
	    case '2':
		gridSize = 20;
		squareSize = 30;
		mines = 40;
		break;
	    case '3':
		gridSize = 30;
		squareSize = 20;
		mines = 60;
		break;
	}

	currentX = 0;
	currentY = 0;

	for (int i = 0 ; i < gridSize ; i++)
	    for (int j = 0 ; j < gridSize ; j++)
	    {
		c.setColor (Color.gray);
		c.fillRect (i * squareSize, j * squareSize, squareSize, squareSize);
		c.setColor (Color.black);
		c.drawRect (i * squareSize, j * squareSize, squareSize, squareSize);
		c.setColor (Color.white);
		c.drawRect (i * squareSize + 1, j * squareSize + 1, squareSize - 2, squareSize - 2);
	    }
    }


    private void moveSelected (int dx, int dy)
    {
	c.setColor (Color.black);
	c.drawRect (currentX * squareSize, currentY * squareSize, squareSize, squareSize);
	c.setColor (Color.white);
	c.drawRect (currentX * squareSize + 1, currentY * squareSize + 1, squareSize - 2, squareSize - 2);

	currentX += dx;
	currentY += dy;

	if (currentX < 0)
	    currentX = 0;
	else if (currentX >= gridSize)
	    currentX = gridSize - 1;
	if (currentY < 0)
	    currentY = 0;
	else if (currentY >= gridSize)
	    currentY = gridSize - 1;


	c.setColor (Color.red);
	c.drawRect (currentX * squareSize, currentY * squareSize, squareSize, squareSize);
	c.drawRect (currentX * squareSize + 1, currentY * squareSize + 1, squareSize - 2, squareSize - 2);


	c.setColor (Color.black);
    }


    private boolean click (boolean leftClick)
    {
	if (isMine [currentY] [currentX])
	{
	    return true;
	}
	return false;
    }


    private void generate ()
    {
	for (int y = 0 ; y < gridSize ; y++)
	{
	    for (int x = 0 ; x < gridSize ; x++)
	    {
		isMine [x] [y] = false;
		grid [x] [y] = 0;
	    }
	}

	int i = 0;
	int randomX, randomY;

	while (i < mines)
	{
	    randomX = (int) (Math.random () * gridSize);
	    randomY = (int) (Math.random () * gridSize);

	    if ((Math.abs (randomX - currentX) > 1 || Math.abs (randomY - currentY) > 1) && !isMine [randomY] [randomX])
	    {
		isMine [randomY] [randomX] = true;

		c.setColor (Color.red);
		c.fillRect (randomX * squareSize + 1, randomY * squareSize + 1, squareSize, squareSize);

		++i;
	    }
	}
    }


    private void showControls ()
    {
	Console d = new Console ("Controls");
	d.print ("These are the controls:");
	d.getChar ();
	d.close ();
    }


    public void game ()
    {
	boolean exit = false;
	boolean firstClick = true;

	char input;

	init ();

	while (!exit)
	{
	    input = c.getChar ();
	    switch (input)
	    {
		case '?':
		case 'H':
		case 'h':
		    showControls ();
		case 'W':
		case 'w':
		    moveSelected (0, -1);
		    break;
		case 'A':
		case 'a':
		    moveSelected (-1, 0);
		    break;
		case 'S':
		case 's':
		    moveSelected (0, 1);
		    break;
		case 'D':
		case 'd':
		    moveSelected (1, 0);
		    break;
		case 'Q':
		case 'q':
		    exit = true;
		    break;
		case 'O':
		case 'o':
		    if (firstClick)
			generate ();
		    firstClick = false;
		    if (click (true))
			exit = true;
		    break;
		case 'P':
		case 'p':
		    click (false);
		    break;
	    }
	}

	result (false);
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


