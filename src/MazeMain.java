/* Display the Initial MazeBoard and Solver
   by Anderson, Franceschi
   
   @author: jillian.maher
   @date: Nov 13, 2020
*/

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class MazeMain extends Application 
{

  @Override
  public void start( Stage stage ) 
  {
	  try 
	    {
	      URL url = getClass( ).getResource( "fxml_maze_display.fxml" );
	      BorderPane root = FXMLLoader.load( url );
	      Scene scene = new Scene( root, 800, 600 );
	      stage.setTitle( "Maze Game" );
	      stage.setScene( scene ); 
	      stage.show( );
	    }
	    catch( Exception e )
	    {
	      e.printStackTrace( ); 
	    }
  }    
    
  public static void main( String [] args ) 
  {
    launch( args );
  }    
}