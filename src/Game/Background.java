package Game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.util.RandomGenerator;

public class Background extends GCompound implements Runnable {
	GImage ground1, ground2, cloud1, cloud2, cloud3;
	boolean isIntersected = true;
	private double height;
	RandomGenerator rn = new RandomGenerator();
	
	public int SPEED = -10;
	
	
	public Background(double width, double height) {
		this.height = height;

		ground1 = new GImage("ground.png");
		add(ground1, 0, height - 70);

		ground2 = new GImage("ground.png");
		add(ground2, ground1.getWidth() - 100, height - 70);

		cloud1 = new GImage("cloud.png");
		add(cloud1, width / 2, height / 5);

		cloud2 = new GImage("cloud.png");
		add(cloud2, width, height / 5 * 1.2);

		cloud3 = new GImage("cloud.png");
		add(cloud3, width * 1.3, height / 5 * 1.1);
	}

	@Override
	public void run() {

		while (isIntersected) {

			randomCloud();
			infinityGround();
			ground1.move(SPEED, 0);
			ground2.move(SPEED, 0);
			cloud1.move(-2, 0);
			cloud2.move(-2, 0);
			cloud3.move(-2, 0);
			pause(50);

			try {

				PrintWriter pw = new PrintWriter(new FileWriter("cloud.txt", false));

				pw.println("cloud1 " + cloud1.getX() + " " + cloud1.getY());
				pw.println("cloud2 " + cloud2.getX() + " " + cloud2.getY());
				pw.println("cloud3 " + cloud3.getX() + " " + cloud3.getY());

				pw.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}
	
	
	/**
	 * This method creates 3 cloud in random position
	 *
	 */

	private void randomCloud() {
		double height1 = rn.nextDouble(100, 250);
		if (cloud1.getX() < -cloud1.getWidth())
			cloud1.setLocation(1200, height1);

		double height2 = rn.nextDouble(100, 250);

		if (cloud2.getX() < -cloud2.getWidth())
			cloud2.setLocation(1200, height2);

		double height3 = rn.nextDouble(100, 250);

		if (cloud3.getX() < -cloud3.getWidth())
			cloud3.setLocation(1200, height3);

	}
	
	/**This method creates infinity ground
	 * 
	 * 
	 */
	
	private void infinityGround() {
		if (ground2.getX() == -454 || ground2.getX() == -455) {
			ground1.setLocation(745, height - 70);
		}
		if (ground1.getX() == -455) {
			ground2.setLocation(745, height - 70);
		}

	}

}
