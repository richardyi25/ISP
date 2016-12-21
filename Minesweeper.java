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

    final int SIZE = 30;
    boolean isMine[] [] = new boolean [SIZE] [SIZE];
    int adj[] [] = new int [SIZE] [SIZE];
    boolean uncovered[] [] = new boolean [SIZE] [SIZE];
    boolean flagged[] [] = new boolean [SIZE] [SIZE];
    boolean vist[] [] = new boolean [SIZE] [SIZE]; //For DFS
    boolean cheating;

    int gridSize, squareSize, mines;
    int currentX, currentY;
    char menuChoice;

    public Minesweeper ()
    {
	//A character is 8 pixels wide and 20 pixels tall
	//Window is approx. 600 by 900 pixels
	c = new Console (31, 113, "Minesweeper");
    }


    private void title (String text, int x, int y)
    {
	c.clear ();
	c.setFont (new Font ("Comic Sans MS", 0, 40));
	c.setColor (Color.black);
	c.drawString (text, x, y);
    }


    private void pauseProgram (int y)
    {
	c.setFont (new Font ("Comic Sans MS", 0, 30));
	c.setColor (Color.black);
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
	c.setColor (Color.black);

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
	c.setColor (Color.black);

	c.drawString ("Thank you for using my program", 69, 300);
	c.drawString ("Made by: Richard Yi", 69, 400);

	pauseProgram (500);
	c.close ();
    }


    private void redrawCursor ()
    {
	c.setColor (Color.red);
	c.drawRect (currentX * squareSize, currentY * squareSize, squareSize, squareSize);
	c.drawRect (currentX * squareSize + 1, currentY * squareSize + 1, squareSize - 2, squareSize - 2);
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

	redrawCursor ();

	c.setColor (Color.black);
    }


    private void uncover (int x, int y)
    {
	uncovered [y] [x] = true;
	c.setColor (Color.lightGray);
	c.fillRect (x * squareSize + 1, y * squareSize + 1, squareSize - 2, squareSize - 2);
	c.setColor (Color.white);
	c.drawRect (x * squareSize + 1, y * squareSize + 1, squareSize - 2, squareSize - 2);
	redrawCursor ();

	c.setColor (Color.black);
	c.setFont (new Font ("Comic Sans MS", 0, 20));

	if (adj [y] [x] > 0)
	    c.drawString (String.valueOf (adj [y] [x]), x * squareSize + (squareSize - 20) / 2 + 5, y * squareSize + (squareSize - 20) / 2 + 20);
    }


    private void DFS (int x, int y)
    {
	vist [y] [x] = true;
	uncover (x, y);

	for (int i = -1 ; i <= 1 ; i++)
	    for (int j = -1 ; j <= 1 ; j++) //Check all cells adjacent (delta x and delta y ranging from -1 to 1)
		if (y + i >= 0 && y + i < gridSize && x + j >= 0 && x + j < gridSize && !isMine [y + i] [x + j] && !vist [y + i] [x + j])
		{
		    //If cell is within the grid and is not a mine, and hasn't been visited before
		    if (adj [y + i] [x + j] > 0)
			uncover (x + j, y + i); //If cell has some adjacent mines, uncover
		    else
			DFS (x + j, y + i); //If cell has no adjacent mines, recurse
		}
    }


    private boolean click ()
    {
	if (flagged [currentY] [currentX])
	    return false;
	if (isMine [currentY] [currentX])
	    return true;
	else if (uncovered [currentY] [currentX])
	    return false;
	else if (adj [currentY] [currentX] > 0)
	    uncover (currentX, currentY);
	else
	{
	    for (int y = 0 ; y < gridSize ; y++)
		for (int x = 0 ; x < gridSize ; x++)
		    vist [y] [x] = false;

	    DFS (currentX, currentY);
	}
	return false;
    }


    public boolean winCondition ()
    {
	for (int x = 0 ; x < gridSize ; x++)
	    for (int y = 0 ; y < gridSize ; y++)
		if (!isMine [y] [x])
		    if (!uncovered [y] [x])
			return false;
	return true;
    }


    private void generate ()
    {
	int i = 0;
	int randomX, randomY;

	while (i < mines)
	{
	    randomX = (int) (Math.random () * gridSize);
	    randomY = (int) (Math.random () * gridSize);

	    if ((Math.abs (randomX - currentX) > 1 || Math.abs (randomY - currentY) > 1) && !isMine [randomY] [randomX])
	    {
		isMine [randomY] [randomX] = true;
		++i;
	    }
	}

	int count;
	for (int y = 0 ; y < gridSize ; y++)
	{
	    for (int x = 0 ; x < gridSize ; x++)
	    {
		count = 0;
		for (i = -1 ; i <= 1 ; i++)
		    for (int j = -1 ; j <= 1 ; j++)
			if (y + i >= 0 && y + i < gridSize && x + j >= 0 && x + j < gridSize && isMine [y + i] [x + j])
			    ++count;

		adj [y] [x] = count;
	    }
	}
    }


    private void flag ()
    {
	if (uncovered [currentY] [currentX])
	    return;
	if (flagged [currentY] [currentX])
	{
	    flagged [currentY] [currentX] = false;
	    if (cheating && isMine [currentY] [currentX])
		c.setColor (Color.orange);
	    else
		c.setColor (Color.gray);
	    c.fillRect (currentX * squareSize + 2, currentY * squareSize + 2, squareSize - 3, squareSize - 3);
	    ++mines;
	}
	else
	{
	    flagged [currentY] [currentX] = true;
	    c.setColor (Color.yellow);
	    c.fillRect (currentX * squareSize + 2, currentY * squareSize + 2, squareSize - 3, squareSize - 3);
	    --mines;
	}

	c.setColor (Color.white);
	c.fillRect (620, 50, 300, 50);
	c.setColor ((mines >= 0) ? Color.black:
	Color.red);
	c.drawString ("Mines Left: " + mines, 620, 80);
    }


    private void cheat ()
    {
	c.setColor (Color.orange);
	for (int y = 0 ; y < gridSize ; y++)
	    for (int x = 0 ; x < gridSize ; x++)
		if (isMine [y] [x])
		    c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);

	cheating = true;
    }


    public void game ()
    {
	MyTimer mt = new MyTimer (c);
	mt.start ();

	boolean exit = false;
	boolean firstClick = true;
	boolean win = false;

	char input;

	c.clear ();

	switch (menuChoice)
	{
	    case '1':
		gridSize = 10;
		squareSize = 60;
		mines = 20;
		break;
	    case '2':
		gridSize = 15;
		squareSize = 40;
		mines = 40;
		break;
	    case '3':
		gridSize = 20;
		squareSize = 30;
		mines = 70;
		break;
	}

	currentX = 0;
	currentY = 0;

	for (int i = 0 ; i < gridSize ; i++)
	{
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

	c.setColor (Color.black);

	c.setFont (new Font ("Comic Sans MS", 1, 25));
	c.drawString ("Controls", 610, 140);

	c.setFont (new Font ("Comic Sans MS", 0, 20));
	c.drawString ("Use the WASD keys to move", 610, 170);
	c.drawString ("the selected square around", 610, 195);
	c.drawString ("Press O to uncover square", 610, 240);
	c.drawString ("Press P to flag square", 610, 280);

	for (int y = 0 ; y < gridSize ; y++)
	{
	    for (int x = 0 ; x < gridSize ; x++)
	    {
		isMine [y] [x] = false;
		adj [y] [x] = 0;
		uncovered [y] [x] = false;
		flagged [y] [x] = false;
	    }
	}

	redrawCursor ();


	c.setColor (Color.black);
	c.drawString ("Elapsed Time: 0", 620, 30);
	c.drawString ("Mines Left: " + mines, 620, 80);

	cheating = false;



	while (!exit)
	{
	    input = c.getChar ();
	    switch (input)
	    {
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

		    if (click ())
		    {
			win = false;
			exit = true;
		    }

		    if (winCondition ())
		    {
			win = true;
			exit = true;
		    }

		    break;
		case 'P':
		case 'p':
		    flag ();
		    break;
		case 'C':
		case 'c':
		    cheat ();
		    break;
	    }
	}

	mt.stop ();

	if (win)
	{
	    c.setColor (Color.green);
	    for (int y = 0 ; y < gridSize ; y++)
		for (int x = 0 ; x < gridSize ; x++)
		    if (isMine [y] [x])
			c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);
	}
	else
	{
	    for (int y = 0 ; y < gridSize ; y++)
	    {
		for (int x = 0 ; x < gridSize ; x++)
		{
		    if (isMine [y] [x])
		    {
			if (flagged [y] [x])
			{
			    c.setColor (Color.green);
			    c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);
			}
			else
			{
			    c.setColor (Color.red);
			    c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);
			}
		    }
		    else if (flagged [y] [x] && !isMine [y] [x])
		    {
			c.setColor (Color.red);
			c.fillRect (x * squareSize + squareSize / 4 + 1, y * squareSize + squareSize / 4 + 1, squareSize / 2, squareSize / 2);
		    }
		}
	    }

	    c.setColor (Color.yellow);
	    c.fillRect (620, 320, 60, 60);
	    c.setColor (Color.red);
	    c.fillRect (636, 336, 30, 30);
	    c.fillRect (620, 400, 60, 60);
	    c.setColor (Color.green);
	    c.fillRect (620, 480, 60, 60);

	    c.setColor (Color.black);
	    c.drawString ("Incorrectly Flagged", 700, 355);
	    c.drawString ("Unflagged Mine", 700, 435);
	    c.drawString ("Correctly Flagged", 700, 515);
	}

	c.setFont (new Font ("Comic Sans MS", 1, 30));
	c.setColor (Color.black);
	c.drawString ("Press C to continue", 620, 580);


	while (true)
	{
	    input = c.getChar ();
	    if (input == 'C' || input == 'c')
		break;
	}

	result ((win ? mt.seconds:
	- 1));
    }



    private void result (int time)
    {
	title ("Results", 350, 50);

	if (score >= 0)
	    c.drawString ("Your time: " + score, 100, 200);
	else
	    c.drawString ("You lost!", 100, 200);

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



