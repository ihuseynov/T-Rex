package Game;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MenuPage extends GraphicsProgram {

	private static final long serialVersionUID = 1L;
	public static final int APPLICATION_WIDTH = 765;
	public static final int APPLICATION_HEIGHT = 765;

	public void init() {

		addMouseListeners();
	}

	public void run() {

		menuStage();

	}

	public void mousePressed(MouseEvent e) {
		GObject obj = getElementAt(new GPoint(e.getPoint()));

		if (obj == null)
			return;

		if (obj.equals(labelResume)) {
			Window activeWindow = SwingUtilities.getWindowAncestor(e.getComponent());
			activeWindow.dispose();	

			new Thread() {
				public void run() {
					new Main(true).start();
				}
			}.start();

		} else if (obj.equals(labelNewGame)) {

			Window activeWindow = SwingUtilities.getWindowAncestor(e.getComponent());
			activeWindow.dispose();
	
			new Thread() {
				public void run() {
					new Main(false).start();
				}
			}.start();

		} else if (obj.equals(labelAbout)) {
			about();

		} else if (obj.equals(back)) {

			removeAll();
			menuStage();
		} else if (obj.equals(labelExit)) {
			System.exit(0);
		}

	}

	public void mouseMoved(MouseEvent e) {

		if (recResume.contains(new GPoint(e.getPoint())))
			recResume.setColor(Color.BLACK);
		else
			recResume.setColor(Color.GRAY);

		if (recNewGame.contains(new GPoint(e.getPoint())))
			recNewGame.setColor(Color.BLACK);
		else
			recNewGame.setColor(Color.GRAY);

		if (recAbout.contains(new GPoint(e.getPoint())))
			recAbout.setColor(Color.BLACK);
		else
			recAbout.setColor(Color.GRAY);

		if (recExit.contains(new GPoint(e.getPoint())))
			recExit.setColor(Color.BLACK);
		else
			recExit.setColor(Color.GRAY);

	}

	private void menuStage() {

		labelTrex = new GLabel("T-Rex");
		labelTrex.setColor(Color.BLACK);
		labelTrex.setFont("Georgia-bold-60");
		add(labelTrex, getWidth() / 2 - labelTrex.getWidth() / 2, labelTrex.getHeight() * 2);

		labelResume = new GLabel("Resume");
		labelResume.setColor(Color.WHITE);
		labelResume.setFont("Georgia-bold-20");
		add(labelResume, getWidth() / 2 - labelResume.getWidth() / 2, getHeight() / 2 - labelResume.getHeight());

		recResume = new GRect(labelResume.getWidth(), labelResume.getHeight());
		recResume.setFilled(true);
		recResume.setColor(Color.GRAY);
		add(recResume, getWidth() / 2 - labelResume.getWidth() / 2, getHeight() / 2 - labelResume.getHeight() * 2 + 5);
		recResume.sendToBack();

		labelNewGame = new GLabel("New Game");
		labelNewGame.setFont("Georgia-bold-20");
		labelNewGame.setColor(Color.WHITE);
		add(labelNewGame, getWidth() / 2 - labelResume.getWidth() / 2 - 12, getHeight() / 2 + labelResume.getHeight());

		recNewGame = new GRect(labelNewGame.getWidth(), labelNewGame.getHeight());
		recNewGame.setFilled(true);
		recNewGame.setColor(Color.GRAY);
		add(recNewGame, getWidth() / 2 - labelResume.getWidth() / 2 - 12,
				getHeight() / 2 + labelResume.getHeight() / 2 - 8);
		recNewGame.sendToBack();

		labelAbout = new GLabel("About");
		labelAbout.setFont("Georgia-bold-20");
		labelAbout.setColor(Color.WHITE);
		add(labelAbout, getWidth() / 2 - labelResume.getWidth() / 2 + 12,
				getHeight() / 2 + labelResume.getHeight() * 2 + 12);

		recAbout = new GRect(labelAbout.getWidth(), labelAbout.getHeight());
		recAbout.setFilled(true);
		recAbout.setColor(Color.GRAY);
		add(recAbout, getWidth() / 2 - labelResume.getWidth() / 2 + 12,
				getHeight() / 2 + labelResume.getHeight() / 2 + 30);
		recAbout.sendToBack();

		labelExit = new GLabel("Exit");
		labelExit.setFont("Georgia-bold-20");
		labelExit.setColor(Color.WHITE);
		add(labelExit, getWidth() / 2 - labelResume.getWidth() / 2 + 20,
				getHeight() / 2 + labelResume.getHeight() * 2 + 50);

		recExit = new GRect(labelExit.getWidth(), labelExit.getHeight());
		recExit.setFilled(true);
		recExit.setColor(Color.GRAY);
		add(recExit, getWidth() / 2 - labelResume.getWidth() / 2 + 20,
				getHeight() / 2 + labelResume.getHeight() * 2 + 30);
		recExit.sendToBack();

	}

	private void about() {

		removeAll();
		String about = "This is demo version of T-Rex game created by @iqbalhuseyn.";
		GLabel label = new GLabel(about);
		label.setFont("Georgia-italic-20");
		add(label, getWidth() / 2 - label.getWidth() / 2, getHeight() / 2 - label.getHeight() / 2);

		back.setSize(80, 80);
		add(back, 20, 20);

	}

	GImage back = new GImage("back.png");
	GRect recResume, recExit, recAbout, recNewGame;
	GLabel labelResume, labelExit, labelAbout, labelNewGame, labelTrex;

	public static void main(String[] args) {
		new MenuPage().start();
	}
}
