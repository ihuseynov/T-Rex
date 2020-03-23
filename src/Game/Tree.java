package Game;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.util.RandomGenerator;

public class Tree extends GCompound implements Runnable {
	GImage tree;
	boolean isIntersected = true;
	public double SPEED = -5;

	RandomGenerator rn = new RandomGenerator();
	int x = rn.nextInt(0, 3);

	public Tree(double width, double height) {

		// random 4 types Tree

		if (x == 0) {
			tree = new GImage("images/tree0.png");
			tree.setSize(20, 50);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 1) {
			tree = new GImage("cactus2.png");
			tree.setSize(40, 40);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 2) {
			tree = new GImage("cactus3.png");
			tree.setSize(60, 40);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 3) {
			tree = new GImage("small1.png");
			tree.setSize(20, 40);
			add(tree, width, height - 55 - tree.getHeight());
		}

	}

	public Tree(double width, double height, double x) {

		// random 4 types Tree

		if (x == 0) {
			tree = new GImage("tree0.png");
			tree.setSize(20, 50);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 1) {
			tree = new GImage("cactus2.png");
			tree.setSize(40, 40);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 2) {
			tree = new GImage("cactus3.png");
			tree.setSize(60, 40);
			add(tree, width, height - 55 - tree.getHeight());
		} else if (x == 3) {
			tree = new GImage("small1.png");
			tree.setSize(20, 40);
			add(tree, width, height - 55 - tree.getHeight());
		}

	}

	public double getX() {
		return tree.getX();
	}

	public int getType() {
		return x;
	}

	@Override
	public void run() {
		while (isIntersected) {
			tree.move(SPEED, 0);
			pause(50);
		}
	}
}
