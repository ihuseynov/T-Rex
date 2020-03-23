package Game;

import java.io.File;

import acm.graphics.GCompound;
import acm.graphics.GImage;;

public class Dino extends GCompound implements Runnable {
	GImage first;
	public boolean isIntersected = true;

	public Dino(double width, double height) {

		first = new GImage("first Trex.png");
		first.setSize(50, 50);
		add(first, 50, 360);

	}

	public void run() {
		
		//playing sound while jumping
	

		//jumping
		if (first.getY() == 360) {
			WavPlayer jump = new WavPlayer(new File("sounds/jump.wav"));
			Thread th = new Thread(jump);
			th.start();
			
			
			for (int i = 0; i < 15; i++) {
				if (!isIntersected)
					break;
				first.move(0, -11);
				pause(25);
			}

			for (int i = 0; i < 15; i++) {
				if (!isIntersected)
					break;
				first.move(0, 11);
				pause(25);

			}
		}

	}

	
	/**This method changes the position of Dino. For example, running, bending.
	 * 
	 * 
	 * @param state
	 */
	
	public void standing(String state) {

		pause(100);
		if (first.getY() < 360) {
			first.setImage("first Trex.png");
			first.setSize(50, 50);

		} else if (state.equals("normal")) {

			if (first.getY() == 380)
				first.setLocation(first.getX(), first.getY() - 20);

			first.setImage("second Trex.png");
			first.setSize(50, 50);
			pause(100);
			first.setImage("third Trex.png");
			first.setSize(50, 50);
		} 
		else if (state.equals("bended")) {

			if (first.getY() == 360)

				first.setLocation(first.getX(), first.getY() + 20);

			first.setImage("bend1.png");
			first.setSize(50, 30);

			pause(100);
			first.setImage("bend2.png");
			first.setSize(50, 30);

		}
	}
		
	//getters	
	public double getWidth() {

		return first.getWidth();
	}

	public double getHeight() {

		return first.getHeight();
	}


}
