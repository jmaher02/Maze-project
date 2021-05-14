import java.util.Random;


public enum Direction {UP, DOWN, RIGHT, LEFT; 
		public static Direction getRandomDirection() {
			Random rnd = new Random();
			return Direction.values()[rnd.nextInt(Direction.values().length)]; }
		}