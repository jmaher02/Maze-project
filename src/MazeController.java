//The Control class for the Maze Player

import java.net.URL;

import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MazeController 
{
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	
	//Models
	private Maze maze;
	private Solver solve_control;
	
	@FXML VBox canvasContainer;
	
	@FXML private Label header;
	@FXML private Button solveR;
	@FXML private Button solveI;
	@FXML private Button moveButton;
	@FXML private Button rightButton;
	@FXML private Button leftButton;
	@FXML private Button restart;
	@FXML private Label label;
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	@FXML
	public void initialize()
	{
		maze = new Maze("maze1.txt");
		solve_control = new Solver( maze );
		
		//Update Label Text Colors
		header.setTextFill(Color.web("#aaaaaa"));
		label.setTextFill(Color.web("#bd4700"));
		
		//Set up VBox with Maze Output
		canvas = new Canvas(WIDTH,HEIGHT);
		canvasContainer.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
		Color background = Color.BLACK;
	    gc.setFill(background);
	    gc.fillRect(0, 0, WIDTH, HEIGHT);

	    //Draw the board
	    maze.draw(gc);
	    
	    //Place solver token on screen
	    solve_control.draw(gc);
	}
	
	@FXML
	protected void solveRecursive( ActionEvent event)
	{
		solve_control.solveRecur(gc);
		solve_control.draw(gc);
	}
	
	@FXML
	protected void solveIterative( ActionEvent event)
	{
		if( solve_control.solve(gc) )
		{
			label.setTextFill(Color.web("#73A5E1"));
			label.setFont(new Font("Segoe", 30));
			label.setText("YOU WIN!");
			moveButton.setDisable(true);
			rightButton.setDisable(true);
			leftButton.setDisable(true);
			solveR.setDisable(true);
			solveI.setDisable(true);
		}
	}
	
	@FXML
	protected void moveForward( ActionEvent event)
	{
		if( !solve_control.moveAndDraw(gc))
		{
			label.setText("Cannot Move Forward");
		}
	}
	
	@FXML 
	protected void turnRight( ActionEvent event)
	{
		solve_control.turnRight();
		solve_control.draw(gc);
		label.setText("");
	}
	
	@FXML
	protected void turnLeft( ActionEvent event)
	{
		solve_control.turnLeft();
		solve_control.draw(gc);
		label.setText("");
	}
	
	@FXML
	protected void restart( ActionEvent event)
	{
		solve_control.reset(gc);
	}
	
}
