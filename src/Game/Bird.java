package Game;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.util.RandomGenerator;

public class Bird extends GCompound implements Runnable {

	public int SPEED = -10;
	public boolean isIntersected = true;
	GImage bird;

	public Bird(double width, double height) {
		RandomGenerator rn = new RandomGenerator();
		int x = rn.nextInt(0, 3);
		bird = new GImage("bird.gif");

		// adding birds randomly in random position
		if (x == 0)
			add(bird, width, 300);
		else if (x == 1)
			add(bird, width, 330);

		else
			add(bird, width, 380);

	}

	public void run() {

		while (isIntersected) {
			bird.move(SPEED, 0);
			pause(50);
		}

	}

}
