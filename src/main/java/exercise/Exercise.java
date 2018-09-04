package exercise;

import java.io.File;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Exercise {
	
	private static final String BASE_PATH = Exercise.class.getResource("").getPath();
	private static final String TIGHT_SOUND = BASE_PATH + "tighten.wav";
	private static final String RELEASE_SOUND = BASE_PATH + "release.wav";

	public static void main(String[] args) {
		logInfo("{}", new Date());
		doQuikTightenAndRelease();
		relex(5);
		doSlowTightenAndRelease(50);
		relex(5);
		doTighten(10);
		logInfo("{}", new Date());
	}

	private static void doSlowTightenAndRelease(int maxCount) {
		playHightlightSound(TIGHT_SOUND);
		logInfo("Slow -><- and <--> begin");
		playSound(TIGHT_SOUND);
		logInfo("-><-: {}", 1);
		
		long last = System.currentTimeMillis();
		int count = 0;
		while (count < maxCount * 2 - 1) {
			long currentTimeMillis = System.currentTimeMillis();
			if (count % 2 == 0) {
				if (currentTimeMillis - last >= 10 * 1000) {
					playSound(RELEASE_SOUND);
					logInfo("<-->");
					last = currentTimeMillis;
					count++;
				}
			} else {
				if (currentTimeMillis - last >= 5 * 1000) {
					playSound(TIGHT_SOUND);
					logInfo("-><-: {}", (count / 2 + 2));
					last = currentTimeMillis;
					count++;
				}
			}
		}
		playHightlightSound(RELEASE_SOUND);
		logInfo("Slow -><- and <--> end");
	}

	private static void playSound(String soundFile) {
		try {
			File file = new File(soundFile).getAbsoluteFile();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doTighten(int mins) {
		playHightlightSound(TIGHT_SOUND);
		logInfo("-><- begin");
		
		for (int index = 0; index < mins; index++) {
			logInfo("-><-: {} minutes left", (mins - index));
			keepGoing(60);
		}
		
		playHightlightSound(RELEASE_SOUND);
		logInfo("-><- end");
	}

	private static void relex(int mins) {
		logInfo("==== begin");
		for (int index = 0; index < mins; index++) {
			logInfo("====: {} minutes left", (mins - index));
			keepGoing(60);
		}
		logInfo("==== end");
	}

	private static void doQuikTightenAndRelease() {
		playHightlightSound(TIGHT_SOUND);
		logInfo("Quick -><- and <--> begin");
		keepGoing(120);
		playHightlightSound(RELEASE_SOUND);
		logInfo("Quick -><- and <--> end");
	}

	private static void keepGoing(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void playHightlightSound(String soundFile) {
		for (int index = 0; index < 2; index++) {
			playSound(soundFile);
			keepGoing(1);
			playSound(soundFile);
			keepGoing(1);
			playSound(soundFile);
			keepGoing(2);
		}
	}

	private static void logInfo(String info, Object... args) {
		for (Object arg : args) {
			info = info.replaceFirst("\\{\\}", String.valueOf(arg));
		}
		System.out.println(info);
	}

}
