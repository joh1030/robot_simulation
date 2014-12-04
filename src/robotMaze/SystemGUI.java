package robotMaze;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class SystemGUI extends JFrame {

	private Maze maze;
	private Queue<Robot> robots;
	private JComboBox cavernCombo;
	private Robot currentRobot, nextRobot;
	private JTextField currentRobotField;
	private Cell start;
	private boolean found = false; // false by default

	public static final int LENGTH = 25;

	public SystemGUI (String layoutFile) throws FileNotFoundException {
		maze = new Maze(layoutFile);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((maze.getNumCols()+7) * LENGTH, (maze.getNumRows()+4) * LENGTH);
		setTitle("Robot Maze");

		configRobots();

		maze.setCurrentRobot(currentRobot);

		createControlPanel(this);

		createLegendPanel(this);

		add(maze, BorderLayout.CENTER); // this calls paintComponent()
	}

	public void createLegendPanel(JFrame frame) {
		JPanel bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(2,0));
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JLabel labelGreen = new JLabel("Green");
		JTextField wall = new JTextField("Walls");
		JLabel labelRed = new JLabel("White");
		JTextField start = new JTextField("Start");
		JLabel labelYellow = new JLabel("Yellow");
		JTextField cavern = new JTextField("Caverns");
		JLabel labelGrey = new JLabel("Grey");
		JTextField passage = new JTextField("Passages");
		panel.add(labelGreen); 
		panel.add(wall);
		panel.add(labelRed);
		panel.add(start); 
		panel.add(labelYellow);
		panel.add(cavern);
		panel.add(labelGrey);
		panel.add(passage);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Legends"));
		bigPanel.add(panel, BorderLayout.CENTER);
		frame.add(bigPanel, BorderLayout.EAST);
	}

	public void createControlPanel(JFrame frame) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		frame.add(panel, BorderLayout.SOUTH);

		// Robot Panel
		JPanel robotPanel = new JPanel();
		robotPanel.setBorder(new TitledBorder(new EtchedBorder(), "Robot ID"));
		JLabel robotLabel = new JLabel("CURRENT ROBOT");
		currentRobotField = new JTextField(Integer.toString(currentRobot.getID()));
		robotPanel.add(robotLabel);
		robotPanel.add(currentRobotField);
		panel.add(robotPanel);

		// Cavern Panel
		JPanel cavernPanel = new JPanel();
		JLabel cavernLabel = new JLabel("SELECT CAVERN NUMBER");
		cavernCombo = new JComboBox();
		for (int i = 0; i < maze.getNumRows(); i++) {
			for (int j = 0; j < maze.getNumCols(); j++) {
				if (maze.getCell(i, j).isCavern()) {
					cavernCombo.addItem(((Cavern)maze.getCell(i, j)).getCavernID());
				}
			}
		}
		cavernPanel.add(cavernLabel);
		cavernPanel.add(cavernCombo);
		cavernPanel.setBorder(new TitledBorder(new EtchedBorder(), "Cavern Number"));
		panel.add(cavernPanel);
		// Send Robot Button
		JButton sendButton = new JButton("SEND ROBOT");
		sendButton.addActionListener(new SendButtonListener());
		panel.add(sendButton);
	}

	public boolean getCavernFound(int id, Robot robot) {
		switch(id) {
		case 1:
			return robot.getFoundOne();
		case 2:
			return robot.getFoundTwo();
		case 3:
			return robot.getFoundThree();
		case 4:
			return robot.getFoundFour();
		case 5:
			return robot.getFoundFive();
		case 6:
			return robot.getFoundSix();
		case 7:
			return robot.getFoundSeven();
		case 8:
			return robot.getFoundEight();
		default:
			return false;
		}
	}

	public void setCavernFound(int id, Robot currentRobot) {
		switch(id) {
		case 1:
			currentRobot.setFoundOne(true);
			break;
		case 2:
			currentRobot.setFoundTwo(true);
			break;
		case 3:
			currentRobot.setFoundThree(true);
			break;
		case 4:
			currentRobot.setFoundFour(true);
			break;
		case 5:
			currentRobot.setFoundFive(true);
			break;
		case 6:
			currentRobot.setFoundSix(true);
			break;
		case 7:
			currentRobot.setFoundSeven(true);
			break;
		case 8:
			currentRobot.setFoundEight(true);
			break;
		default:
			break;
		}
	}

	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			updateRobotID();
			int cavern = (int) cavernCombo.getSelectedItem();
			currentRobot.setTargetCavern(cavern, maze);
			// if current robot OR next robot knows the route to the cavern, go straight to the cavern
			if (getCavernFound(cavern, currentRobot) || getCavernFound(cavern, nextRobot)) {
				found = true;
				setCavernFound(cavern, currentRobot); // current robot knows the cavern location
				currentRobot.setRobotLocation(currentRobot.getTargetCavern().getRow(), currentRobot.getTargetCavern().getCol());
				repaint();
			}
			// else, explore the mine
			else {
				currentRobot.explore(cavern, maze);
				found = currentRobot.getFoundCavern();
				if (!found) {
					currentRobot.setRobotLocation(maze.getStart().getRow(), maze.getStart().getCol());
				}
				repaint();
			}

			if (found) {
				JOptionPane.showMessageDialog(null, "Robot " + currentRobot.getID() + " found the path to cavern " + cavern, "Robot Maze", JOptionPane.INFORMATION_MESSAGE );
			}
			else {
				JOptionPane.showMessageDialog(null, "Robot " + currentRobot.getID() + " did not find the path to cavern " + cavern, "Robot Maze", JOptionPane.INFORMATION_MESSAGE );
			}
			currentRobot.setRobotLocation(maze.getStart().getRow(), maze.getStart().getCol()); // return current robot to starting position
			currentRobot.getVisited().clear(); // clear current robot's visited list
			currentRobot.setFoundCavern(false); // reset foundCavern to false
			robots.add(currentRobot); // add robot to the back of the list
			currentRobot = robots.remove(); // set current robot
			nextRobot = robots.peek(); // set next robot
			maze.setCurrentRobot(currentRobot); // set current robot 
			updateRobotID();
			repaint();
		}
	}

	public void configRobots() {
		robots = new LinkedList <Robot>();
		robots.add(new Robot(1, Color.BLUE, maze.getStart().getRow(), maze.getStart().getCol()));
		robots.add(new Robot(2, Color.PINK, maze.getStart().getRow(), maze.getStart().getCol()));
		robots.add(new Robot(3, Color.MAGENTA, maze.getStart().getRow(), maze.getStart().getCol()));
		robots.add(new Robot(4, Color.CYAN, maze.getStart().getRow(), maze.getStart().getCol()));
		currentRobot = robots.remove(); // set current robot
		nextRobot = robots.peek(); // set next robot
	}

	public Queue<Robot> getRobots(){
		return robots;
	}

	public Maze getMaze() {
		return maze;
	}

	public JComboBox getCavernCombo() {
		return cavernCombo;
	}

	public void updateRobotID() {
		currentRobotField.setText(Integer.toString(currentRobot.getID()));
	}

	public Robot getCurrentRobot() {
		return currentRobot;
	}

	public static void main (String[] args) throws FileNotFoundException {
		SystemGUI system = new SystemGUI("maze.csv");
		system.setDefaultCloseOperation(EXIT_ON_CLOSE);
		system.setVisible(true);
	}
}
