/*
Dec. 12 2016
Richard Yi
Mrs. Krasteva
ISP (Minesweeper)

This program is a minesweeper game with 3 difficulties that have increasing grid sizes and mine densities.

On launching the program, a splash screen animation appears.
The program then automatically goes to the main menu.
The user has an option to play one of the three difficulties,
view instructions, view high scores, or exit the program.

If the user chooses to play one of the difficulties, they will be playing the game
until they either win, lose, or give up
They are then brought to the results screen, which tells them the result of their game.
If they won, they are asked to enter their name which will be saved to the high scores file
corresponding to the difficulty they played in.
They are then brought back to the main menu.

If the user chooses to view instructions, they will view a screen that briefly explains
the concepts, mechanics of Minesweeper as well as controls for the game.
They are then brought back to the main menu.

If the user chooses to view high scores, they view the top 10 scores for the three difficulties, respectively.
They are then brought back to the main menu.

If the user chooses the exit, the console window closes, and the program halts.




===IMPORTANT DEBUG INFORMATION===
When in game:
-Press C to reveal all mines
-Press X to instantly win
=================================




Global Variables:

		   Name               Type                                                         Description
======================================================================================================================================
			c              hsa.Console                           A console that provides an input/output interface with the user
		   SIZE             final int                               The maximum size that the grid array dimensions can hold
		  isMine           boolean[][]                                      Stores whether the square at [y][x] is a mine
		   adj               int[][]                                            Stores the number of adjacent mines
		uncovered          boolean[][]                                   Stores whether the square at [y][x] is uncovered
		 flagged           boolean[][]                                    Stores whether the square at [y][x] is flagged
		   vist            boolean[][]                  Stores whether the square at [y][x] has been visited during the Depth-First Search
	   gameStarted           boolean                 Whether or not the game has started (the game starts when the user uncovers the first square)
		 cheating            boolean                    Whether or not the user is cheating (by pressing C to reveal the location of all mines)
		 gridSize              int                                     The side length of the grid for the current difficulty
		squareSize             int                           The side length (in pixels) of a grid square for the current difficulty
		  mines                int                                  The number of mines remaining (that have not been flagged)
		totalMines             int                             The total number of mines within the grid for the current difficulty
		 currentX              int                                  The current X-coordinate of the user's cursor in the grid
		 currentY              int                                  The current Y-coordinate of the user's cursor in the grid
		menuChoice             int                                          Stores the user's choice in the main menu
*/

import hsa.*;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Minesweeper
{
	Console c;

	final int SIZE = 30;
	boolean isMine[] [] = new boolean [SIZE] [SIZE];
	int adj[] [] = new int [SIZE] [SIZE];
	boolean uncovered[] [] = new boolean [SIZE] [SIZE];
	boolean flagged[] [] = new boolean [SIZE] [SIZE];
	boolean vist[] [] = new boolean [SIZE] [SIZE]; //For DFS
	boolean gameStarted, cheating;

	int gridSize, squareSize, mines, totalMines;
	int currentX, currentY;
	char menuChoice;

	public Minesweeper ()
	{
		//A character is 8 pixels wide and 20 pixels tall
		//Window is approx. 600 by 900 pixels
		c = new Console (31, 113, "Minesweeper");
	}


	/*
	Title Method
	Clears the screen and draws a title at a specific X and Y coordinate pair
	*/
	private void title (String text, int x, int y)
	{
		c.clear ();
		c.setFont (new Font ("Comic Sans MS", 0, 40));
		c.setColor (Color.black);
		c.drawString (text, x, y);
	}


	/*
	Pause-program Method
	Pauses the program and draws a prompt text at a specific Y-coordinate
	*/
	private void pauseProgram (int y)
	{
		c.setFont (new Font ("Comic Sans MS", 0, 30));
		c.setColor (Color.black);
		c.drawString ("Press any key to continue...", 50, y);
		c.getChar ();
	}


	/*
	Splash Screen Method
	Starts and joins the SplashScreen Thread, passing the current Console

	Local Variables

			Name                Type                            Description
			 s              SplashScreen    Thread that displays the splash screen when started
	*/
	public void splashScreen () throws InterruptedException
	{
		SplashScreen s = new SplashScreen (c);
		s.start ();
		s.join ();
	}


	/*
	Main Menu Method

	Prompts the user for what part of the game they want to navigate to.
	The program keeps accepting a character until they type one of the characters
	corresponding to a part of the game (1, 2, 3, Q, W, or E, as well as q, w, or e)


	Control Flow
	============
	Conditional Loop #1:
	Gets a character.
	This loop exits when the user enters a valid character
	(1, 2, 3, Q, W, E, q, w, or e)

	Switch Statement #1:
		For any of the cases: {1, 2, 3, q, w, e, Q, W, E}, break the loop
	*/
	public void mainMenu ()
	{
		title ("Minesweeper", 345, 50);

		c.setFont (new Font ("Comic Sans MS", 0, 20));
		c.setColor (Color.black);

		c.drawString ("Press 1 to play easy mode", 10, 100);
		c.drawString ("Press 2 to play medium mode", 10, 150);
		c.drawString ("Press 3 to play hard mode", 10, 200);
		c.drawString ("Press Q for help and instructions", 10, 250);
		c.drawString ("Press W to see high scores", 10, 300);
		c.drawString ("Press E to quit", 10, 350);

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


	/*
	Instructions Screen Method

	Displays the rules and mechanics of Minesweeper as well as the controls for this game.
	*/
	public void instructions ()
	{
		title ("Instructions", 345, 50);

		c.setFont (new Font ("Comic Sans MS", 0, 25));

		c.drawString ("Minesweeper is a single-player strategic game where the objective is to", 20, 100);
		c.drawString ("determine to location of all the mines in the grid by uncovering squares", 20, 125);
		c.drawString ("that you have logically determined are not mines.", 20, 150);

		c.drawString ("When you uncover a square in the grid, it will display how many mines", 20, 195);
		c.drawString ("are adjacent to the square. It is guaranteed that the first square you", 20, 220);
		c.drawString ("uncover will neither be a mine or be adjacent to a mine.", 20, 245);

		c.drawString ("If you logically deduce that a square must be a mine, you can flag that", 20, 290);
		c.drawString ("square for future reference.", 20, 315);

		c.drawString ("If you uncover a square that is a mine, you lose the game, so be careful!", 20, 355);
		c.drawString ("To win the game, uncover all squares that are not mines.", 20, 380);

		c.drawString ("Use the WASD keys to move the current selected square around the grid.", 20, 425);
		c.drawString ("Press O to uncover the current square, and P to flag the current square.", 20, 450);
		pauseProgram (600);
	}


	/*
	High Scores Method

	Shows the 10 highest scores for each difficulty.
	If any highscore file is invalid, it is reset.

	Local Variables

				Name                                Type                                        Description
	==========================================================================================================================================
			  entries                               int                        The number of entries (name and score) in a file
			   header                              String                   The first line of the file (should be the file header)
			   scores                              int[]                               The scores contained in the file
			   names                              String[]                              The names contained in the file
			 difficulty                           String[]                                The name of each difficulty
			  fileName                             String                               The name of the file being read
				in                             BufferedReader                            Object for reading the file
				out                              PrintWriter                           Object for writing to the file
				 i                                  int                                      Loops from 1 to 3
				 i                                  int                             Loops from 0 to the numbr of entries
				 i                                  int                         Loops from 0 to the number of entries minus 1
				 j                                  int                                     Loops from 0 to i
				 i                                  int                 Loops from 0 to the minimum of 10 and the number of entries
			   input                               char                                    Holds user keypress
	Control Flow
	============
	Counted Loop #1:
	Loop 3 times for the 3 diffiulties


	Conditional #1:
		Checks if the highscore file exists
		If it doesn't, reset the file

	Conditional #2:
		If there is no header or there is an invalid header, reset the file

	Exception Handler #1:
		Initialize the size of names and scores
		Attempt to read the data, and then sort it

		If a conversion fails (NumberFormatException) or there is no input when it is attempted
		to be read (NullPointerException), give up and reset the file


		Counted Loop #1:
		Read in the entries of the file in a loop

		Counted Loop #2:
		Counted Loop #3:
			An standard unoptimized implementation of Bubblesort
			From 1 .. N - 1, then 1 .. N - 2, then 1 .. N - 3
			and so on, if A[N] > A[N + 1], swap them

		Counted Loop #4:
		Display the top 10 entries
		If there are less than 10 entries, display all of them
	*/
	public void highScores () throws IOException
	{
		int entries;
		String header;
		int scores[];
		String names[];
		String difficulty[] = {"Easy", "Medium", "Hard"};
		String fileName;
		BufferedReader in;
		PrintWriter out;
		char input;


		for (int n = 1 ; n <= 3 ; n++)
		{
			title ("High Scores - " + difficulty [n - 1], 20, 50);

			fileName = "highscores" + String.valueOf (n) + ".txt";

			if (!new File (fileName).exists ())
			{
				JOptionPane.showMessageDialog (null, "File not found. Creating new file.");

				out = new PrintWriter (new FileWriter (fileName)); //reset file
				out.println ("High Scores (DO NOT MODIFY)");
				out.println ("0");
				out.close ();
				continue;
			}

			in = new BufferedReader (new FileReader (fileName));

			header = in.readLine ();

			if (header == null || !header.equals ("High Scores (DO NOT MODIFY)"))
			{
				JOptionPane.showMessageDialog (null, "File header invalid. Erasing file.");

				out = new PrintWriter (new FileWriter (fileName)); //reset file
				out.println ("High Scores (DO NOT MODIFY)");
				out.println ("0");
				out.close ();
				continue;
			}

			try
			{
				entries = Integer.parseInt (in.readLine ());
				names = new String [entries];
				scores = new int [entries];
				String tempName;
				int tempScore;

				for (int i = 0 ; i < entries ; i++)
				{
					names [i] = in.readLine ();
					scores [i] = Integer.parseInt (in.readLine ());
				}

				//bubblesort
				for (int i = entries - 1 ; i > 0 ; i--)
				{
					for (int j = 0 ; j < i ; j++)
					{
						if (scores [j] > scores [j + 1])
						{
							tempScore = scores [j];
							tempName = names [j];

							scores [j] = scores [j + 1];
							names [j] = names [j + 1];

							scores [j + 1] = tempScore;
							names [j + 1] = tempName;
						}
					}
				}

				c.setFont (new Font ("Comic Sans MS", 0, 20));
				for (int i = 0 ; i < Math.min (entries, 10) ; i++)
				{
					c.drawString (names [i], 20, i * 40 + 120);
					c.drawString (String.valueOf (scores [i]), 700, i * 40 + 120);
				}
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog (null, "Invalid input high scores file. Erasing file.");
				in.close ();

				out = new PrintWriter (new FileWriter (fileName)); //reset file
				out.println ("High Scores (DO NOT MODIFY)");
				out.println ("0");
				out.close ();
				continue;
			}
			catch (NullPointerException e)
			{
				JOptionPane.showMessageDialog (null, "Invalid input high scores file. Erasing file.");
				in.close ();

				out = new PrintWriter (new FileWriter (fileName)); //reset file
				out.println ("High Scores (DO NOT MODIFY)");
				out.println ("0");
				out.close ();
				continue;
			}

			c.drawString ("Press C or space to continue. Press X to delete the file.", 50, 600);

			while (true)
			{
				input = c.getChar ();
				if (input == 'c' || input == 'C' || input == ' ')
					break;
				else if (input == 'x' || input == 'X')
				{
					JOptionPane.showMessageDialog (null, "Erasing file.");
					in.close ();

					out = new PrintWriter (new FileWriter (fileName)); //reset file
					out.println ("High Scores (DO NOT MODIFY)");
					out.println ("0");
					out.close ();
					break;
				}
			}
		}
	}


	/*
	Goodbye Method
	Thanks the user for using the program and closes the console window.
	*/
	public void goodbye ()
	{
		title ("Minesweeper", 345, 50);

		c.setFont (new Font ("Comic Sans MS", 0, 30));
		c.setColor (Color.black);

		c.drawString ("Thank you for using my program", 50, 300);
		c.drawString ("Made by: Richard Yi", 50, 400);

		pauseProgram (500);
		c.close ();
	}



	//Redraws the cursor at the current coordiantes
	private void redrawCursor ()
	{
		c.setColor (Color.red);
		c.drawRect (currentX * squareSize, currentY * squareSize, squareSize, squareSize);
		c.drawRect (currentX * squareSize + 1, currentY * squareSize + 1, squareSize - 2, squareSize - 2);
	}


	/*
	Move the cursor a certain number of squares on the X and Y axes.


	Local Variables(Paramaters)

		   Name         Type                  Description
	===================================================================
		  deltaX        int         The change in the X coordiante
		  deltaY        int         The change in the Y coordinate

	Control Flow
	============
	Conditional #1:
		If the current X-coordinate is less than 0, set it to 0
		If the current X-coordinate is more than the maximum, set it to the maximum

	Conditional #2:
		If the current Y-coordinate is less than 0, set it to 0
		If the current Y-coordinate is more than the maximum, set it to the maximum

	*/
	private void moveSelected (int deltaX, int deltaY)
	{
		c.setColor (Color.black);
		c.drawRect (currentX * squareSize, currentY * squareSize, squareSize, squareSize);
		c.setColor (Color.white);
		c.drawRect (currentX * squareSize + 1, currentY * squareSize + 1, squareSize - 2, squareSize - 2);

		currentX += deltaX;
		currentY += deltaY;

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


	/*
	Uncovers a square at the x and y coordinates specified.

	Local Variables(Paramaters)

		   Name         Type                          Description
	==================================================================================
			x           int             X-coordinate of the square to be uncovered
			y           int             Y-coordinate of the square to be uncovered

	Control Flow
	============

	Conditional #1:
		If the number of adjacent mines to the square sepcified is more than 0, display that number at the square (centered)
	*/
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


	/*
	A depth first search for computing how the squares are uncovered
	if a user chooses to uncover a mine that has 0 adjacent mines

	First, set the current location to visited and uncover it

		   Name         Type                                   Description
	===========================================================================================
			x           int             X-coordinate of the current square in the traversal
			y           int             Y-coordinate of the current square in the traversal

	Control Flow
	============

	Counted Loop #1:
		Counted Loop #2:
		Check all the adjacent cells (With an X and Y coordinate one less, the same, or one more the the original)

		Conditional #1:
			If the cell is within the grid, isn't a mine, and hasn't been visited in the DFS before

			Conditional #2:
			If the cell does have adjacent mines, just uncover it but don't recurse
			Conditional #3:
			If the cell has no adjacent mines, call this method on the new coordinates
	*/
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


	/*
	What happens when the user "clicks" on a mine.
	Returns true if the user loses on the click.

	Control Flow
	============

	Conditional #1:
		If the current location of the cursor is a flag, do nothing (return false).
	Conditional #2:
		If the current location is a mine, return true, signifying the user has lost.
	Conditinoal #3:
		If the current location has been uncovered, do nothing (return false).
	Conditional #4:
		If the current locaiton has (an) adjacent mine(s), uncover the square at the current location
	Conditional #5:
		Otherwise, it means there are no mines around it, so reset the vist[][] array and preform a DFS at the current location
	*/
	private boolean click ()
	{
		if (flagged [currentY] [currentX])
			return false;
		else if (isMine [currentY] [currentX])
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


	/*
	Check if the user has won.

	Control Flow
	============

	Counted Loop #1:
		Counted Loop #2:
		For each square in the grid,
		Conditinoal #1:
			If the square is not a mine and hasn't been uncovered,
			it means the user hasn't won yet, so return false

	If the nested loops finish without returning, all non-mines have been accounted for
	and the user wins, so return true.
	*/
	public boolean winCondition ()
	{
		for (int x = 0 ; x < gridSize ; x++)
			for (int y = 0 ; y < gridSize ; y++)
				if (!isMine [y] [x] && !uncovered [y] [x])
					return false;
		return true;
	}


	/*
	Randomly generates the mines.
	It is called when the user uncovers his first square)

	Local Variables

		   Name                 Type                                   Description
	===========================================================================================
		 generated              int                          Number of mines generated so far
		  randomX               int                               A random X-coordinate
		  randomY               int                               A random X-coordinate
		   count                int                    Temporarily holds the number of adjacent mines

	Control Flow
	============

	Conditional Loop #1:
		Keeps looping until there are enough mines generated

		Conditional #1:
		If the random coordinate is not adjacent to the current coordinate (where the user clicked) and it is not already a mine,
		set that coordinate to a mine, and increment the number of mines generated

	Counted Loop #1:
		Counted Loop #2:
		For each square in the grid

		Counted Loop #3:
			Counted Loop #4:
			For each adjacent square

			Conditional #2:
				If the adjacent square is in the grid bounds, and there's a mine there, add one to count

	*/
	private void generate ()
	{
		int generated = 0;
		int randomX, randomY;
		int count;

		while (generated < mines)
		{
			randomX = (int) (Math.random () * gridSize);
			randomY = (int) (Math.random () * gridSize);

			if ((Math.abs (randomX - currentX) > 1 || Math.abs (randomY - currentY) > 1) && !isMine [randomY] [randomX])
			{
				isMine [randomY] [randomX] = true;
				++generated;
			}
		}

		for (int y = 0 ; y < gridSize ; y++)
		{
			for (int x = 0 ; x < gridSize ; x++)
			{
				count = 0;
				for (int i = -1 ; i <= 1 ; i++)
					for (int j = -1 ; j <= 1 ; j++)
						if (y + i >= 0 && y + i < gridSize && x + j >= 0 && x + j < gridSize && isMine [y + i] [x + j])
							++count;

				adj [y] [x] = count;
			}
		}
	}


	/*
	Flags a mine (triggered when the user presses P on a square)

	Control Flow
	============
	Conditional #1:
		 If the square is already uncovered, or the game hasn't started yet, do nothing (return)
	Conditional #2:
		 If the square is already flagged, change the flagged status to false, and change it back to gray
		(or to orange if cheat mode is activated)
		Also, increment the number of mines
	Otherwise (if the square isn't flagged),
		Make the square flagged by setting the array element and changing the color
	*/
	private void flag ()
	{
		if (uncovered [currentY] [currentX] || !gameStarted)
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
		Color.red); //ternary operator
		c.drawString ("Mines Left: " + mines, 620, 80);
	}


	/*
	Activates cheat mode

	Control Flow
	============

	Counted Loop #1:
		Counted Loop #2:
		For each square in the grid

		Conditional #1:
			If it's a mine and hasn't been flagged, make it orange
	*/
	private void cheat ()
	{
		c.setColor (Color.orange);
		for (int y = 0 ; y < gridSize ; y++)
			for (int x = 0 ; x < gridSize ; x++)
				if (isMine [y] [x] && !flagged [y] [x])
					c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);

		cheating = true;
	}


	/*
	Game method.
	Initializes the game variables and handles all keypresses made throughout the game.

		Name                 Type                                             Description
	============================================================================================================================
		 mt                 MyTimer          Starts the timer that keeps track, and tells the user how much time has elapsed
		exit                boolean                       Controls whether or not the while loop below exits
		win                 boolean                                Holds whether or not the user won
		   input                 char

	Control Flow
	============
	Switch Statement #1:
		Depending on which difficulty the user chose, set a different number of
		squares in the grid, the number of pixels per square, and the number of mines

	Counted Loop #1:
		Counted Lop #2:
		Draw all the squares and grid outlines

	Counted Loop #3:
		Counted Loop #4:
		For each square in the grid, reset the information for that square

	Conditional Loop #1:
		Exits only when the exit variable is set to true.
		The variable is set to true when the user wins, loses, or gives up.

		Switch Statement #2:
		Depending on what key the user presses, handle it by calling the method
		corresponding to the action

		Conditional #1:
			If the user uncovers a square and the game hasn't started yet,
			generate the mines and start the timer

		Conditional #2:
			If the user lost from uncovering that square (the square is a mine),
			set the "win" flag to false and exit the loop

		Conditional #3:
			If the user won from uncovering the square (all non-mine squares have been uncovered),
			set the "win" flag to true and exit the loop

	Conditional #4:
		If the user has won, make all mines green squares

		Counted Loop #5:
		Counted Loop #6:
			For each square in the grid,

			Conditional #5:
			If the square contains a mine, make it green on the screen

		If the user didn't win (they lost),

		Counted Loop #7:
		Counted Loop #8:
			For each square in the grid,

			Conditional #6:
			If the square is a mine,

			Conditional #7:
				If it's been flagged, turn it green (the mine was correctly flagged)

				Otherwise, make it red (the mine was not flagged)

			Conditional #8:
				If it's been flagged but isn't a mine, turn it add a red dot to the square


	Conditional Loop #2:
		Exit when the user presses 'c' or 'C'
	*/
	public void game () throws IOException
	{
		MyTimer mt = new MyTimer (c);

		boolean exit = false;
		boolean win = false;
		char input;

		c.clear ();

		gameStarted = false;

		switch (menuChoice)
		{
			case '1':
				gridSize = 10;
				squareSize = 60;
				mines = 15;
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
		c.drawString ("Press Q to give up", 610, 320);

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
					if (!gameStarted)
					{
						generate ();
						mt.start ();
					}
					gameStarted = true;

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
				case 'X':
				case 'x':
					win = true;
					exit = true;
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
							c.setColor (Color.green);
						else
							c.setColor (Color.red);

						c.fillRect (x * squareSize + 2, y * squareSize + 2, squareSize - 3, squareSize - 3);
					}
					else if (flagged [y] [x] && !isMine [y] [x])
					{
						c.setColor (Color.red);
						c.fillRect (x * squareSize + squareSize / 4 + 1, y * squareSize + squareSize / 4 + 1, squareSize / 2, squareSize / 2);
					}
				}
			}

			c.setColor (Color.yellow);
			c.fillRect (620, 330, 60, 60);
			c.setColor (Color.red);
			c.fillRect (636, 346, 30, 30);
			c.fillRect (620, 410, 60, 60);
			c.setColor (Color.green);
			c.fillRect (620, 490, 60, 60);

			c.setColor (Color.black);
			c.drawString ("Incorrectly Flagged", 700, 365);
			c.drawString ("Unflagged Mine", 700, 445);
			c.drawString ("Correctly Flagged", 700, 525);
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
		- 1)); //ternary operator
	}


	/*
	Results Screen

	Local Variables

			Name                    Type                                     Description
	=================================================================================================================
			 in                BufferedReader                       Object for reading from a file
			 out                PrinterWriter                        Object for writing to a file
			header                 String                 Stores the file's first line (should be the header)
			newName                String             The text that the user chooses as their name for this session
		   fileName                String                          The name of the file being read
		   entries                  int                  The number of entries (name/score pairs) in the file
			scores                 int[]                            All the scores in the file
			names                 String[]                           All the names in the file
			input                  char              Character input (to wait for the user to enter newline or space)
		   charsOK                boolean                Holds whether or not the characters in the name are valid

	Control Flow
	============

	Conditional #1:
		If the time is in a valid range (0 or more), then save the highscore

		Conditional Loop #1:
		Keeps looping until the inputted name is valid
		(It's valid if the name contains 32 characters or less and contains a non-whitespace character)

		Counted Loop #1:
			For each character in the loop

		Conditional #2:
			If the name is longer than 32 characters, it is invalid

		Conditional #3:
			If the name contains only whitespace characters, it is invalid

		Conditional #4:
			If the name is valid, break the loop

		Conditional #5:
		If the high scores file doesn't exist, create the file

		Otherwise, try to read the file

		Conditional #6:
			If the header is invalid or missing, erase/reset file

			Otherwise, continue

			Exception Handler #1:
			Try to read the file.

			Counted Loop #2:
				Read in the names and scores in a loop

			Counted Loop #3:
				Re-write the names and scores in a loop back into the file, including the new one

			If there is an invalid field (NumberFormatException) or a missing field (NullPointerException), reset the file

		If the score is invalid (-1), the user lost.

		Conditional Loop #2:
		Exits when the user enters a newline or space
	*/
	private void result (int time) throws IOException
	{
		BufferedReader in;
		PrintWriter out;
		String header, newName = "";
		String fileName = "highscores" + menuChoice + ".txt";
		int entries;
		int scores[];
		String names[];
		char input;
		boolean charsOK = false;

		title ("Results", 380, 50);

		if (time >= 0)
		{
			c.drawString ("Your time: " + time, 50, 200);

			c.setFont (new Font ("Comic Sans MS", 0, 20));
			c.drawString ("Please enter your name: ", 25, 400);

			while (true)
			{
				c.setCursor (22, 4);
				c.println ();
				c.setCursor (22, 4);

				newName = c.readLine ();

				for (int i = 0 ; i < newName.length () ; i++)
					if (newName.charAt (i) != ' ' || newName.charAt (i) != '\n')
						charsOK = true;

				if (newName.length () > 32)
					JOptionPane.showMessageDialog (null, "Your name cannot be longer than 32 characters!");

				if (!charsOK)
					JOptionPane.showMessageDialog (null, "Your name cannot be comprised only of whitespace characters");

				if (!(newName.length () > 32 || !charsOK))
					break;
			}

			if (!new File (fileName).exists ())
			{
				JOptionPane.showMessageDialog (null, "No file found. Creating new file.");
				out = new PrintWriter (new FileWriter (fileName));

				out.println ("High Scores (DO NOT MODIFY)");
				out.println ("1");
				out.println (newName);
				out.println (time);
				out.close ();
			}
			else
			{
				in = new BufferedReader (new FileReader (fileName));
				header = in.readLine ();

				if (header == null || !header.equals ("High Scores (DO NOT MODIFY)"))
				{
					JOptionPane.showMessageDialog (null, "File header invalid. Erasing file.");

					in.close ();

					out = new PrintWriter (new FileWriter (fileName));
					out.println ("High Scores (DO NOT MODIFY)");
					out.println ("1");
					out.println (newName);
					out.println (time);
					out.close ();
				}
				else
				{
					out = new PrintWriter (new FileWriter (fileName));

					try
					{
						entries = Integer.parseInt (in.readLine ());
						scores = new int [entries];
						names = new String [entries];

						for (int i = 0 ; i < entries ; i++)
						{
							names [i] = in.readLine ();
							scores [i] = Integer.parseInt (in.readLine ());
						}

						in.close ();

						out.println ("High Scores (DO NOT MODIFY)");
						out.println (entries + 1);
						for (int i = 0 ; i < entries ; i++)
						{
							out.println (names [i]);
							out.println (scores [i]);
						}
						out.println (newName);
						out.println (time);

						out.close ();
					}
					catch (NumberFormatException e)
					{
						JOptionPane.showMessageDialog (null, "Invalid input high scores file. Erasing file.");
						in.close ();

						out = new PrintWriter (new FileWriter (fileName)); //reset file
						out.println ("High Scores (DO NOT MODIFY)");
						out.println ("1");
						out.println (newName);
						out.println (time);
						out.close ();
					}
					catch (NullPointerException e)
					{
						JOptionPane.showMessageDialog (null, "Invalid input high scores file. Erasing file.");
						in.close ();

						out = new PrintWriter (new FileWriter (fileName)); //reset file
						out.println ("High Scores (DO NOT MODIFY)");
						out.println ("1");
						out.println (newName);
						out.println (time);
						out.close ();
					}
				}

			}
		}
		else
			c.drawString ("You lost!", 380, 300);

		c.setFont (new Font ("Comic Sans MS", 0, 30));
		c.drawString ("Press enter or space to continue...", 20, 600);

		while (true)
		{
			input = c.getChar ();
			if (input == '\n' || input == ' ')
				break;
		}
	}


	/*
	Local Variables

			Name              Type                                                        Description
	=====================================================================================================================================
			 m             Minesweeper            Object from this class so that the methods can be excecuted in a non-static context

	Control Flow
	============

	Conditional Loop #1:
		Keeps looping until the user presses 'e' or 'E'

		Conditional #1:
		If the menu choice is 1, 2, or 3, start the game
		Conditional #2:
		If the menu choice is Q or q,
	*/
	public static void main (String[] args) throws IOException, InterruptedException
	{
		Minesweeper m = new Minesweeper ();
		//m.splashScreen ();
		m.instructions ();

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


