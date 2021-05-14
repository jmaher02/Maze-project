/**This tester will verify the Maze class in text version
 * 
 * @author jillian.maher
 * @date  Nov 3, 2020
 *
 */
public class MazeTester 
{

	public static void main(String[] args)
	{
		//Maze maze = new Maze(35,35);
		//System.out.println(maze);
		
		Maze maze = new Maze("maze1.txt");
		System.out.println(maze);
	}
}
