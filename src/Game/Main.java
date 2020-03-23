package Game;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

public class Main extends GraphicsProgram {

	private boolean gameNotOvered = false;
	private int typo;
	private double position;

	public void init() {
		// digits for score
		num1 = new GImage("0.jpg");
		num2 = new GImage("0.jpg");
		num3 = new GImage("0.jpg");
		num4 = new GImage("0.jpg");
		num5 = new GImage("0.jpg");

		add(num5, getWidth() - 30, getHeight() / 25);
		add(num4, getWidth() - 45, getHeight() / 25);
		add(num3, getWidth() - 60, getHeight() / 25);
		add(num2, getWidth() - 75, getHeight() / 25);
		add(num1, getWidth() - 90, getHeight() / 25);

		backButton();
		addMouseListeners();
		addKeyListeners();

	}

	private void backButton() {
		backButton = new GImage("back.png");
		backButton.setSize(50, 50);
		add(backButton, 20, 20);
	}

	public Main(boolean resume) {
		Main.resume = resume;
	}

	public void run() {

		readingFromFile();

		if (resume) {
			System.out.println("In resume");
			resuming();
			highScore(high);
		}

		b = new Background(getWidth(), getHeight());
		add(b);

		t = new Tree(getWidth(), getHeight());
		add(t);

		d = new Dino(getWidth(), getHeight());
		add(d);

		tree = new Thread(t);
		back = new Thread(b);
		tree.start();
		back.start();
		arrayTree.add(t);
		
	
		
		while (true) {

			if (isIntersected) {

				removingAll();
				score++;

				d.standing(state);

				treeBridApperance();
				intersection();

				scoreBoard(score);
				scoreVoice(score);

				writingToTheFile();

			} else if (!gameNotOvered) {

				gameOver();
				pause(1500);
			}

		}
	}

	/**
	 * This method is used to resume the game where you left
	 * 
	 * 
	 */
	private void resuming() {
		for (int i = 0; i < treesAndScore.size() - 1; i++) {

			if (treesAndScore.get(i).startsWith("t")) {
				
				position = Double.parseDouble(treesAndScore.get(i).substring(5, treesAndScore.get(i).indexOf(':')));
				typo = Integer.parseInt(treesAndScore.get(i).substring(treesAndScore.get(i).indexOf(':')+1));
					System.out.println(position);
				if (position < 650 && position > 0) {
					Tree t = new Tree(position, getHeight(), typo);
					add(t);
					arrayTree.add(t);
					Thread th = new Thread(t);
					th.start();
				}
			} else if (treesAndScore.get(i).startsWith("s")) {
				score = Integer.parseInt(treesAndScore.get(i).substring(6));

			} else if (treesAndScore.get(i).startsWith("h")) {

				high = Integer.parseInt(treesAndScore.get(i).substring(5));
			}
		}
	}

	/**
	 * This method writes data of score and location current trees.
	 * 
	 * 
	 */
	private void writingToTheFile() {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("info.txt", false));

			for (int i = 0; i < arrayTree.size(); i++) {

				pw.println("tree " + arrayTree.get(i).getX() + ":" + + arrayTree.get(i).getType());
			
				pw.println("score " + score);
			}
			if (high > 0)
				pw.println("high " + high);
			pw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * This method removes all objects that are out of screen;
	 * 
	 * 
	 */

	private void removingAll() {
		for (int i = 0; i < arrayTree.size(); i++) {
			if (arrayTree.get(i).getX() < -100) {
				remove(arrayTree.get(i));
				arrayTree.remove(i);

			}

		}

		for (int i = 0; i < arrayBird.size(); i++) {
			if (arrayBird.get(i).getX() < -100) {
				remove(arrayBird.get(i));
				arrayBird.remove(i);

			}
		}
	}

	/**
	 * This method creates cactuses(trees) and birds in random position.
	 * 
	 */

	private void treeBridApperance() {

		if (score > 100 && score % 75 == 0) {

			Bird bird = new Bird(getWidth(), getHeight());
			add(bird);
			arrayBird.add(bird);
			Thread birdThread = new Thread(bird);
			birdThread.start();

		}

		else if (score % 15 == 0) {

			Tree t = new Tree(getWidth(), getHeight());
			add(t);

			arrayTree.add(t);
			Thread tt = new Thread(t);
			tt.start();

		}
	}

	/**
	 * This method checks intersection with bird and cactus, then if it
	 * intersects, it makes all running objects stop.
	 * 
	 */

	private void intersection() {

		for (int i = arrayTree.size() - 1; i >= 0; i--) {

			if (d.getBounds().intersects(arrayTree.get(i).getBounds())) {

				makingEveryThingFalse();

			}
		}
		for (int i = 0; i < arrayBird.size(); i++) {

			if (d.getBounds().intersects(arrayBird.get(i).getBounds())) {

				makingEveryThingFalse();
			}
		}

	}

	/**
	 * This method plays music when score is hundreds.
	 * 
	 */

	private void scoreVoice(int score) {

		if (score != 0 && score % 100 == 0) {
			WavPlayer scoreUp = new WavPlayer(new File("sounds/scoreup100.wav"));
			Thread th = new Thread(scoreUp);
			th.start();

		}
	}

	/**
	 * This method is handling display of current score.
	 * 
	 */

	private void scoreBoard(int score) {

		num5.setImage(score % 10 + ".jpg");
		num4.setImage(score / 10 % 10 + ".jpg");
		num3.setImage(score / 100 % 10 + ".jpg");
		num2.setImage(score / 1000 % 10 + ".jpg");
		num1.setImage(score / 10000 % 10 + ".jpg");

	}

	/**
	 * This method is handling display of high score.
	 * 
	 */

	private void highScore(int score) {
		if (score == 0)
			return;
		if (score > high)
			high = score;

		GImage num1, num2, num3, num4, num5;
		add(highScore, getWidth() - 250, 10);

		num5 = new GImage(high % 10 + ".jpg");
		add(num5, getWidth() - 130, getHeight() / 25);

		num4 = new GImage(high / 10 % 10 + ".jpg");
		add(num4, getWidth() - 145, getHeight() / 25);

		num3 = new GImage(high / 100 % 10 + ".jpg");
		add(num3, getWidth() - 160, getHeight() / 25);

		num2 = new GImage(high / 1000 % 10 + ".jpg");
		add(num2, getWidth() - 175, getHeight() / 25);

		num1 = new GImage(high / 10000 % 10 + ".jpg");
		add(num1, getWidth() - 190, getHeight() / 25);

	}

	private void makingEveryThingFalse() {
		for (int j = arrayTree.size() - 1; j >= 0; j--)
			arrayTree.get(j).isIntersected = false;

		for (int k = arrayBird.size() - 1; k >= 0; k--)
			arrayBird.get(k).isIntersected = false;

		b.isIntersected = false;
		isIntersected = false;
		d.isIntersected = false;

	}

	/**
	 * gameOver method is for adding pictures of last scene.
	 * 
	 * 
	 */

	private void gameOver() {

		if (gameOvered == 0) {

			WavPlayer dead = new WavPlayer(new File("sounds/dead.wav"));
			Thread th = new Thread(dead);
			th.start();

			highScore(score);

			d.first.setImage("dead Trex.png");
			d.first.setSize(50, 50);

			gameOver = new GImage("gameOver.png");
			gameOver.setLocation(getWidth() / 2 - gameOver.getWidth() / 2, getHeight() / 2 + gameOver.getHeight() / 2);
			add(gameOver);

			restart = new GImage("restartIcon.png");
			add(restart, getWidth() / 2 - restart.getWidth() / 2,
					getHeight() / 2 + gameOver.getHeight() / 2 + restart.getHeight());
			remove(backButton);
			System.out.println("yes");

			for (Bird b : arrayBird)
				b.bird.setImage("bird1.png");
		}
		gameOvered++;
	}

	/**
	 * This method reads from file in order to start from where it left. All
	 * information are stored in ArrayList.
	 * 
	 */

	private void readingFromFile() {
		try {

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader("info.txt"));

			while (true) {
				String line = br.readLine();
				treesAndScore.add(line);

				if (line == null)
					break;

			}

		} catch (FileNotFoundException e) {
			System.err.println("The file you specified does not exist.");
		} catch (IOException e) {
			System.err.println("Some other IO exception occured. Message: " + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * This mouse listener. In this method restarting game and resuming game
	 * parts are done.
	 * 
	 */

	public void mousePressed(MouseEvent e) {

		if (getElementAt(e.getX(), e.getY()) == null)
			return;
		gobj = getElementAt(new GPoint(e.getPoint()));
		if (restart != null && gobj.equals(restart)) {
			gameNotOvered = false;
			removeAll();
			arrayTree.clear();
			arrayBird.clear();

			// adding high score to the screen
			GImage numHigh1, numHigh2, numHigh3, numHigh4, numHigh5;
			add(highScore, getWidth() - 250, 10);

			numHigh5 = new GImage(high % 10 + ".jpg");
			add(numHigh5, getWidth() - 130, getHeight() / 25);

			numHigh4 = new GImage(high / 10 % 10 + ".jpg");
			add(numHigh4, getWidth() - 145, getHeight() / 25);

			numHigh3 = new GImage(high / 100 % 10 + ".jpg");
			add(numHigh3, getWidth() - 160, getHeight() / 25);

			numHigh2 = new GImage(high / 1000 % 10 + ".jpg");
			add(numHigh2, getWidth() - 175, getHeight() / 25);

			numHigh1 = new GImage(high / 10000 % 10 + ".jpg");
			add(numHigh1, getWidth() - 190, getHeight() / 25);

			// making 0 all variables
			score = 0;
			gameOvered = 0;

			// making true every variable
			t.isIntersected = true;
			b.isIntersected = true;
			isIntersected = true;
			bird.isIntersected = true;
			d.isIntersected = true;

			// adding background and dino
			b = new Background(getWidth(), getHeight());
			add(b);

			d = new Dino(getWidth(), getHeight());
			add(d);

			t = new Tree(getWidth(), getHeight());
			add(t);

			tree = new Thread(t);
			back = new Thread(b);

			tree.start();
			back.start();
			arrayTree.add(t);

			num1 = new GImage("0.jpg");
			num2 = new GImage("0.jpg");
			num3 = new GImage("0.jpg");
			num4 = new GImage("0.jpg");
			num5 = new GImage("0.jpg");

			add(num5, getWidth() - 30, getHeight() / 25);
			add(num4, getWidth() - 45, getHeight() / 25);
			add(num3, getWidth() - 60, getHeight() / 25);
			add(num2, getWidth() - 75, getHeight() / 25);
			add(num1, getWidth() - 90, getHeight() / 25);

			backButton();
		} else if (gobj.equals(backButton)) {
			gameNotOvered = true;
			makingEveryThingFalse();

			Window activeWindow = SwingUtilities.getWindowAncestor(e.getComponent());
			activeWindow.dispose();

			new Thread() {
				public void run() {
					new MenuPage().start();
				}
			}.start();
		}
	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {

			if (gameOvered == 0) {
				Thread th = new Thread(d);
				th.start();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN)

			state = "bended";

	}

	public void keyReleased(KeyEvent e) {
		state = "normal";

	}


	// Constants

	private static final long serialVersionUID = 1L;
	private static boolean resume = false;
	private int high;
	private boolean isIntersected = true;

	private GImage gameOver;
	private GObject gobj;
	private Dino d;
	private Background b;
	private Tree t;
	private Bird bird = new Bird(getWidth(), getHeight());
	private String state = "normal";

	private GImage restart;
	private GImage highScore = new GImage("highSCore.png");
	private GImage backButton;

	private int gameOvered = 0;

	private ArrayList<Tree> arrayTree = new ArrayList<>();
	private ArrayList<Bird> arrayBird = new ArrayList<>();
	ArrayList<String> treesAndScore = new ArrayList<>();

	private Thread tree;
	private Thread back;

	private int score = 0;
	private GImage num1, num2, num3, num4, num5;

}
