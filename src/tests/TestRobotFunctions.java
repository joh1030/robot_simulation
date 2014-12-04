package tests;

import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.Random;

import org.junit.*;

import robotMaze.*;

public class TestRobotFunctions {
	
	private static Maze maze;
	private Queue<Robot> robots = null;
	private Robot currentRobot;
	
	@Before
	public void setUp() throws FileNotFoundException {
		SystemGUI system = new SystemGUI("maze.csv");
		system.configRobots();
		maze = system.getMaze();
		robots = system.getRobots();
	}
	
	// test valid cell
	@Test
	public void testValidCell() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setRobotLocation(13,14);
		Cell validCellOne = currentRobot.chooseValidCell(maze);
		Assert.assertNotEquals(validCellOne, null);
		
		currentRobot.setRobotLocation(0, 0);
		Cell validCellTwo = currentRobot.chooseValidCell(maze);
		Assert.assertEquals(validCellTwo, null);
	}
	
	// test setting target
	@Test
	public void testSetTarget() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setTargetCavern(1, maze);
		Assert.assertEquals(currentRobot.getTargetCavern().getRow(), 0);
		Assert.assertEquals(currentRobot.getTargetCavern().getCol(), 0);
		
		currentRobot.setTargetCavern(7, maze);
		Assert.assertEquals(currentRobot.getTargetCavern().getRow(), 19);
		Assert.assertEquals(currentRobot.getTargetCavern().getCol(), 9);
	}
	
	// 1 is not reachable
	@Test
	public void findUnfindable() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setTargetCavern(1, maze);
		currentRobot.explore(1, maze); 
		Assert.assertFalse(currentRobot.getFoundOne()); // should return false
		robots.offer(currentRobot); // add robot back to list
	}
	
	// 5 is the shortest
	@Test
	public void findShortest() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setTargetCavern(5, maze);
		currentRobot.explore(5, maze); 
		Assert.assertTrue(currentRobot.getFoundFive()); 
		robots.offer(currentRobot); // add robot back to list
		
	}
	
	// 7 is the longest (and trickiest)
	@Test
	public void findLongest() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setTargetCavern(7, maze);
		currentRobot.explore(7, maze); 
		Assert.assertTrue(currentRobot.getFoundSeven());
		robots.offer(currentRobot); // add robot back to list
	}
	
	// 3 is the weirdest
	@Test
	public void findWeirdest() {
		currentRobot = robots.poll(); // set current robot
		currentRobot.setTargetCavern(3, maze);
		currentRobot.explore(3, maze); //1 is unreachable, should set pathFound to false
		Assert.assertTrue(currentRobot.getFoundThree());
		robots.offer(currentRobot); // add robot back to list
	}
}
