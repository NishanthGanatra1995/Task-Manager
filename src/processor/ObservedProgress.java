package processor;

import java.util.Observable;
import java.util.concurrent.Callable;

/**
 * Created by Nishanth on 18/07/2016.
 */
public class ObservedProgress extends Observable {

	private int progress;
	private Callable<Object> runnable;

	public ObservedProgress () {
		progress = 0;
	}

	public void setProgress (int progress) {
		this.progress = progress;
		setChanged();
		notifyObservers(this);
	}

	public void setRunnable(Callable<Object> runnable) {
		this.runnable = runnable;
	}

	public int getProgress() {
		return progress;
	}

	public Callable<Object> getRunnable() {
		return runnable;
	}
}
