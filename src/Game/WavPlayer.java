package Game;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JSlider;

public class WavPlayer implements Runnable {

	private AudioInputStream audioInputStream = null;
	public Clip clip = null;
	JSlider slide;

	public WavPlayer(File f) {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(f);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			slide = new JSlider();
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}
	}

	public void setSlider(JSlider slider) {
		slide = slider;
	}


	@Override
	public void run() {
		clip.start();
		while (clip.getMicrosecondPosition() != clip.getMicrosecondLength()) {
			slide.setValue((int) (clip.getMicrosecondPosition() / 1000000));
		}

	}

}