/** The Maze class 
 * 
 *  This class will: 
 *    - create a Maze of size (always square) or with a given file
 *    - store the characters to describe a Maze
 *        (S)tart, (E)nd, (W)all, and (0) for empty
 *    - create a random matrix
 *  
 *  @author jillian.maher
 *  @date Nov 13, 2020
 *  
 */
import java.util.Random;
import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;

public class Maze 
{
	private char[][] maze;
	private static final int START_ROW = 1;   //Can change to any value 1 to height-2
	private static final int START_COL = 0;   //Must be 0
	public static final int START_X = 100;
	public static final int START_Y = 150;
	private int spotWidth = 10;
	private int endRow;
	private int endCol;
	private Random rnd = new Random();
	
	public Maze(String fileName)
	{
		try(Scanner input = new Scanner( new File(fileName)))
		{
			int size = input.nextInt();  input.nextLine();
			maze = new char[size][size];
			int r = 0;
			
			while(input.hasNext())
			{
				String[] mazeLine = input.nextLine().split(" ");
				for(int i = 0; i < mazeLine.length; i++)
				{
					maze[r][i] = mazeLine[i].charAt(0);
				}
				r++;
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Maze File was not located.");
		}
		
		
	}
	
	/* Attempted to create RANDOM MAZE - not ready at all 
	public Maze(int size)
	{
		maze = new char[size][size];
		endRow = maze.length-2;
		endCol = maze[0].length - 1;
		
		//Fill the maze with walls
		for(int r=0; r<maze.length; r++)
		{
			for(int c=0; c<maze[r].length; c++)
			{
				maze[r][c] = 'W';
			}
		}
		
		//Set the start and end position
		maze[START_ROW][START_COL] = 'S';
		maze[endRow][endCol] = 'E';

		setWinningPath();
	}
	
	// Possible algorithm: set each row.
	//  first row all walls
	//  next row random percentage of walls, none next to eachother
	//  each following row: 
	//     if empty above and % possibility for opening only on odd columns
	//     if wall above 
	//         think about it more
	//
	//   WALLS AND OPENINGS ALWAYS ODD INCREMENTS
	private void setWinningPath( )
	{
		int r = START_ROW;
		int c = START_COL;
		Direction direction = Direction.RIGHT;
		int stayPathCount = 0;
		
		while( r!=endRow || c!=endCol-1 ) //Check for spot immediately next to End
		{
			//Set up the next place to move. Change direction if the new spot is not in Bounds
			switch(direction)
			{
			case UP: r--; 
					if( !isInBounds(r, c))
					{	r++; direction = Direction.getRandomDirection(); stayPathCount = 1; }
					break;
			case DOWN: r++; 
					if( !isInBounds(r, c))
					{	r--; direction = Direction.getRandomDirection(); stayPathCount = 1; }
					break;
			case RIGHT: c++; 
					if( !isInBounds(r, c))
					{	c--; direction = Direction.getRandomDirection(); stayPathCount = 1; }
					break; 
			case LEFT: c--; 
					if( !isInBounds(r, c))
					{	c++; direction = Direction.getRandomDirection(); stayPathCount = 1; }
					break;  
			}
			
			maze[r][c] = 0;
			stayPathCount++;
			
			if(stayPathCount >= 3 && stayPathCount%2==1 && rnd.nextInt(100) < 70 && !nearOpening(r,c,direction))
			{
				direction = Direction.getRandomDirection();
				stayPathCount = 1;
			}
			
			
			while(approachEdge(r,c, direction))
			{
				direction = Direction.getRandomDirection();
				stayPathCount = 1;
			}
			if(c==endCol-1 && r==endRow)
			{
				direction = Direction.RIGHT;
				stayPathCount = 1; 
			}
			
			System.out.println(r + " " + c + " " + direction + " " + stayPathCount);	
		}
	}
	
	
	private boolean approachEdge(int r, int c, Direction direction)
	{
		switch(direction)
		{
		case UP: return r == 1;
		case DOWN: return r == maze.length - 2;
		case RIGHT: return c == maze[0].length - 2;
		case LEFT: return r == maze.length - 2;
		default: return false; 
		}
	}
	// Determine if the path is moving toward an existing opening
	private boolean nearOpening(int r, int c, Direction dir)
	{
		switch(dir)
		{
		case UP:  return isInBounds(r-2,c) && maze[r-2][c] == 0;
		case DOWN:  return isInBounds(r+2,c) && maze[r+2][c] == 0;
		case RIGHT:  return isInBounds(r,c+2) && maze[r][c+2] == 0;
		case LEFT:  return isInBounds(r,c-2) && maze[r][c-2] == 0;
		default: return false;
		}
	}
	END RANDOM MAZE CODE */
	
	public boolean isInBounds(int r, int c)
	{
		return r >= 1 && c >= 1 && r <= maze.length - 2 && c <= maze[0].length - 2;
	}
	
	//Update the maze to track progress
	//  @param value = 1 --> right turn
	//  @param value = 2 --> left turn
	public void setSpot(int row, int column, int value)
	{
		maze[row][column] = (char)(value+48);
	}
	
	public char getSpot(int row, int column)
	{
		return maze[row][column];
	}
	
	public int getSize()
	{
		return maze.length;
	}
	
	public void draw(GraphicsContext gc)
	{
	    gc.setStroke(Color.BLACK);
	    
	    //Show the Walls and Start and End board
	    for(int r=0; r<maze.length; r++)
	    {
	    	for(int c=0; c<maze.length; c++)
	    	{
	    		drawSpot(gc, maze[r][c], START_Y + c*10, START_X + r*10 );
	    	}
	    }
	}
	
	public void drawSpot(GraphicsContext gc, char spot, int x, int y)
	  {
		  gc.setFill(Color.GRAY);
		  if(spot == 'W')
		  {
			  gc.fillRect(x,y,spotWidth, spotWidth);
		  }
		  if(spot == 'S')
		  {
			  //gc.setFill(Color.BLUE);
			  gc.fillText("IN", x, y + spotWidth);
		  }
		  if(spot == 'E')
		  {
			  //gc.setFill(Color.BLUE);
			  gc.fillText("END", x, y + spotWidth);
		  }
		  gc.strokeRect(x, y, spotWidth, spotWidth);
	  }
	
	public String toString()
	{
		String output = "";
		
		for(int r=0; r<maze.length; r++)
		{
			for(int c=0; c<maze[r].length; c++)
			{
				if(maze[r][c] != 48)
					output += maze[r][c] + " ";
				else
					output += "  ";
			}
			output += "\n";
		}
		
		return output; 		
	}
	
}
