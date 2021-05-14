/**Solver follows the Maze and displays position on Screen
 * 
 * @author jillian.maher
 *
 */

import java.awt.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Solver 
{
	private Maze maze;
	private int row;
	private int col;
	private Direction dir;
	
	
	public Solver(Maze newMaze)
	{
		maze = newMaze;
		setStart();
		dir = Direction.RIGHT;
	}
	
	private void setStart()
	{
		for(int r=0; r<maze.getSize(); r++)
		{
			for(int c=0; c<maze.getSize(); c++)
			{
				if(maze.getSpot(r, c) == 'S')
				{
					row=r;
					col=c;
					return;
				}
			}
		}
		//if no start character found, set a default start
		row=1;
		col=1;
	}
	
	public void reset(GraphicsContext gc)
	{
		//Remove oldMark
		gc.setFill(Color.BLACK);
		gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 10);

		setStart();
		dir = Direction.RIGHT;

		draw(gc);
	}
	
	public boolean move()
	{
		if(maze.getSpot(row, col)=='E' )
			return false;
		if(maze.getSpot(row, col)=='S' && dir == Direction.LEFT)
			return false;
		
		switch(dir)
		{
		case UP: row--; break;
		case DOWN: row++; break;
		case RIGHT: col++; break; 
		case LEFT: col--; break;  
		}
		if(maze.getSpot(row,col) == 'W')
		{
			switch(dir)
			{
			case UP: row++; break;
			case DOWN: row--; break;
			case RIGHT: col--; break; 
			case LEFT: col++; break;  
			}
			return false;
		}
		return true;
	}
	
	public void turnRight()
	{
		switch(dir)
		{
		case UP: dir=Direction.RIGHT; break;
		case DOWN: dir=Direction.LEFT; break;
		case RIGHT: dir=Direction.DOWN; break; 
		case LEFT: dir=Direction.UP; break;  
		}
		maze.setSpot(row,col,1);
	}
	
	public void turnLeft()
	{
		switch(dir)
		{
		case UP: dir=Direction.LEFT; break;
		case DOWN: dir=Direction.RIGHT; break;
		case RIGHT: dir=Direction.UP; break; 
		case LEFT: dir=Direction.DOWN; break;  
		}
		maze.setSpot(row,col,2);
	}
	
	public void draw(GraphicsContext gc)
	{
		//Fill full box
		Color lt_blue = Color.rgb(115, 165, 225);
		gc.setFill(lt_blue);
		gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 10);
		//Show direction
		gc.setFill(Color.BLUE);
		switch(dir)
		{
		case UP: gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 3);
				break;
		case DOWN: gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10 + 7, 10, 3);
				break;
		case RIGHT: gc.fillRect(Maze.START_Y + col*10 + 7, Maze.START_X + row*10, 3, 10);
				break;
		case LEFT: gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 3, 10);
				break;
		}	
	}
	
	public boolean moveAndDraw(GraphicsContext gc)
	{
		//Remove oldMark
		gc.setFill(Color.BLACK);
		gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 10);

		boolean change = move();
		draw(gc);
		
		return change;		
	}
	
	public void solveRecur(GraphicsContext gc)
	{
		System.out.println("Location: " + row + " " + col);
		//Remove oldMark
		gc.setFill(Color.BLACK);
		gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 10);

		//set hold location
		int holdRow = row;
		int holdCol = col;
		Direction holdDir = dir;
		
		if( nearEnd() )
		{
			setEndGame();
			draw(gc);
			return;
		}
		
		if(move())
		{
			solveRecur(gc);
		}
		if(isPathRight())
		{
			turnRight();
			solveRecur(gc);
		}
		if(isPathLeft())
		{
			turnLeft();
			solveRecur(gc);
		}
		draw(gc);
	}
	
	public boolean solve(GraphicsContext gc)
	{
		//Remove oldMark
		gc.setFill(Color.BLACK);
		gc.fillRect(Maze.START_Y + col*10, Maze.START_X + row*10, 10, 10);

		if( nearEnd() )
		{
			setEndGame();
			draw(gc);
			return true;
		}
		
		if(maze.getSpot(row,col) == '1')
		{
			turnLeft();
			move();
		}
		if(maze.getSpot(row, col) == '2')
		{
			move();
		}
		if( isPathRight() )
		{
			turnRight();
			move();
		}
		else if( isPathLeft() )
		{
			turnLeft();
			move();
		}
		else
		{
			if(!move())
			{
				turnRight();
				turnRight();
				move();
				maze.setSpot(row, col, 3);
			}
		}
		draw(gc);
		return false;
	}
	
	private boolean nearEnd()
	{
		if(maze.isInBounds(row, col))
		{
			return maze.getSpot(row+1, col)=='E' || maze.getSpot(row-1,col)=='E' ||
				maze.getSpot(row, col+1)=='E' || maze.getSpot(row, col-1)=='E';
		}
		return false;
	}
	
	private boolean isPathRight()
	{
		switch(dir)
		{
		case UP: return maze.getSpot(row, col+1)!='W';
		case DOWN: return maze.getSpot(row, col-1)!='W';
		case RIGHT: return maze.getSpot(row+1, col)!='W'; 
		case LEFT: return maze.getSpot(row-1, col)!='W';  
		}
		return false;
	}

	private boolean isPathLeft()
	{
		switch(dir)
		{
		case UP: return maze.getSpot(row, col-1)!='W';
		case DOWN: return maze.getSpot(row, col+1)!='W';
		case RIGHT: return maze.getSpot(row-1, col)!='W'; 
		case LEFT: return maze.getSpot(row+1, col)!='W';  
		}
		return false;
	}
	
	private void setEndGame()
	{
		for(int r=0; r<maze.getSize(); r++)
		{
			for(int c=0; c<maze.getSize(); c++)
			{
				if(maze.getSpot(r, c) == 'E')
				{
					row=r;
					col=c;
					dir = Direction.RIGHT;
					return;
				}
			}
		}
	}
}
